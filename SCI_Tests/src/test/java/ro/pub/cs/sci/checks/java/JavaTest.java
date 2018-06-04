package ro.pub.cs.sci.checks.java;

import org.apache.commons.lang.StringUtils;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.plugins.java.api.JavaFileScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan Anton (banton@adobe.com)
 */
public abstract class JavaTest {

    private static final List<File> CLASSPATH_JAR;

    private static final String SUREFIRE_TEST_CLASS_PATH = "surefire.test.class.path";

    private static final String JAVA_CLASS_PATH = "java.class.path";

    static {
        CLASSPATH_JAR = new ArrayList<>();
        String classPath = StringUtils.defaultIfBlank(System.getProperty(SUREFIRE_TEST_CLASS_PATH), System.getProperty(JAVA_CLASS_PATH));
        if (StringUtils.isNotBlank(classPath)) {
            for (String jar : classPath.split(File.pathSeparator)) {
                if (jar.endsWith(".jar")) {
                    CLASSPATH_JAR.add(new File(jar));
                }
            }
        }
    }

    protected JavaFileScanner check;

    protected String filename;

    protected void verify() {
        verify(true);
    }

    protected void verify(boolean withJarClassPath) {
        if (withJarClassPath) {
            JavaCheckVerifier.verify(filename, check, CLASSPATH_JAR);
        } else {
            JavaCheckVerifier.verify(filename, check);
        }
    }

    protected void verifyNoIssue() {
        JavaCheckVerifier.verifyNoIssue(filename, check);
    }
}

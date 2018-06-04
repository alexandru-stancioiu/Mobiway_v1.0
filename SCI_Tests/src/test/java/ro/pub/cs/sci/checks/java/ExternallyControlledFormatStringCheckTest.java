package ro.pub.cs.sci.checks.java;

import org.junit.Test;
import ro.pub.cs.sci.checks.ExternallyControlledFormatStringCheck;

public class ExternallyControlledFormatStringCheckTest extends JavaTest {

    @Test
    public void visitNode() {
        check = new ExternallyControlledFormatStringCheck();
        filename = "src/test/files/checks/java/ExternallyControlledFormatString.java";
        verify();
    }

}


package ro.pub.cs.sci.extensions;

import com.google.common.collect.ImmutableList;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import ro.pub.cs.sci.checks.ExternallyControlledFormatStringCheck;

import java.util.Collections;
import java.util.List;

public class JavaCheckListRegistrar implements CheckRegistrar {

    public static final List<Class<? extends JavaCheck>> CHECK_CLASSES
            = ImmutableList.<Class<? extends JavaCheck>>builder()
            .add(ExternallyControlledFormatStringCheck.class)
            .build();

    public void register(RegistrarContext registrarContext) {
        registrarContext.registerClassesForRepository(
                "SCIRules",
                CHECK_CLASSES,
                Collections.<Class<? extends JavaCheck>>emptyList()
        );
    }
}

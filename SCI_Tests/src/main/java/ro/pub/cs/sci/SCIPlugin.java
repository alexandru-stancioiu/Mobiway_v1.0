package ro.pub.cs.sci;
import org.sonar.api.Plugin;
import org.sonar.api.server.rule.RulesDefinition;
import ro.pub.cs.sci.extensions.JavaCheckListRegistrar;
import ro.pub.cs.sci.profiles.JavaProfile;

public class SCIPlugin implements Plugin {

    public void define(Context context) {
        context.addExtensions(JavaProfile.class,
                JavaCheckListRegistrar.class,
                RulesDefinition.class);
    }
}

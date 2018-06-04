package ro.pub.cs.sci.profiles;

import org.apache.commons.io.IOUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.profiles.XMLProfileParser;
import org.sonar.api.resources.Languages;
import org.sonar.api.utils.ValidationMessages;
import ro.pub.cs.sci.util.InputStreamLanguageDecorator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class QualityProfile extends ProfileDefinition {

    protected Settings settings;
    protected Languages languages;
    protected XMLProfileParser parser;
    protected String languageKey;
    protected String profileSource;

    public QualityProfile(Settings settings, Languages languages, XMLProfileParser parser, String languageKey, String profileSource) {
        this.settings = settings;
        this.languages = languages;
        this.parser = parser;
        this.languageKey = languageKey;
        this.profileSource = profileSource;
    }

    @Override
    public RulesProfile createProfile(ValidationMessages validationMessages) {

        if (languages.get(languageKey) != null) {
            InputStream input = QualityProfile.class.getResourceAsStream(profileSource);
            InputStreamReader reader = new InputStreamReader(
                    new InputStreamLanguageDecorator(input, languageKey)
            );
            try {
                RulesProfile parse = parser.parse(reader, validationMessages);
                parse.setDefaultProfile(true);
                return parse;
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    IOUtils.closeQuietly(reader);
                }
            }
        } else {
            return null;
        }
    }
}

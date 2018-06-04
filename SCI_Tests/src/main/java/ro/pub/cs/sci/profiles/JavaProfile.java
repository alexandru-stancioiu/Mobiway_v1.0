/*************************************************************************
 * ADOBE CONFIDENTIAL
 * __________________
 * <p>
 * Copyright 2017 Adobe Systems Incorporated
 * All Rights Reserved.
 * <p>
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package ro.pub.cs.sci.profiles;

import org.sonar.api.config.Settings;
import org.sonar.api.profiles.XMLProfileParser;
import org.sonar.api.resources.Languages;

public class JavaProfile extends QualityProfile {

    public JavaProfile(Settings settings, Languages languages, XMLProfileParser parser) {
        super(settings, languages, parser, "java", "/java-profile.xml");
    }
}

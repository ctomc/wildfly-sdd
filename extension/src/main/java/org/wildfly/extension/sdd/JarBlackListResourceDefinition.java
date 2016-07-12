package org.wildfly.extension.sdd;

import java.util.Collection;
import java.util.Collections;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.AttributeMarshaller;
import org.jboss.as.controller.AttributeParser;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.StringListAttributeDefinition;

/**
 * @author Tomaz Cerar (c) 2013 Red Hat Inc.
 */
class JarBlackListResourceDefinition extends PersistentResourceDefinition {

    static final StringListAttributeDefinition JAR_NAMES = new StringListAttributeDefinition.Builder("jars")
            .setAllowNull(false)
            .setAttributeMarshaller(AttributeMarshaller.COMMA_STRING_LIST)
            .setAttributeParser(AttributeParser.COMMA_DELIMITED_STRING_LIST)
            .build();

    static final JarBlackListResourceDefinition INSTANCE = new JarBlackListResourceDefinition();

    private JarBlackListResourceDefinition() {
        super(SDDExtension.JAR_BLACKLIST_PATH,
                SDDExtension.getResolver("jar-blacklist"),
                //new AbstractAddStepHandler(JAR_NAMES),
                new JarBlackListAdd(),
                ReloadRequiredRemoveStepHandler.INSTANCE);
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Collections.singleton((AttributeDefinition) JAR_NAMES);
    }
}

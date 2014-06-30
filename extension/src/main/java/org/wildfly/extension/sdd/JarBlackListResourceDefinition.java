package org.wildfly.extension.sdd;

import java.util.Collection;
import java.util.Collections;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.DefaultAttributeMarshaller;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.StringListAttributeDefinition;
import org.jboss.dmr.ModelNode;

/**
 * @author Tomaz Cerar (c) 2013 Red Hat Inc.
 */
class JarBlackListResourceDefinition extends PersistentResourceDefinition {

    static final StringListAttributeDefinition JAR_NAMES = new StringListAttributeDefinition.Builder("jars")
            .setAllowNull(false)
            .setAttributeMarshaller(new DefaultAttributeMarshaller() {
                @Override
                public void marshallAsAttribute(AttributeDefinition attribute, ModelNode resourceModel, boolean marshallDefault, XMLStreamWriter writer) throws
                        XMLStreamException {

                    StringBuilder builder = new StringBuilder();
                    if (resourceModel.hasDefined(attribute.getName())) {
                        for (ModelNode p : resourceModel.get(attribute.getName()).asList()) {
                            builder.append(p.asString()).append(", ");
                        }
                    }
                    if (builder.length() > 3) {
                        builder.setLength(builder.length() - 2);
                    }
                    if (builder.length() > 0) {
                        writer.writeAttribute(attribute.getXmlName(), builder.toString());
                    }
                }
            })
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

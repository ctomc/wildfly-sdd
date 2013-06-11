/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.extension.sdd;

import static org.jboss.as.controller.parsing.ParseUtils.readStringAttributeElement;
import static org.jboss.as.controller.parsing.ParseUtils.unexpectedElement;

import java.util.List;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a> (c) 2012 Red Hat Inc.
 */
class SDDSubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>, XMLElementWriter<SubsystemMarshallingContext> {
    protected static final SDDSubsystemParser INSTANCE = new SDDSubsystemParser();

    private SDDSubsystemParser() {
    }

    public void writeContent(XMLExtendedStreamWriter writer, SubsystemMarshallingContext context) throws XMLStreamException {
        context.startSubsystemElement(Namespace.CURRENT.getUriString(), false);
        ModelNode model = context.getModelNode();
        ModelNode blackListModel = model.get(SDDExtension.JAR_BLACKLIST_PATH.getKeyValuePair());
        if (blackListModel.isDefined()) {
            writer.writeStartElement("blacklist");
            JarBlackListResourceDefinition.JAR_NAMES.getAttributeMarshaller().marshallAsAttribute(JarBlackListResourceDefinition.JAR_NAMES, blackListModel, false, writer);
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    /**
     * {@inheritDoc}
     */

    public void readElement(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException {
        final PathAddress address = PathAddress.pathAddress(SDDExtension.SUBSYSTEM_PATH);
        list.add(Util.createAddOperation(address));

        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            switch (Namespace.forUri(reader.getNamespaceURI())) {
                case SDD_1_0: {
                    switch (reader.getLocalName()) {
                        case "blacklist": {
                            ModelNode op = Util.createAddOperation(address.append(SDDExtension.JAR_BLACKLIST_PATH));
                            String value = readStringAttributeElement(reader, "jars");
                            JarBlackListResourceDefinition.JAR_NAMES.parseAndSetParameter(value, op, reader);
                            list.add(op);
                            break;
                        }
                        default: {
                            reader.handleAny(list);
                            break;
                        }
                    }
                    break;
                }
                default: {
                    throw unexpectedElement(reader);
                }
            }
        }
    }
}


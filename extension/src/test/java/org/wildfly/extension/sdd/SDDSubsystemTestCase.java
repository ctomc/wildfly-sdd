package org.wildfly.extension.sdd;

import java.io.IOException;

import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;

/**
 * This is the barebone test
 *
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
public class SDDSubsystemTestCase extends AbstractSubsystemBaseTest {

    public SDDSubsystemTestCase() {
        super(SDDExtension.SUBSYSTEM_NAME, new SDDExtension());
    }

    @Override
    protected String getSubsystemXml() throws IOException {
        return readResource("sdd-1.0.xml");
    }

}

package org.wildfly.extension.sdd;

import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

/**
 * @author Tomaz Cerar (c) 2013 Red Hat Inc.
 */
public class SDDRootResource extends SimpleResourceDefinition {
    static final SDDRootResource INSTANCE = new SDDRootResource();

    private SDDRootResource() {
        super(SDDExtension.SUBSYSTEM_PATH,
                SDDExtension.getResolver(),
                new SDDSubsystemAdd(),
                ReloadRequiredRemoveStepHandler.INSTANCE);
    }

    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        resourceRegistration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE, false);
    }

    @Override
    public void registerChildren(ManagementResourceRegistration resourceRegistration) {
        super.registerChildren(resourceRegistration);
        resourceRegistration.registerSubModel(JarBlackListResourceDefinition.INSTANCE);
    }
}

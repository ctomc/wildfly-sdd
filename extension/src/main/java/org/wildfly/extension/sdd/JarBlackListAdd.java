package org.wildfly.extension.sdd;

import java.util.List;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.as.server.deployment.Phase;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;

/**
 * @author Tomaz Cerar (c) 2013 Red Hat Inc.
 */
final class JarBlackListAdd extends AbstractBoottimeAddStepHandler {

    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        JarBlackListResourceDefinition.JAR_NAMES.validateAndSet(operation, model);
    }

    @Override
    protected void performBoottime(OperationContext context, ModelNode operation, ModelNode model, ServiceVerificationHandler verificationHandler, List<ServiceController<?>> newControllers) throws OperationFailedException {
        final List<String> blacklist =  JarBlackListResourceDefinition.JAR_NAMES.unwrap(context,model);
        context.addStep(new AbstractDeploymentChainStep() {
            @Override
            protected void execute(DeploymentProcessorTarget processorTarget) {

                processorTarget.addDeploymentProcessor(SDDExtension.SUBSYSTEM_NAME, Phase.PARSE, Phase.PARSE_WEB_DEPLOYMENT, new JarBlackListDetectorProcessor(blacklist));


            }
        }, OperationContext.Stage.RUNTIME);
    }
}

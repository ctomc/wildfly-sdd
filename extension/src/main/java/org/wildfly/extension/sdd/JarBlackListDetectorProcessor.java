package org.wildfly.extension.sdd;

import java.io.IOException;
import java.util.List;

import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.modules.filter.PathFilter;
import org.jboss.modules.filter.PathFilters;
import org.jboss.vfs.VirtualFile;

/**
 * @author Tomaz Cerar (c) 2013 Red Hat Inc.
 */
public class JarBlackListDetectorProcessor implements DeploymentUnitProcessor {

    private final List<String> blackList;

    public JarBlackListDetectorProcessor(List<String> blacklist) {
        this.blackList = blacklist;
        SDDLogger.ROOT_LOGGER.infof("Jar blacklist: %s", blacklist);
    }

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        final ResourceRoot deploymentRoot = deploymentUnit.getAttachment(Attachments.DEPLOYMENT_ROOT);
        List<ResourceRoot> resourceRoots = deploymentUnit.getAttachmentList(Attachments.RESOURCE_ROOTS);

        try {
            processBlackList(resourceRoots);
        } catch (IOException e) {
            SDDLogger.ROOT_LOGGER.couldNotProcessBlacklist(e);
        }

    }

    @Override
    public void undeploy(DeploymentUnit context) {

    }


    private void processBlackList(final List<ResourceRoot> jars) throws IOException {
        for (String blackListed : blackList) {
            PathFilter filter = PathFilters.match(blackListed);
            for (ResourceRoot root : jars) {
                VirtualFile jar = root.getRoot();
                SDDLogger.ROOT_LOGGER.tracef("Processing jar %s, matches: %s", jar.getName(), filter.accept(jar.getName()));

                if (filter.accept(jar.getName())) {
                    SDDLogger.ROOT_LOGGER.jarBlacklisted(jar);
                }
            }
        }
    }
}

package org.wildfly.extension.sdd;

import java.io.IOException;
import java.util.List;

import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ModuleSpecification;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.as.server.moduleservice.ServiceModuleLoader;
import org.jboss.modules.filter.PathFilter;
import org.jboss.modules.filter.PathFilters;
import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.VirtualFileFilter;
import org.jboss.vfs.VisitorAttributes;
import org.jboss.vfs.util.SuffixMatchFilter;

/**
 * @author Tomaz Cerar (c) 2013 Red Hat Inc.
 */
public class ClassBlackListDetectorProcessor implements DeploymentUnitProcessor {

    private static final String WEB_INF_LIB = "WEB-INF/lib";
    private static final String EAR_LIB = "lib";
    private static final VirtualFileFilter JAR_FILTER = new SuffixMatchFilter(".jar", VisitorAttributes.DEFAULT);
    private final List<String> blackList;

    public ClassBlackListDetectorProcessor(List<String> blacklist) {
        this.blackList = blacklist;
        SDDLogger.ROOT_LOGGER.infof("Class blacklist: %s", blacklist);
    }

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        final ResourceRoot deploymentRoot = deploymentUnit.getAttachment(Attachments.DEPLOYMENT_ROOT);
        final ServiceModuleLoader moduleLoader = deploymentUnit.getAttachment(Attachments.SERVICE_MODULE_LOADER);
        final ModuleSpecification moduleSpecification = deploymentUnit.getAttachment(Attachments.MODULE_SPECIFICATION);
        List<ResourceRoot> resourceRoots = deploymentUnit.getAttachmentList(Attachments.RESOURCE_ROOTS);


        //List<VirtualFile> jars = getJarList(deploymentRoot.getRoot());


        try {
            processBlackList(resourceRoots);
        } catch (IOException e) {
            SDDLogger.ROOT_LOGGER.couldNotProcessBlacklist(e);
        }

    }

    @Override
    public void undeploy(DeploymentUnit context) {

    }

    /*<T> List<T> findIntersection(Set<T> a, Set<T> b) {
        if (a.size() > b.size()) { return findIntersection(b, a); }
        List<T> l = new ArrayList<T>();
        for (T t : a) { if (b.contains(call)) { l.add(call); } }
        return l;
    }*/

   /* private List<VirtualFile> getJarList(final VirtualFile deploymentRoot) throws DeploymentUnitProcessingException {
        final List<VirtualFile> entries = new ArrayList<>();
        // WEB-INF lib
        final VirtualFile webinfLib = deploymentRoot.getChild(WEB_INF_LIB);
        final VirtualFile earLib = deploymentRoot.getChild(EAR_LIB);
        try {
            if (webinfLib.exists()) {
                final List<VirtualFile> archives = webinfLib.getChildren(JAR_FILTER);
                for (final VirtualFile archive : archives) {
                    entries.add(archive);

                }
            }
            if (earLib.exists()) {
                final List<VirtualFile> archives = earLib.getChildren(JAR_FILTER);
                for (final VirtualFile archive : archives) {
                    entries.add(archive);

                }
            }
        } catch (IOException e) {
            throw new DeploymentUnitProcessingException("Could get get jar list!", e);
        }
        return entries;
    }*/

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

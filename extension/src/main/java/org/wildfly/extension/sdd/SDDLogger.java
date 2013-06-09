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

import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.WARN;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.vfs.VirtualFile;

/**
 * @author Tomaz Cerar
 */
@MessageLogger(projectCode = "SDD")
public interface SDDLogger extends BasicLogger {

    /**
     * A root logger with the category of the package name.
     */
    SDDLogger ROOT_LOGGER = Logger.getMessageLogger(SDDLogger.class, SDDLogger.class.getPackage().getName());

    ////////////////////////////////////////////////////////////
    //18200-18226 are copied across from the old web subsystem

    @LogMessage(level = INFO)
    @Message(id = 1, value = "Starting Silly Deployment Detector subsystem")
    void subsystemStarted();

    @LogMessage(level = WARN)
    @Message(id = 2, value = "Jar %s could cause deployment problems")
    void jarBlacklisted(VirtualFile file);

    @LogMessage(level = WARN)
    @Message(id = 3, value = "Could not process blacklist")
    void couldNotProcessBlacklist(@Cause Throwable cause);


}

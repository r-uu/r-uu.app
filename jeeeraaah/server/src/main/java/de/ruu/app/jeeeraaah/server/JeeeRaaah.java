// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package de.ruu.app.jeeeraaah.server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import static de.ruu.app.jeeeraaah.common.Paths.PATH_TO_APP;

@ApplicationPath(PATH_TO_APP)
public class JeeeRaaah extends Application { }

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
package de.ruu.app.demo.server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import static de.ruu.app.demo.common.Paths.DEMO;

@ApplicationPath(DEMO)
public class Demo extends Application { }

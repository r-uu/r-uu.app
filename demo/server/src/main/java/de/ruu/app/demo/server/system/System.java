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
package de.ruu.app.demo.server.system;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import static de.ruu.app.demo.common.Paths.SYSTEM;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Path(SYSTEM)
public class System
{
    @GET
    @Produces(APPLICATION_JSON)
    @Timed(name = "getPropertiesTime",
           description = "Time needed to lookup the JVM system properties")
    @Counted(absolute = true, description
             = "Number of times the JVM system properties are requested")
    public Response getProperties() {
        return Response.ok(java.lang.System.getProperties()).build();
    }
}
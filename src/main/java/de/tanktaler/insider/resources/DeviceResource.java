/**
 * Copyright 2018 ThinxNet GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tanktaler.insider.resources;

import de.tanktaler.insider.core.auth.InsiderAuthPrincipal;
import de.tanktaler.insider.core.response.InsiderEnvelop;
import de.tanktaler.insider.models.device.Device;
import io.dropwizard.auth.Auth;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class DeviceResource {
  private final Datastore datastore;

  public DeviceResource(final Datastore datastore) {
    this.datastore = datastore;
  }

  @Inject
  private Morphia morphia;

  @GET
  @Path("/{id}")
  public Response fetchOne(
    @Auth final InsiderAuthPrincipal user,
    @PathParam("id") final ObjectId id
  ) {
    return Response.ok(new InsiderEnvelop(
      this.morphia.toDBObject(this.datastore.get(Device.class, id))
    )).build();
  }

  @GET
  public Response fetchAll(@Auth final InsiderAuthPrincipal user) {
    return Response.ok(
      new InsiderEnvelop(
        this.datastore.createQuery(Device.class).field("thing").in(
          user.entity().getThings().stream().map(e -> e.getId()).collect(Collectors.toSet())
        ).asList().stream().map(this.morphia::toDBObject).toArray()
      )
    ).build();
  }
}
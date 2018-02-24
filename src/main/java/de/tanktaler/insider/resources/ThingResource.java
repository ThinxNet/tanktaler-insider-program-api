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
import de.tanktaler.insider.models.thing.Thing;
import io.dropwizard.auth.Auth;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/things")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class ThingResource {
  private final Datastore datastore;

  public ThingResource(final Datastore datastore) {
    this.datastore = datastore;
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Thing fetchOne(
    @Auth final InsiderAuthPrincipal user,
    @PathParam("id") final ObjectId id
  ) {
    return this.datastore.createQuery(Thing.class)
      .field("users.id").equal(user.entity().getId())
      .field("_id").equal(id).get();
  }

  @GET
  public List<Thing> fetchAll(@Auth final InsiderAuthPrincipal user) {
    return this.datastore.createQuery(Thing.class)
      .field("users.id").equal(user.entity().getId()).asList();
  }
}

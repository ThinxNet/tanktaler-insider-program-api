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

package de.tanktaler.insider.model.session;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiRelation;
import io.crnk.core.resource.annotations.JsonApiResource;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.time.Instant;

@Entity(value = "ip_session_segment", noClassnameStored = true)
@JsonApiResource(type = "session-segments")
public final class SessionSegment {
  @Id
  @JsonApiId
  private ObjectId id;

  @JsonApiRelation
  @Reference(idOnly = true, lazy = true)
  private SessionSummary session;

  private Instant timestamp;

  @Property("attributes")
  private Document props;

  private String device;

  public ObjectId getId() {
    return this.id;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  public Document getProps() {
    return this.props;
  }

  public String getDevice() {
    return this.device;
  }

  public SessionSummary getSession() {
    return this.session;
  }
}

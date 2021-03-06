/**
 * Copyright 2019 ThinxNet GmbH
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

package one.ryd.insider.core.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Objects;
import org.bson.types.ObjectId;

public final class ObjectIdSerialize extends JsonSerializer<ObjectId> {
  @Override
  public void serialize(
    final ObjectId objId, final JsonGenerator gen, final SerializerProvider serializers
  ) throws IOException {
    if (!Objects.isNull(objId)) {
      gen.writeString(objId.toString());
      return;
    }
    gen.writeNull();
  }
}

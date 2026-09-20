/*
 * Copyright 2026 hikingc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.hikingc.matrixsdk.api.rooms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.rooms.models.PublishedRoomsChunk;
import java.util.List;
import org.jspecify.annotations.NonNull;

/// Information about published rooms on the server.
///
/// @param chunk a paginated chunk of published rooms.
/// @param nextBatch a pagination token for the response. If null, then the record contains data of
///   the last page
/// @param prevBatch a pagination token that allows fetching previous results. If null, then this is
///   the first batch
/// @param totalRoomCountEstimate if available, an estimate on the total number of published rooms
public record PublicRoomDirectory(
    @NonNull @JsonProperty(required = true) List<PublishedRoomsChunk> chunk,
    String nextBatch,
    String prevBatch,
    Integer totalRoomCountEstimate) {}

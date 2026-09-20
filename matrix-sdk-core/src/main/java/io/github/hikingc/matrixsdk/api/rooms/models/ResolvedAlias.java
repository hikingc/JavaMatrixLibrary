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
package io.github.hikingc.matrixsdk.api.rooms.models;

import io.github.hikingc.matrixsdk.api.identifiers.RoomID;
import java.util.List;

/// Resolved Room alias information.
///
/// @param roomId the room id for the room alias.
/// @param servers a list of servers aware of said alias.
public record ResolvedAlias(RoomID roomId, List<String> servers) {}

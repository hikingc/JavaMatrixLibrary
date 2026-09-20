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

import java.util.List;
import org.jspecify.annotations.Nullable;

/// Details of the room filter.
///
/// @param genericSearchTerm a [String] to search for in the room metadata, for example: name,
///   topic, canonical alias, etc.
/// @param roomTypes a [java.util.List] of room types to search for. To include rooms without a room
///   type, specify null within this list. **When not specified, all applicable rooms (regardless of
///   type) are returned.**
public record RoomFilter(String genericSearchTerm, @Nullable List<String> roomTypes) {}

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
package io.github.hikingc.matrixsdk.api.filters;

import java.util.List;

/// Data points that can be used to restrict which events are returned to the client.
///
/// @param accountData the user account data that isn’t associated with rooms to include.
/// @param eventFields [List] of event fields to include. If this list is absent then all fields are
///   included. The entries are **dot-separated paths** for each property to include. So
///   `['content.body']` will include the body field of the content object. A server may include
///   more fields than were requested.
/// @param eventFormat The format to use for events. `client` will return the events in a format
///   suitable for clients. `federation` will return the raw event as received over federation. The
///   default is `client`.
/// @param presence the presence updates to include.
/// @param room filters to be applied to room data.
public record FilterDefinition(
    EventFilter accountData,
    List<String> eventFields,
    String eventFormat,
    EventFilter presence,
    RoomFilter room) {}

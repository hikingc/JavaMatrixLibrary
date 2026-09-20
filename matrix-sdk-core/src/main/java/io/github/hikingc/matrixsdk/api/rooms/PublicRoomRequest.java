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

/// Configuration parameters to query at the server's public rooms' directory.
///
/// @param filter to apply in the results.
/// @param includeAllNetworks whether to include all known networks/protocols from application
///   services on the homeserver.
/// @param limit of the number of results returned.
/// @param since a pagination token from a previous request, allowing clients to get the next (or
///   previous) batch of rooms. The direction of pagination is specified solely by which token is
///   supplied, rather than via an explicit flag.
/// @param thirdPartyInstanceId the specific third-party network/protocol to request from the
///   homeserver. Can only be used if `includeAllNetworks` is `false`.
/// @see <a
/// href="https://spec.matrix.org/v1.19/client-server-api/#get_matrixclientv3thirdpartyprotocols">
///   `/thirdparty/protocols`</a>, the endpoint that returns the instance_id of a `Protocol`
public record PublicRoomRequest(
    RoomFilter filter,
    Boolean includeAllNetworks,
    Integer limit,
    String since,
    String thirdPartyInstanceId) {}

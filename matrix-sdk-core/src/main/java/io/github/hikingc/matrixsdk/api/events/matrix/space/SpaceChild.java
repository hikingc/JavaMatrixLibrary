package io.github.hikingc.matrixsdk.api.events.matrix.space;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;
import java.util.List;
import org.jspecify.annotations.NonNull;

/// Content information where the relationship of a child room to a space-room is defined. Doesn't
/// have an effect in rooms that aren't spaces.
///
/// @param order optional string to define ordering among space children. These are
///   lexicographically compared against other children’s order, if present.
///
///   Must consist of ASCII characters within the range `\x20` (space) and `\x7E` (~), inclusive.
///   Must not exceed 50 characters.
///
///   `order` values with the wrong type, or otherwise invalid contents, are to be treated as though
///   the `order` key was not provided.
///
/// @param suggested optional (default `false`) flag to denote whether the child is “suggested” or
///   of interest to members of the space. This is primarily intended as a rendering hint for
///   clients to display the room differently, such as eagerly rendering them in the room list.
/// @param via a list of servers to try and join through.
///
///   When not present or invalid, the child room is not considered to be part of the space.
/// @see <a href="https://spec.matrix.org/v1.19/appendices/#routing">Routing in the spec.</a>
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#spaces">Spaces in spec.</a>
/// @see <a href="https://spec.matrix.org/v1.19/client-server-api/#ordering-of-children-within-a-space">
///   Ordering of spaces in spec. </a>
public record SpaceChild(
    String order, Boolean suggested, @NonNull @JsonProperty(required = true) List<String> via)
    implements StateEventContent {}

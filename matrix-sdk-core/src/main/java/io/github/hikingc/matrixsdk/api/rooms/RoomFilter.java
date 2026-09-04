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

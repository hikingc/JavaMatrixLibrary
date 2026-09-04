package io.github.hikingc.matrixsdk.api.events.matrix;

import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.*;

/// Marker interface for ephemeral content type events.
public sealed interface EphemeralContent
    permits EphemeralDirect,
        EphemeralFullyRead,
        EphemeralPresence,
        EphemeralReceipt,
        EphemeralTag,
        EphemeralTyping {}

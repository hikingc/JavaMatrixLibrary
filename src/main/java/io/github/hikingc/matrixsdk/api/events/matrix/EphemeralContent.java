package io.github.hikingc.matrixsdk.api.events.matrix;

import io.github.hikingc.matrixsdk.api.events.matrix.ephemeral.*;

public sealed interface EphemeralContent permits EphemeralDirect, EphemeralFullyRead, EphemeralPresence, EphemeralReceipt, EphemeralTag, EphemeralTyping {}

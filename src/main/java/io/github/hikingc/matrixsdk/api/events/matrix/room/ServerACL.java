package io.github.hikingc.matrixsdk.api.events.matrix.room;

import io.github.hikingc.matrixsdk.api.events.matrix.StateEventContent;

import java.util.List;

public record ServerACL(List<String> allow,
                        Boolean allowIpLiterals,
                        List<String> deny) implements StateEventContent {}

package org.Isa4.dto;

import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class RunStatus {

    public static AtomicBoolean logicRun = new AtomicBoolean(true);
}

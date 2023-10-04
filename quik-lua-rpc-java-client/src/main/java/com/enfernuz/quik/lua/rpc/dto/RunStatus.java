package com.enfernuz.quik.lua.rpc.dto;

import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class RunStatus {

    public static AtomicBoolean rpcRun = new AtomicBoolean(true);
}

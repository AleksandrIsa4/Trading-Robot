package com.enfernuz.quik.lua.rpc.events.api;

/**
 * Режим опроса удалённого RPC-сервиса <b>quik-lua-rpc</b> на предмет появления нового события API QLua терминала
 * QUIK.
 */
public enum PollingMode {

    /**
     * Блокирующее чтение очереди событий. Читающий поток заблокируется до получения нового события.
     */
    BLOCKING,

    /**
     * Неблокирующее чтение очереди событий. Читающий поток не блокируется при отсутствии новых событий.
     */
    NO_BLOCKING;
}

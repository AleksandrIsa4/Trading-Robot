package com.enfernuz.quik.lua.rpc.config;

import com.enfernuz.quik.lua.rpc.api.messages.Message;
import com.enfernuz.quik.lua.rpc.api.zmq.ZmqTcpQluaRpcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
public class ZmqTcpQluaRpcClientConfig {

    @Bean
    public ZmqTcpQluaRpcClient getZmqTcpQluaRpcClient() {
        final String filePath = "rpc-client-config.json";
        if (filePath == null) {
            log.error("Не задан путь до файла конфигурации.");
            System.exit(1);
        }
        File configFile = null;
        try {
            configFile = new File(filePath);
        } catch (final Exception ex) {
            log.error(String.format("Не удалось прочитать файл '%s'.", filePath), ex);
            System.exit(1);
        }
        log.info("Чтение файла конфигурации...");
        ClientConfiguration config = null;
        try {
            config = JsonClientConfigurationReader.INSTANCE.read(configFile);
        } catch (final Exception ex) {
            log.error(String.format("Не удалось получить объект конфигурации из файла '%s'.", filePath), ex);
            System.exit(1);
        }
        log.info("Инициализация клиента...");
        final ZmqTcpQluaRpcClient rpcClient = ZmqTcpQluaRpcClient.newInstance(config);
        try {
            log.info("Соединение с RPC-сервисом...");
            rpcClient.open();
            log.info("Выполнение удалённой процедуры 'message' на терминале QUIK...");
            final Integer result = rpcClient.qlua_message("Hello, world!", Message.IconType.WARNING);
            log.info("Результат в result.Args: {}.", result);
            if (result == null) {
                log.error("Удалённая процедура 'message' выполнилась с ошибкой.");
            } else {
                log.info("Результат выполнения удалённой процедуры 'message': {}.", result);
            }
            log.info("Выход из программы...");
        } catch (
                final Exception ex) {
            log.error("Не удалось выполнить удалённый вызов процедуры.", ex);
            System.exit(1);
        }
        return rpcClient;
    }
}

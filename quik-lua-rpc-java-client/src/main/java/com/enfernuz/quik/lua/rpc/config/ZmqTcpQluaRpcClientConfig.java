package com.enfernuz.quik.lua.rpc.config;

import com.enfernuz.quik.lua.rpc.api.messages.Message;
import com.enfernuz.quik.lua.rpc.api.zmq.ZmqTcpQluaEventProcessor;
import com.enfernuz.quik.lua.rpc.api.zmq.ZmqTcpQluaRpcClient;
import com.enfernuz.quik.lua.rpc.events.api.LoggingEventHandler;
import com.enfernuz.quik.lua.rpc.events.api.PollingMode;
import com.enfernuz.quik.lua.rpc.events.api.QluaEvent;
import com.enfernuz.quik.lua.rpc.events.api.QluaEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Bean
    public ZmqTcpQluaEventProcessor getZmqTcpQluaEventProcessor() {
        final String filePath = "subscription-client-config.json";
        if (filePath == null) {
            log.error("Не задан путь до файла конфигурации.");
            System.exit(1);
        }

        File configFile=null;
        try {
            configFile = new File(filePath);
        } catch (final Exception ex) {
            log.error(String.format("Не удалось прочитать файл '%s'.", filePath), ex);
            System.exit(1);
        }

        log.info("Чтение файла конфигурации...");
        ClientConfiguration config=null;
        try {
            config = JsonClientConfigurationReader.INSTANCE.read(configFile);
        } catch (final Exception ex) {
            log.error(String.format("Не удалось получить объект конфигурации из файла '%s'.", filePath), ex);
            System.exit(1);
        }

        log.info("Инициализация клиента...");
        final ExecutorService stdinScannerExecutorService = Executors.newSingleThreadExecutor();
       // final ZmqTcpQluaEventProcessor eventProcessor = ZmqTcpQluaEventProcessor.newInstance(config, PollingMode.BLOCKING);
        final ZmqTcpQluaEventProcessor eventProcessor = ZmqTcpQluaEventProcessor.newInstance(config, PollingMode.NO_BLOCKING);
        try  {

            log.info("Подписка на все события...");
            eventProcessor.subscribe(QluaEvent.EventType.ON_TRANS_REPLY);
          //  eventProcessor.subscribeToEverything();

            log.info("Регистрация обработчиков событий...");
            eventProcessor.register(LoggingEventHandler.INSTANCE);

            log.info("Соединение с RPC-сервисом...");
            eventProcessor.open();

            // monitor the Enter key pressing
            final AtomicBoolean enough = new AtomicBoolean(false);
            stdinScannerExecutorService.execute(() -> {

                try {
                    enough.set(System.in.read() > 0);
                } catch (final Exception ex) {
                    log.error("Ошибка при чтении стандартного потока ввода.", ex);
                }
            });

            log.info("Начало обработки событий. Нажмите Enter для остановки...");
/*            while ( !enough.get() ) {
                eventProcessor.process(1);
            }*/


            log.info("Выход из программы...");
        } catch (final QluaEventProcessor.QluaEventProcessingException ex) {
            log.error("Ошибка при обработке события.", ex);
        } catch (final Exception ex) {
            log.error("Не удалось начать обработку событий.", ex);
        }
        return eventProcessor;
/*        finally {
            stdinScannerExecutorService.shutdownNow();
        }*/
    }
}

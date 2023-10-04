# Trading-Robot

Торговый робот на Java через терминал QUIK, с помощью Protocol Buffers. Связь между модулями приложения через Kafka.

За основу взят проект
[quik-lua-rpc-java-client](https://github.com/Enfernuz/quik-lua-rpc-java-client) и [quik-lua-rpc](https://github.com/Enfernuz/quik-lua-rpc)

Сборка и установка
1. Установите Docker и Docker-compose для вашей операционной системы.
2. Установите терминал QUIK.
3. Установите [quik-lua-rpc](https://github.com/Enfernuz/quik-lua-rpc).
4. Установите терминал Protocol Buffers.
5. Клонируйте этот репозиторий
6. Откройте консоль в каталоге репозитория.
7. Соберите образ Docker c брокером сообщения KAFKA и базой данной PostgreSQL. docker-compose up
8. Собрать проект в Maven с помощью команды: "mvn clean package"

![img](https://github.com/cochachyLE-Dev/ProyectoBancoS1-Service1/blob/main/Diagram-Arquitecture.PNG)


```ps1
## Create directories: 
#rm broker_1, broker_2, broker_3, zookeeper
#md broker_1, broker_2, broker_3, zookeeper
```

configuration of <code>zookeeper.properties</code>

```conf
# the directory where the snapshot is stored.
dataDir=C:/Kafka/data/zookeeper
# the port at which the clients will connect
clientPort=2181
# disable the per-ip limit on the number of connections since this is a non-production config
maxClientCnxns=0
# Disable the adminserver by default to avoid port conflicts.
# Set the port to something non-conflicting if choosing to enable this
admin.enableServer=false
# admin.serverPort=8080
```

configuration of <code>server1.properties</code>

```conf
############################# Server Basics #############################
# The id of the broker. This must be set to a unique integer for each broker.
broker.id=1

############################# Socket Server Settings #############################
# The address the socket server listens on.
listeners=PLAINTEXT://:9092
delete.topic.enabled=true

############################# Log Basics #############################
# A comma separated list of directories under which to store log files
log.dirs=C:/Kafka/data/broker_1/logs

############################# Zookeeper #############################
# Zookeeper connection
zookeeper.connect=localhost:2181

# Timeout in ms for connecting to zookeeper
zookeeper.connection.timeout.ms=18000
```

```ps1
## Iniciar Zookeeper
zookeeper-server-start $env:KAFKA_CONFIG\zookeeper.properties

## Iniciar Apache Kafka
kafka-server-start $env:KAFKA_CONFIG\server1.properties

## Listar brokers
zookeeper-shell localhost:2181 ls /brokers/ids

## Crear tópico
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic1

## Listar tópicos
kafka-topics --list --zookeeper localhost:2181

## Eliminar tópico
kafka-topics --bootstrap-server localhost:9091 --delete --topic topic1

## Crear productor
kafka-console-producer --broker-list localhost:9091 --topic topic1

## Crear consumidor
kafka-console-consumer --bootstrap-server localhost:9091 --group group1 --topic topic1 --from-beginning

## listar grupos
kafka-consumer-groups --list --bootstrap-server localhost:9091

## eliminar grupo
#kafka-consumer-groups --bootstrap-server localhost:9091, localhost:9092, localhost:9093 --delete --group group1

## ver suscripción de grupo en tópico
kafka-consumer-groups --bootstrap-server localhost:9091 --describe --group group1

## Detener Apache Kafka
kafka-server-stop --bootstrap-server localhost:9091

## Detener Zookeeper
zookeeper-server-stop
```


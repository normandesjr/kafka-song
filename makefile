ENV_KEYS=`cat .env`

init-ksql:
	confluent start ksql-server
	kafka-topics --if-not-exists --create --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1 --topic current_song 
	kafka-topics --if-not-exists --create --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1 --topic guesses
	ksql < ksql/init.sql

destroy-ksql:
	confluent destroy


init-servers:
	java ${ENV_KEYS} -jar spotify-polling/target/spotify-polling-0.0.1-SNAPSHOT.jar
	
start-guesses:
	ksql-datagen schema=./datagen/guesses.avro format=json topic=guesses key=user maxInterval=500 iterations=10000


# docker run -d --name=grafana -p 3000:3000 grafana/grafana 
# docker run -d --name elasticsearch  -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:6.5.0
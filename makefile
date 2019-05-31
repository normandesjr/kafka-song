init-ksql:
	confluent start ksql-server
	kafka-topics --if-not-exists --create --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1 --topic current_song 
	kafka-topics --if-not-exists --create --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1 --topic guesses
	ksql < ksql/init.sql

destroy-ksql:
	confluent destroy

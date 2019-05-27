# Kafka Song

The main goal of this project is to show how KSQL (https://www.confluent.io/product/ksql/) works. KSQL is the streaming SQL engine that enables real-time data processing along with Apache Kafka.

Bellow you will find some guidance to setup the project to work locally.

## Confluent Platform

To startup all the servers I suggest you to use Confluent Platform (I used version 5.2.1).

You can download it in this link: https://www.confluent.io/download/. After extract, add it to the PATH variable.

To start all the 4 servers (Zookeeper, Kafka, Schema Registry and KSQL Server) type the command:

```
$ confluent start ksql-server
```

## Twitter

To be able to get tweets from Twitter you need to set two environment variables *twitter.consumerKey* and *twitter.consumerSecret*.

You will get these variables in https://developer.twitter.com/ creating a new app. 

## Spotify

To be able to get the song from Spotify you need three variables, *spotify.refreshToken*, *spotify.clientId* and *spotify.clientSecret*.

You will get the *spotify.clientId* and *spotify.clientSecret* in https://developer.spotify.com/ at the Dashboard page.

Tip: the Spotify form will ask you for a "redirect URI", you can add something like: http://localhost/callback. Do not matter if you have this application up and running, for this purpose will be ok. 

### Spotify - OAuth

To generate the *spotify.refreshToken* Spotify use OAuth Authorization Code Flow (https://developer.spotify.com/documentation/general/guides/authorization-guide/). 

As this is a demo application, you can use Postman (https://www.getpostman.com/) to get the refresh token.

1. Getting *the code*

Open Google Chrome and type the URL bellow adding your client_id and the redirect_uri that you used when you created the app: 

[https://accounts.spotify.com/authorize?client_id=<your_client_id>&response_type=code&redirect_uri=<your_redirect_uri>&scope=user-read-currently-playing]

Now Chrome will show an URL like: [http://localhost/callback?code=<the_code_is_shown_here>]

Copy the *code* and save it, you'll need it to generate the *refreshToken*.

2. Getting the *refresh token*

Now using Postman to create a POST request for https://accounts.spotify.com/api/token

Set the Content-Type header to application/x-www-form-urlencoded

Add a basic auth with your clientId and clientSecret

At body you'll add three parameters:

* *grant_type* with the value authorization_code
* *code* with the value you got earlier with Chrome
* *redirect_uri* with the value you added to create the app

Make the request. The response JSON will have the *refresh_token* parameter. 
    
## Generate guesses (tweet simulator)

You can generate guesses directly to a topic using the datagen/guesses.avro file. The command is:

```
ksql-datagen schema=./datagen/guesses.avro format=json topic=guesses key=user maxInterval=5000 iterations=10000
```


## Streams and Tables

At KSQL you have to create streams and tables to be able to see the winners.

Current Song: 

```
CREATE STREAM SONG_WRAPPER (NAME VARCHAR, ARTIST VARCHAR) WITH (KAFKA_TOPIC='current_song', VALUE_FORMAT='JSON');
CREATE STREAM SONG_STAGE_1 with (partitions=1) AS SELECT 'CURRENT_SONG' AS CURRENT_SONG, UCASE(NAME) AS SONG_KEY, NAME, ARTIST FROM SONG_WRAPPER PARTITION BY CURRENT_SONG;
CREATE TABLE SONG (CURRENT_SONG VARCHAR, SONG_KEY VARCHAR, NAME VARCHAR, ARTIST VARCHAR) WITH (KAFKA_TOPIC='SONG_STAGE_1', VALUE_FORMAT='JSON', KEY='CURRENT_SONG');
```

Winner:

```
CREATE STREAM GUESSES_WRAPPER (SONG VARCHAR, USER VARCHAR) WITH (KAFKA_TOPIC='guesses', VALUE_FORMAT='JSON');
CREATE STREAM GUESSES_STAGE_1 AS SELECT UCASE(SONG) AS SONG_KEY, SONG, USER FROM GUESSES_WRAPPER;
CREATE STREAM GUESSES_STAGE_2 AS SELECT SONG_KEY, SONG, USER, 'CURRENT_SONG' AS CURRENT_SONG FROM GUESSES_STAGE_1;
CREATE STREAM GUESSES_STAGE_3 AS SELECT ROWTIME AS TIMESTAMP, SONG_KEY, SONG, USER, 'CURRENT_SONG' AS CURRENT_SONG FROM GUESSES_STAGE_2 PARTITION BY TIMESTAMP;
CREATE STREAM GUESSES_STAGE_4 AS SELECT TIMESTAMP, USER FROM GUESSES_STAGE_3 G LEFT JOIN SONG S ON G.CURRENT_SONG = S.CURRENT_SONG WHERE G.SONG_KEY = S.SONG_KEY;
CREATE STREAM GUESSES_STAGE_5 AS SELECT TIMESTAMP, USER FROM GUESSES_STAGE_4 PARTITION BY TIMESTAMP;
CREATE STREAM WINNERS AS SELECT USER FROM GUESSES_STAGE_5;
```

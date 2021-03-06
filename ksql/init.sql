CREATE STREAM SONG_WRAPPER (NAME VARCHAR, ARTIST VARCHAR) WITH (KAFKA_TOPIC='current_song', VALUE_FORMAT='JSON');
CREATE STREAM SONG_STAGE_1 with (partitions=1) AS SELECT 'CURRENT_SONG' AS CURRENT_SONG, UCASE(NAME) AS SONG_KEY, NAME, ARTIST FROM SONG_WRAPPER PARTITION BY CURRENT_SONG;
CREATE TABLE SONG (CURRENT_SONG VARCHAR, SONG_KEY VARCHAR, NAME VARCHAR, ARTIST VARCHAR) WITH (KAFKA_TOPIC='SONG_STAGE_1', VALUE_FORMAT='JSON', KEY='CURRENT_SONG');
CREATE STREAM GUESSES_WRAPPER (SONG VARCHAR, USER VARCHAR) WITH (KAFKA_TOPIC='guesses', VALUE_FORMAT='JSON');
CREATE STREAM GUESSES_STAGE_1 with (partitions=1) AS SELECT UCASE(SONG) AS SONG_KEY, SONG, USER FROM GUESSES_WRAPPER;
CREATE STREAM GUESSES_STAGE_2 with (partitions=1) AS SELECT SONG_KEY, SONG, USER, 'CURRENT_SONG' AS CURRENT_SONG FROM GUESSES_STAGE_1;
CREATE STREAM GUESSES_STAGE_3 with (partitions=1) AS SELECT ROWTIME AS TIMESTAMP, SONG_KEY, SONG, USER, 'CURRENT_SONG' AS CURRENT_SONG FROM GUESSES_STAGE_2 PARTITION BY TIMESTAMP;
CREATE STREAM GUESSES_STAGE_4 with (partitions=1) as SELECT TIMESTAMP, USER FROM GUESSES_STAGE_3 G LEFT JOIN SONG S ON G.CURRENT_SONG = S.CURRENT_SONG WHERE G.SONG_KEY = S.SONG_KEY;
CREATE STREAM WINNERS with (partitions=1) AS SELECT TIMESTAMP, USER FROM GUESSES_STAGE_4 PARTITION BY TIMESTAMP;

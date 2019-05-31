/* eslint-disable  func-names */
/* eslint-disable  no-console */

const Alexa = require('ask-sdk-core');
const firebaseAdmin = require('firebase-admin');
const AWS = require('aws-sdk');
const S3 = new AWS.S3({region: process.env.AWS_REGION, apiVersion: '2006-03-01'});
const params = 
{
  Bucket: 'kafka-song', 
  Key: 'kafka-song-firebase-admin.json' 
}

var promiseDb = S3.getObject(params).promise().then(r => {
  var serviceAccount = JSON.parse(r.Body);
  firebaseAdmin.initializeApp({
    credential: firebaseAdmin.credential.cert(serviceAccount)
  });
  
  return firebaseAdmin.firestore();
});

const WinnerIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'WinnerIntent';
  },

  handle(handlerInput) {
    return promiseDb
      .then(db => db.collection('winners').orderBy('time', 'asc').limit(1).get())
      .then((winners) => {
        var speechText = 'There is no winner yet';
        winners.forEach((winnerDocument) => {
          var winner = winnerDocument.data();
          speechText = 'The winner is ' + winner.name;
        });
        return speechText;
      })
      .then(speechText => 
        handlerInput.responseBuilder
          .speak(speechText)
          .getResponse())
      .catch((err) => {
        console.log('Error getting documents', err);
        return handlerInput.responseBuilder
          .speak('Sorry, some error happened finding the winner')
          .getResponse();
      });
  }
};

const HelpIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'AMAZON.HelpIntent';
  },
  handle(handlerInput) {
    const speechText = 'You can ask me who is the winner!';

    return handlerInput.responseBuilder
      .speak(speechText)
      .reprompt(speechText)
      .getResponse();
  }
};

const ErrorHandler = {
  canHandle() {
    return true;
  },
  handle(handlerInput, error) {
    console.log(`Error handled: ${error.message}`);

    return handlerInput.responseBuilder
      .speak('Sorry, I can\'t understand. Please say again.')
      .getResponse();
  }
};

const skillBuilder = Alexa.SkillBuilders.custom();

exports.kafkaSong = skillBuilder
  .addRequestHandlers(
    WinnerIntentHandler,
    HelpIntentHandler
  )
  .addErrorHandlers(ErrorHandler)
  .lambda();

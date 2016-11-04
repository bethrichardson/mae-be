'use strict';

var APP_ID = 'amzn1.ask.skill.443cde2f-8637-4f9a-ae98-614b7ec19582';
var http = require('http');
var queryString = require('querystring');

/**
 * This sample demonstrates a simple skill built with the Amazon Alexa Skills Kit.
 * The Intent Schema, Custom Slots, and Sample Utterances for this skill, as well as
 * testing instructions are located at http://amzn.to/1LzFrj6
 *
 * For additional samples, visit the Alexa Skills Kit Getting Started guide at
 * http://amzn.to/1LGWsLG
 */


// --------------- Helpers that build all of the responses -----------------------

function buildSpeechletResponse(title, output, repromptText, shouldEndSession) {
    return {
        outputSpeech: {
            type: 'PlainText',
            text: output,
        },
        card: {
            type: 'Simple',
            title: `SessionSpeechlet - ${title}`,
            content: `SessionSpeechlet - ${output}`,
        },
        reprompt: {
            outputSpeech: {
                type: 'PlainText',
                text: repromptText,
            },
        },
        shouldEndSession,
    };
}

function buildResponse(sessionAttributes, speechletResponse) {
    return {
        version: '1.0',
        sessionAttributes,
        response: speechletResponse
    };
}


// --------------- Functions that control the skill's behavior -----------------------

function getWelcomeResponse(callback) {
    // If we wanted to initialize the session to have some attributes we could add those here.
    const sessionAttributes = {};
    const cardTitle = 'Welcome';
    const speechOutput = 'Welcome to Mae. ' +
        'Would you like to record a new journal entry?';
    // If the user either does not reply to the welcome message or says something that is not
    // understood, they will be prompted again with this text.
    const repromptText = 'Please record a new journal entry by speaking it outloud';
    const shouldEndSession = false;

    callback(sessionAttributes,
        buildSpeechletResponse(cardTitle, speechOutput, repromptText, shouldEndSession));
}

function handleSessionEndRequest(callback) {
    const cardTitle = 'Session Ended';
    const speechOutput = 'Thank you for speaking with Mae today. Have a nice day!';
    // Setting this to true ends the session and exits the skill.
    const shouldEndSession = true;

    callback({}, buildSpeechletResponse(cardTitle, speechOutput, null, shouldEndSession));
}

function createJournalAttributes(journal) {
    return {
        journal,
    };
}

/**
 * Sets the journal in the session and prepares the speech to reply to the user.
 */
function setJournalInSession(intent, session, callback) {
    const intentName = intent.name;
    const journalSlot = intent.slots.Journal;
    let repromptText = '';
    let sessionAttributes = {};
    const shouldEndSession = false;
    let speechOutput = '';
    let requestType = "journal";

    if (intentName === 'MyHeightIsIntent') {
        requestType = "height";
    } else if (intentName === 'MyWeightIsIntent') {
        requestType = "weight";
    } else if (intentName === 'MySleepIsIntent') {
        requestType = "sleep";
    } else if (intentName === 'MyDiaperIsIntent') {
        requestType = "diaper";
    }

    var endpoint = 'mae-be.herokuapp.com';
    if (journalSlot) {
        var data = {
            type: requestType,
            value: journalSlot.value
        };

        var options = {
            host: endpoint,
            path: '/journals',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        };

        var body = JSON.stringify(data);

        var req = http.request(options, function (res) {

            // Collect response data as it comes back.
            var responseString = '';
            res.on('data', function (data) {
                responseString += data;
            });

            // Log the responce received from Mae.
            // Or could use JSON.parse(responseString) here to get at individual properties.
            res.on('end', function () {
                console.log('Maebe Response: ' + responseString);
                journalSlot.value = responseString;
                finishWithJournal(journalSlot)
            });

            // Handler for HTTP request errors.
            req.on('error', function (e) {
                console.error('HTTP error: ' + e.message);
                finishWithJournal(journalSlot)
            });
        });

        req.write(body);
        req.end();

    } else {
        speechOutput = "I'm not sure I recorded a journal entry. Please try again.";
        repromptText = "I'm not sure what your journal entry is. You can tell me your " +
            'entry by saying, my entry is.';
        callback(sessionAttributes,
            buildSpeechletResponse(intentName, speechOutput, repromptText, shouldEndSession));
    }

    function finishWithJournal(journalSlot) {
        const journal = journalSlot.value;
        sessionAttributes = createJournalAttributes(journal);
        speechOutput = `${journal}`;
        repromptText = "You can ask me your journal entry by saying, what's my journal entry?";

        callback(sessionAttributes,
            buildSpeechletResponse(intentName, speechOutput, repromptText, shouldEndSession));

    }

    // http.get(endpoint, data, function (res) {
    //     console.log('Status Code: ' + res.statusCode);
    //
    //     res.on('data', function (data) {
    //         journalResponseString += data;
    //     });
    //
    //     res.on('end', function () {
    //         var speechResponseObject = JSON.parse(journalResponseString);
    //
    //         if (speechResponseObject.error) {
    //             console.log("Mae error: " + speechResponseObject.error.message);
    //         } else {
    //             journalResponseString = speechResponseObject;
    //         }
    //     });
    // }).on('error', function (e) {
    //     console.log("Communications error: " + e.message);
    // });


}

function getJournalFromSession(intent, session, callback) {
    let journal;
    const repromptText = null;
    const sessionAttributes = {};
    let shouldEndSession = false;
    let speechOutput = '';

    if (session.attributes) {
        journal = session.attributes.Journal;
    }

    if (journal) {
        speechOutput = `Your journal entry is ${journal}. Goodbye.`;
        shouldEndSession = true;
    } else {
        speechOutput = "I'm not sure what your journal is, you can say it now.";
    }

    // Setting repromptText to null signifies that we do not want to reprompt the user.
    // If the user does not respond or says something that is not understood, the session
    // will end.
    callback(sessionAttributes,
        buildSpeechletResponse(intent.name, speechOutput, repromptText, shouldEndSession));
}


// --------------- Events -----------------------

/**
 * Called when the session starts.
 */
function onSessionStarted(sessionStartedRequest, session) {
    console.log(`onSessionStarted requestId=${sessionStartedRequest.requestId}, sessionId=${session.sessionId}`);
}

/**
 * Called when the user launches the skill without specifying what they want.
 */
function onLaunch(launchRequest, session, callback) {
    console.log(`onLaunch requestId=${launchRequest.requestId}, sessionId=${session.sessionId}`);

    // Dispatch to your skill's launch.
    getWelcomeResponse(callback);
}

/**
 * Called when the user specifies an intent for this skill.
 */
function onIntent(intentRequest, session, callback) {
    console.log(`onIntent requestId=${intentRequest.requestId}, sessionId=${session.sessionId}`);

    const intent = intentRequest.intent;
    const intentName = intentRequest.intent.name;

    // Dispatch to your skill's intent handlers
    if (intentName === 'MyJournalIsIntent' || intentName === 'MyWeightIsIntent' || intentName === 'MySleepIsIntent'
        || intentName === 'MyHeightIsIntent' || intentName === 'MyDiaperIsIntent') {
        setJournalInSession(intent, session, callback);
    } else if (intentName === 'WhatsMyJournalIntent') {
        getJournalFromSession(intent, session, callback);
    } else if (intentName === 'AMAZON.HelpIntent') {
        getWelcomeResponse(callback);
    } else if (intentName === 'AMAZON.StopIntent' || intentName === 'AMAZON.CancelIntent') {
        handleSessionEndRequest(callback);
    } else {
        throw new Error('Invalid intent');
    }
}

/**
 * Called when the user ends the session.
 * Is not called when the skill returns shouldEndSession=true.
 */
function onSessionEnded(sessionEndedRequest, session) {
    console.log(`onSessionEnded requestId=${sessionEndedRequest.requestId}, sessionId=${session.sessionId}`);
    // Add cleanup logic here
}


// --------------- Main handler -----------------------

// Route the incoming request based on type (LaunchRequest, IntentRequest,
// etc.) The JSON body of the request is provided in the event parameter.
exports.handler = (event, context, callback) => {
    try {
        console.log(`event.session.application.applicationId=${event.session.application.applicationId}`);

        /**
         * Uncomment this if statement and populate with your skill's application ID to
         * prevent someone else from configuring a skill that sends requests to this function.
         */
        /*
         if (event.session.application.applicationId !== 'amzn1.echo-sdk-ams.app.[unique-value-here]') {
         callback('Invalid Application ID');
         }
         */

        if (event.session.new) {
            onSessionStarted({ requestId: event.request.requestId }, event.session);
        }

        if (event.request.type === 'LaunchRequest') {
            onLaunch(event.request,
                event.session,
                (sessionAttributes, speechletResponse) => {
                    callback(null, buildResponse(sessionAttributes, speechletResponse));
                });
        } else if (event.request.type === 'IntentRequest') {
            onIntent(event.request,
                event.session,
                (sessionAttributes, speechletResponse) => {
                    callback(null, buildResponse(sessionAttributes, speechletResponse));
                });
        } else if (event.request.type === 'SessionEndedRequest') {
            onSessionEnded(event.request, event.session);
            callback();
        }
    } catch (err) {
        callback(err);
    }
};
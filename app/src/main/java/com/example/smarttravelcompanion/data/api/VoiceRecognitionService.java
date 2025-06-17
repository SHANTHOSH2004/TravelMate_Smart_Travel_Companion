package com.example.smarttravelcompanion.data.api;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;

public class VoiceRecognitionService {
    private static final String TAG = "VoiceRecognitionService";
    private static VoiceRecognitionService instance;
    private final Context context;
    private SpeechRecognizer speechRecognizer;

    private VoiceRecognitionService(Context context) {
        this.context = context.getApplicationContext();
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        } else {
            Log.e(TAG, "Speech recognition is not available on this device");
        }
    }

    public static synchronized VoiceRecognitionService getInstance(Context context) {
        if (instance == null) {
            instance = new VoiceRecognitionService(context);
        }
        return instance;
    }

    public interface VoiceRecognitionCallback {
        void onSuccess(String text);
        void onError(String error);
    }

    public void startListening(VoiceRecognitionCallback callback) {
        if (speechRecognizer == null) {
            callback.onError("Speech recognition is not available on this device");
            return;
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        speechRecognizer.setRecognitionListener(new android.speech.RecognitionListener() {
            @Override
            public void onReadyForSpeech(android.os.Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {
                String errorMessage;
                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        errorMessage = "Audio recording error";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        errorMessage = "Client side error";
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        errorMessage = "Insufficient permissions";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        errorMessage = "Network error";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        errorMessage = "Network timeout";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        errorMessage = "No match found";
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        errorMessage = "RecognitionService busy";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        errorMessage = "Server error";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        errorMessage = "No speech input";
                        break;
                    default:
                        errorMessage = "Unknown error";
                        break;
                }
                callback.onError(errorMessage);
            }

            @Override
            public void onResults(android.os.Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    callback.onSuccess(matches.get(0));
                } else {
                    callback.onError("No speech recognized");
                }
            }

            @Override
            public void onPartialResults(android.os.Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, android.os.Bundle params) {}
        });

        speechRecognizer.startListening(intent);
    }

    public void shutdown() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }
} 
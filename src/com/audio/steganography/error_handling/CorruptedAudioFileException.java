package com.audio.steganography.error_handling;

public class CorruptedAudioFileException extends StegoException {
    public CorruptedAudioFileException(String message) {
        super(message);  // Pass the message to the base class constructor
    }
}

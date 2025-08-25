package com.audio.steganography.error_handling;

public class ShortAudioFileException extends StegoException {
    public ShortAudioFileException(String message) {
        super(message);  // Pass the message to the base class constructor
    }
}

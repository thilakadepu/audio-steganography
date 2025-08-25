package com.audio.steganography.core;

import java.nio.charset.StandardCharsets;

public class LSBEncoder {

    public static byte[] encodeMessage(byte[] audioBytes, String message) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        int messageLength = messageBytes.length;

        int start = 44; // Skip WAV header
        int bitsNeeded = (messageLength + 4) * 8;

        if ((audioBytes.length - start) < bitsNeeded) {
            throw new IllegalArgumentException("Not enough space in audio file.");
        }

        byte[] stego = audioBytes.clone();

        // Store message length in first 32 bits
        for (int i = 0; i < 32; i++) {
            int bit = (messageLength >>> (31 - i)) & 1;
            stego[start + i] = (byte)((stego[start + i] & 0xFE) | bit);
        }

        // Store message bytes
        for (int i = 0; i < messageBytes.length; i++) {
            for (int bit = 0; bit < 8; bit++) {
                int bitVal = (messageBytes[i] >>> (7 - bit)) & 1;
                int index = start + 32 + i * 8 + bit;
                stego[index] = (byte)((stego[index] & 0xFE) | bitVal);
            }
        }

        return stego;
    }
}

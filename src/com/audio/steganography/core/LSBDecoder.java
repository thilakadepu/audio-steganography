package com.audio.steganography.core;

public class LSBDecoder {

    public static String decodeMessage(byte[] audioBytes) {
        int start = 44;

        int length = 0;
        for (int i = 0; i < 32; i++) {
            int bit = audioBytes[start + i] & 1;
            length = (length << 1) | bit;
        }

        byte[] message = new byte[length];
        for (int i = 0; i < length; i++) {
            int b = 0;
            for (int j = 0; j < 8; j++) {
                int bit = audioBytes[start + 32 + i * 8 + j] & 1;
                b = (b << 1) | bit;
            }
            message[i] = (byte)b;
        }

        return new String(message);
    }
}

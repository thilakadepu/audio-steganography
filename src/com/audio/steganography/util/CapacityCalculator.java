package com.audio.steganography.util;

public class CapacityCalculator {

    public static final int HEADER_SIZE = 44;
    public static final int LENGTH_BITS = 32;

    public static int getMaxBase64Chars(byte[] audioBytes) {
        int usableBits = audioBytes.length - HEADER_SIZE - LENGTH_BITS;
        return usableBits / 8;
    }

    public static int getMaxPlainTextChars(byte[] audioBytes) {
        int maxBase64Chars = getMaxBase64Chars(audioBytes);
        int base64PerBlock = 344;
        int plainPerBlock = 245;

        int numBlocks = maxBase64Chars / base64PerBlock;
        return numBlocks * plainPerBlock;
    }

    public static int getMaxEncryptedBytes(byte[] audioBytes) {
        int usableBits = audioBytes.length - HEADER_SIZE - LENGTH_BITS;
        return usableBits / 8;
    }
}

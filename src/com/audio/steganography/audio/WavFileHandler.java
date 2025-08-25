package com.audio.steganography.audio;

import javax.sound.sampled.*;
import java.io.*;

public class WavFileHandler {

    private byte[] audioBytes;
    private AudioFormat format;

    public void loadWavFile(String filePath) throws Exception {
        File file = new File(filePath);
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(file)) {
            format = stream.getFormat();
            audioBytes = stream.readAllBytes();
        }
    }

    public void writeWavFile(String filePath, byte[] data) throws Exception {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             AudioInputStream audioStream = new AudioInputStream(bais, format, data.length / format.getFrameSize())) {
            File outFile = new File(filePath);
            AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, outFile);
        }
    }

    public byte[] getAudioBytes() {
        return audioBytes;
    }
}

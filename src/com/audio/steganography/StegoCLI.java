package com.audio.steganography;

import com.audio.steganography.audio.WavFileHandler;
import com.audio.steganography.core.LSBEncoder;
import com.audio.steganography.core.LSBDecoder;
import com.audio.steganography.crypto.RSAUtils;
import com.audio.steganography.error_handling.*;
import com.audio.steganography.util.CapacityCalculator;

import java.io.File;
import java.util.Base64;
import java.util.Scanner;

public class StegoCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Audio Steganography CLI");

        try {
            while (true) {
                System.out.println("\nDo you wish to encode or decode an audio file?");
                System.out.print("Type 'encode', 'decode', or 'q' to quit: ");
                String command = scanner.nextLine().trim().toLowerCase();

                switch (command) {
                    case "encode":
                        handleEncode(scanner);
                        break;
                    case "decode":
                        handleDecode(scanner);
                        break;
                    case "q":
                        System.out.println("Program has terminated.");
                        return;
                    default:
                        System.out.println("Invalid command.");
                }
            }
        } catch (Exception e) {
            System.out.println("Exited due to error.");
        } finally {
            scanner.close();
        }
    }

    private static void handleEncode(Scanner scanner) {
        try {
            listFiles("input");

            System.out.print("Enter input WAV filename (from /input/): ");
            String inputPath = "input/" + ensureWav(scanner.nextLine());

            WavFileHandler handler = new WavFileHandler();
            handler.loadWavFile(inputPath);
            byte[] audioBytes = handler.getAudioBytes();

            int maxChars = CapacityCalculator.getMaxPlainTextChars(audioBytes);
            System.out.println("Maximum encodable message length: " + maxChars + " characters");

            System.out.print("Enter your secret message: ");
            String message = scanner.nextLine();
            if (message.length() > maxChars) {
                throw new ShortAudioFileException("Message too long for this audio file.");
            }

            RSAUtils rsa = new RSAUtils();
            byte[] encrypted = rsa.encrypt(message);
            String base64 = Base64.getEncoder().encodeToString(encrypted);

            byte[] stegoBytes = LSBEncoder.encodeMessage(audioBytes, base64);

            System.out.print("Enter output WAV filename (to save in /output/): ");
            String outputPath = "output/" + ensureWav(scanner.nextLine());
            handler.writeWavFile(outputPath, stegoBytes);

            System.out.println("Message encoded successfully!");

        } catch (StegoException e) {
            System.out.println("Stego Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void handleDecode(Scanner scanner) {
        try {
            listFiles("output");

            System.out.print("Enter WAV filename to decode (from /output/): ");
            String inputPath = "output/" + ensureWav(scanner.nextLine());

            WavFileHandler handler = new WavFileHandler();
            handler.loadWavFile(inputPath);
            byte[] audioBytes = handler.getAudioBytes();

            String base64 = LSBDecoder.decodeMessage(audioBytes);
            byte[] encrypted = Base64.getDecoder().decode(base64);

            RSAUtils rsa = new RSAUtils();
            String message = rsa.decrypt(encrypted);

            System.out.println("Decoded Hidden Message: " + message);

        } catch (StegoException e) {
            System.out.println("Stego Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void listFiles(String dirPath) {
        System.out.println("\nAvailable WAV files in '" + dirPath + "':");
        File dir = new File(dirPath);
        String[] files = dir.list((d, name) -> name.toLowerCase().endsWith(".wav"));
        if (files != null) {
            for (String file : files) {
                System.out.println("  " + file);
            }
        }
    }

    private static String ensureWav(String name) {
        return name.toLowerCase().endsWith(".wav") ? name : name + ".wav";
    }
}

package com.audio.steganography.crypto;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;

public class RSAUtils {

    private final KeyPair keyPair;
    private static final String PUBLIC_KEY_FILE = "keys/public.key";
    private static final String PRIVATE_KEY_FILE = "keys/private.key";

    public RSAUtils() throws Exception {
        if (new File(PUBLIC_KEY_FILE).exists() && new File(PRIVATE_KEY_FILE).exists()) {
            this.keyPair = loadKeyPair();
        } else {
            this.keyPair = generateAndSaveKeyPair();
        }
    }

    private KeyPair generateAndSaveKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        saveKey(PUBLIC_KEY_FILE, pair.getPublic().getEncoded());
        saveKey(PRIVATE_KEY_FILE, pair.getPrivate().getEncoded());

        return pair;
    }

    private void saveKey(String filePath, byte[] keyBytes) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(keyBytes);
        }
    }

    private KeyPair loadKeyPair() throws Exception {
        byte[] publicBytes = readKey(PUBLIC_KEY_FILE);
        byte[] privateBytes = readKey(PRIVATE_KEY_FILE);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pub = kf.generatePublic(new java.security.spec.X509EncodedKeySpec(publicBytes));
        PrivateKey priv = kf.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateBytes));

        return new KeyPair(pub, priv);
    }

    private byte[] readKey(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return fis.readAllBytes();
        }
    }

    public byte[] encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        return cipher.doFinal(message.getBytes());
    }

    public String decrypt(byte[] encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return new String(cipher.doFinal(encryptedMessage));
    }
}

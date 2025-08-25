# 🎧 Audio Steganography using LSB & RSA Encryption

This is a Java-based command-line application that allows you to **hide and retrieve encrypted messages inside WAV audio files** using **Least Significant Bit (LSB) steganography** combined with **RSA encryption** for enhanced security.

---

## 🔐 Features

- 🎵 Encode plaintext messages inside `.wav` audio files.
- 🔒 Encrypt messages with **RSA public-key cryptography**.
- 🕵️ Extract and decrypt hidden messages from stego audio files.
- 📦 CLI-based interface for simplicity.
- 🧠 Built-in error handling for corrupted or short audio files.
- 🧮 Auto-calculates maximum encodable message length per audio file.

---

## 📁 Project Structure

```
com.audio.steganography
├── audio            # WAV file loading and saving
├── core             # LSB encoder/decoder logic
├── crypto           # RSA key generation and encryption/decryption
├── error_handling   # Custom exception classes
├── util             # Utility for capacity calculation
└── StegoCLI.java    # Main CLI interface
```

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 11 or higher

### 🔧 Compile & Run

To compile and run the application:

**Compile:**
```bash
javac -d out src/com/audio/steganography/StegoCLI.java
```

> Make sure all other `.java` files are compiled too, or compile the whole `src` directory if needed:
```bash
javac -d out $(find src -name "*.java")
```

**Run:**
```bash
cd out
java com.audio.steganography.StegoCLI
```

---

## 📥 Usage

### 1. Encoding a Message

- Place a WAV file inside the `/input` folder.
- Run the CLI and select `encode`.
- Enter the input file name, your message, and the output file name.
- The encoded file will be saved to `/output`.

### 2. Decoding a Message

- Select `decode` from the CLI.
- Choose the stego WAV file from the `/output` folder.
- The program will extract and decrypt the hidden message.

---

## 🔐 Encryption

This project uses **RSA-2048** to encrypt and decrypt messages before embedding:

- Keys are saved in the `keys/` directory:
  - `public.key`
  - `private.key`
- If keys do not exist, they are auto-generated on first run.

---

## ⚠️ Error Handling

Custom exceptions for better debugging:

- `StegoException` – Base class
- `CorruptedAudioFileException` – For damaged WAV files
- `ShortAudioFileException` – When audio is too short to hold the message

---

## 📊 Capacity

Capacity is auto-calculated using:
```java
CapacityCalculator.getMaxPlainTextChars(audioBytes);
```

Encoding takes into account:
- WAV header size (44 bytes)
- 32 bits reserved for message length
- RSA encryption output size (in base64)

---

## 📚 Technologies Used

- Java Standard Edition
- Java Sound API (`javax.sound.sampled`)
- RSA Encryption (`javax.crypto`, `java.security`)
- Base64 Encoding
- Command-Line Interface

---

## 🗂️ Sample Directory Structure

```
/input             # Original WAV files
/output            # WAV files with encoded messages
/keys              # Generated RSA key pairs
```

---

## 👨‍💻 Author

**Thilak Adepu**  

---

## 📝 License

This project is open-source and available under the **MIT License**.  
Feel free to use, modify, and distribute it with attribution.
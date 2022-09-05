package com.example.demo.services;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptedText);

}

package com.project.demo.service;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptedText);

}

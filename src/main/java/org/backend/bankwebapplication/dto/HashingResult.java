package org.backend.bankwebapplication.dto;

class HashingResult{
    String hash;
    byte[] salt;

    public HashingResult(String hash, byte[] salt){
        this.hash = hash;
        this.salt = salt;
    }
}

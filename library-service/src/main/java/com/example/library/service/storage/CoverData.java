package com.example.library.service.storage;

public class CoverData {
    private byte[] bytes;
    private String originalFilename;

    public CoverData(byte[] bytes, String originalFilename) {
        this.bytes = bytes;
        this.originalFilename = originalFilename;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }
}

package com.example.library.core.domain;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String account;
    private String userName;
    private String email;
    private String passwordHash;
    private String role;
    private String status;
    private Integer isDeleted;
}

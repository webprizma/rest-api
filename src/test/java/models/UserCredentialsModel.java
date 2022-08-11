package models;

import lombok.Data;

@Data
public class UserCredentialsModel {
    private String email;
    private String password;
}
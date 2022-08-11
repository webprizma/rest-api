package models;

import lombok.Data;

@Data
public class RegistrationAndLoginModel {
    private String token;
    private String error;
}
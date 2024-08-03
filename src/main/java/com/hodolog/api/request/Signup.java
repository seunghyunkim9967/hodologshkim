package com.hodolog.api.request;

import lombok.Data;

@Data
public class Signup {

    private String account;
    private String name;
    private String email;
    private String password;


}

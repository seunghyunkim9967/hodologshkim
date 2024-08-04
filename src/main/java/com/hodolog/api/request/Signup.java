package com.hodolog.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {

    private String account;
    private String name;
    private String email;
    private String password;


}

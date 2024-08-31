package com.keepgreen.pojo.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UserMessageDto {
    private String name;// name
    private String email;// email
    private Date birthDate;// birth_date
    private String token;// token
}

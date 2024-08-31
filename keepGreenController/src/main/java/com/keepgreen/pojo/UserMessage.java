package com.keepgreen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage {
    private int id;//id
    private String name;// name
    private String email;// email
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;// birth_date
    private Timestamp createdAt;//用户信息创建时间
    private Timestamp updatedAt;// 用户信息更新时间；
}

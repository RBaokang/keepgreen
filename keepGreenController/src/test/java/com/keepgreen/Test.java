package com.keepgreen;

import com.keepgreen.utils.Md5Utils;
import com.keepgreen.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Value;

public class Test {
    @Value("spring:mail:username")
    private static final String from = "";

    @org.junit.jupiter.api.Test
    void testMail(){
        System.out.println(from);
    }

    @org.junit.jupiter.api.Test
    void testUUID() throws Exception {

    }


}

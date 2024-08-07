package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.keygen.KeyGenerators;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class AESUtilTest {

    @Test
    void testCryptPass() {

        String salt = KeyGenerators.string().generateKey();
        log.info("salt : {}", salt);
        String input = "sa";
        String decode =  AESUtil.decrypt("89a19138224edada685510969a08dbf2b6846522ff072636cba50e9fc543cbf0");
        Assertions.assertEquals(input, decode);

    }

}

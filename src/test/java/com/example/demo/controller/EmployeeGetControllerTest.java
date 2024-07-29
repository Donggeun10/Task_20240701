package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.enums.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        mockMvc
            .perform(
                post("/api/employee") // url
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("contentType", ContentType.JSON.name())
                    .content("""
                                 [
                                 {"name": "김클로", "email":"clo@clovf.com", "tel":"010-1111-2424", "joined":"2012-01-05"},
                                 {"name": "김클로바", "email":"clova@clovf.com", "tel":"010-1111-2525", "joined":"2022-01-05"},
                                 {"name": "박마블","email":"md@clovf.com", "tel":"010-3535-7979", "joined": "2013-07-01" },
                                 {"name": "홍커넥", "email":"connect@clovf.com", "tel":"010-8531-7942","joined":"2019-12-05"}
                                 ]
                                 """)
            )
            .andDo(print()) // api 수행내역 로그 출력
            .andExpect(status().isCreated()); // response status 201 검증

    }

    @Test
    void testFoundName() throws Exception {

        String name = "김클로";

        mockMvc
            .perform(
                get("/api/employee/"+name) // url
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(name)); // response name 데이터 검증
    }

    @Test
    void testNotFoundName() throws Exception {

        String name = "김클로바";

        mockMvc
            .perform(
                get("/api/employee/"+name) // url
            )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void testFoundTel() throws Exception {

        String tel = "010-1111";

        mockMvc
            .perform(
                get("/api/employee/tel/"+tel) // url
            )
            .andDo(print())
            .andExpect(status().isOk());

    }

    @Test
    void testNotFoundTel() throws Exception {

        String tel = "010-2222";

        mockMvc
            .perform(
                get("/api/employee/tel/"+tel) // url
            )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

}

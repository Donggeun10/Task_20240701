package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.enums.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInsertJsonString() throws Exception {

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
    void testInsertJsonStringFail() throws Exception {

        mockMvc
            .perform(
                post("/api/employee") // url
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("contentType", ContentType.JSON.name())
                    .content("""
                                 [
                                 {"name": "김클로", "email":"clo@clovf.com", "tel":"010-1111-24245", "joined":"2012-01-05"},
                                 {"name": "김클로바", "email":"clova@clovf.com", "tel":"010-1111-2525", "joined":"2022-01-05"},
                                 {"name": "박마블","email":"md@clovf.com", "tel":"010-3535-7979", "joined": "2013-07-01" },
                                 {"name": "홍커넥", "email":"connect@clovf.com", "tel":"010-8531-7942","joined":"2019-12-05"}
                                 ]
                                 """)
            )
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testInsertCsvString() throws Exception {

        mockMvc
            .perform(
                post("/api/employee") // url
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("contentType", ContentType.CSV.name())
                    .content("""
                                 김철수, charles@clovf.com, 010-7531-2468, 2018-03-07
                                 박영희, matilda@clovi.com, 010-8765-4321, 2021-04-28
                                 홍길동, kildong.hong@clovf.com, 010-1234-5678, 2015-08-15
                                 """)
            )
            .andDo(print()) // api 수행내역 로그 출력
            .andExpect(status().isCreated()); // response status 201 검증
    }

}

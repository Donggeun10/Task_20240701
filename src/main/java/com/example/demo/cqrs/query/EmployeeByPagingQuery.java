package com.example.demo.cqrs.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeByPagingQuery {

    private final int page;
    private final int pageSize;

}
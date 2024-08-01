package com.example.demo.cqrs.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeByNameQuery {

    private final String name;

}
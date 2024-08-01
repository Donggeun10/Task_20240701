package com.example.demo.component;

import com.example.demo.entity.Employee;
import com.example.demo.enums.ContentType;
import com.example.demo.exception.BadRequestParseException;
import com.example.demo.exception.NotFoundContentTypeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class DataConverter {

	private final CsvMapper csvMapper;
	private final ObjectMapper objectMapper;

    public DataConverter(CsvMapper csvMapper, ObjectMapper objectMapper) {
        this.csvMapper = csvMapper;
        this.objectMapper = objectMapper;
    }

    public List<Employee> parseRequestBodyToEmployeeList(ContentType contentType, String body) throws BadRequestParseException, NotFoundContentTypeException {

        return switch (contentType) {
            case JSON -> parseJsonToEmployeeList(body);
            case CSV -> parseCsvToEmployeeList(body);
            default -> throw new NotFoundContentTypeException(String.format("지원하지 않는 형식(%s)의 데이터 입니다.", contentType));
        };
	}

	private List<Employee> parseJsonToEmployeeList(String jsonData) throws BadRequestParseException{

		try {

			return objectMapper.readValue(jsonData, new TypeReference<List<Employee>>() {});

		} catch (JsonProcessingException e) {
			throw new BadRequestParseException(e.getMessage());
		} 
		
	}

	private List<Employee> parseCsvToEmployeeList(String csvData) throws BadRequestParseException{

		try {

			MappingIterator<Employee> mappingIterator = csvMapper.readerFor(Employee.class).with(employeeCsvSchema()).readValues(csvData);
			return mappingIterator.readAll();

		}catch (Exception e){
			throw new BadRequestParseException(e.getMessage());
		}

	}

	public List<Employee> parseRequestBodyToEmployeeList(ContentType contentType, MultipartFile file) throws BadRequestParseException, NotFoundContentTypeException {

		return switch (contentType) {
			case JSON -> parseJsonToEmployeeList(file);
			case CSV -> parseCsvToEmployeeList(file);
			default -> throw new NotFoundContentTypeException(String.format("지원하지 않는 형식(%s)의 데이터 입니다.", contentType));
		};

	}

	private List<Employee> parseJsonToEmployeeList(MultipartFile file) throws BadRequestParseException {

		try {

			return objectMapper.readValue(file.getInputStream(), new TypeReference<List<Employee>>() {});

		} catch (Exception e) {
			throw new BadRequestParseException(e.getMessage());
		}
	}

	private List<Employee> parseCsvToEmployeeList(MultipartFile file) throws BadRequestParseException {

		try {

			MappingIterator<Employee> mappingIterator = csvMapper.readerFor(Employee.class).with(employeeCsvSchema()).readValues(file.getInputStream());
			return mappingIterator.readAll();

		} catch (IOException e) {
			throw new BadRequestParseException(e.getMessage());
		}

	}

	private CsvSchema employeeCsvSchema() {
		return CsvSchema.builder()
	            .addColumn("name",CsvSchema.ColumnType.STRING)
	            .addColumn("email",CsvSchema.ColumnType.STRING)
	            .addColumn("tel",CsvSchema.ColumnType.STRING)
	            .addColumn("joined",CsvSchema.ColumnType.STRING)
	            .build().withoutHeader();
	}
}

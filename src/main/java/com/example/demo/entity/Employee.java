package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="TB_EMPLOYEE",
		indexes={
			@Index(name = "idx_employee_n1", columnList = "name")
})
@ToString
public class Employee {

	@JsonProperty
	@Column(name = "name", length = 20)
	private String name;
	
	@Id
	@JsonProperty
	@Column(name = "tel", length = 13)
	@Getter
	private String tel;
	
	@JsonProperty
	@Column(name = "email", length = 100)
	@Getter
	private String email;
	
	@JsonProperty
	@Column(name = "joined")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Schema(type = "string", pattern = "yyyy-MM-dd")
	private Date joined;

}
	


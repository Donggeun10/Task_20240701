package com.example.demo.advice;



import com.example.demo.domain.ErrorResponse;
import com.example.demo.exception.BadRequestNoContentPageException;
import com.example.demo.exception.BadRequestParseException;
import com.example.demo.exception.DataSaveException;
import com.example.demo.exception.NotFoundContentTypeException;
import com.example.demo.exception.NotFoundEmployeeException;
import com.example.demo.util.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class EmployeeControllerAdvice {
	
	@ExceptionHandler(NotFoundEmployeeException.class)
	public ResponseEntity<String> notFoundEmployee(NotFoundEmployeeException e) {
		
		ErrorResponse errorRes = new ErrorResponse(e.getMessage());
		
		log.error(DataUtil.makeErrorLogMessage(e));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DataUtil.objectToString(errorRes));	
		
	}
	
	@ExceptionHandler(BadRequestNoContentPageException.class)
	public ResponseEntity<String> serverException(BadRequestNoContentPageException badRequestNoContentPageException) {
		
		ErrorResponse errorRes = new ErrorResponse(badRequestNoContentPageException.getMessage());
		
		log.error(DataUtil.makeErrorLogMessage(badRequestNoContentPageException));	
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DataUtil.objectToString(errorRes));	
		
	}

	@ExceptionHandler(NotFoundContentTypeException.class)
	public ResponseEntity<String> serverException(NotFoundContentTypeException notFoundContentTypeException) {

		ErrorResponse errorRes = new ErrorResponse(notFoundContentTypeException.getMessage());

		log.error(DataUtil.makeErrorLogMessage(notFoundContentTypeException));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DataUtil.objectToString(errorRes));

	}
	
	@ExceptionHandler(BadRequestParseException.class)
	public ResponseEntity<String> serverException(BadRequestParseException badRequestParseException) {
		
		ErrorResponse errorRes = new ErrorResponse("ContentType과 일치하지 않은 우형의 데이터가 전송되었습니다. ");
		
		log.error(DataUtil.makeErrorLogMessage(badRequestParseException));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DataUtil.objectToString(errorRes));	
		
	}

	@ExceptionHandler(DataSaveException.class)
	public ResponseEntity<String> serverException(DataSaveException dataSaveException) {

		ErrorResponse errorRes = new ErrorResponse("데이터 저장 중 오류가 발생했습니다.");

		log.error(DataUtil.makeErrorLogMessage(dataSaveException));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DataUtil.objectToString(errorRes));

	}

}

package server.meeting.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionResponse> handleApiException(ApiException e) {
        ErrorType errorType = e.getErrorType();
        ApiExceptionResponse response = new ApiExceptionResponse(
                errorType.getStatus().value(),
                errorType.getErrorCode(),
                errorType.getDescription()
        );
        return ResponseEntity.status(errorType.getStatus()).body(response);
    }

    // Dto Valid Exception
    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleDtoNotValid(MethodArgumentNotValidException ex) {

        // MethodArgumentNotValidException에서 FieldError들을 가져와서 메시지를 추출하여 리스트에 추가
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream( )
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorType errorType = ErrorType._BAD_REQUEST;
        ApiExceptionResponse response = new ApiExceptionResponse(
                errorType.getStatus().value(),
                errorType.getErrorCode(),
                String.join(", ", errors)
        );
        return ResponseEntity.status(errorType.getStatus()).body(response);
    }
}

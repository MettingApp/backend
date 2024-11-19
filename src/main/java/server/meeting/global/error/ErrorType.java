package server.meeting.global.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ErrorType implements ErrorCodeInterface {

    _OK(OK, "200", "성공");

    private final HttpStatus status;
    private final String errorCode;
    private final String description;


}

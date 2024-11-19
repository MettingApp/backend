package server.meeting.global.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {

    HttpStatus getStatus();
    String getErrorCode();
    String getDescription();
}

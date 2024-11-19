package server.meeting.global.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {

    Integer getStatus();
    String getErrorCode();
    String getDescription();
}

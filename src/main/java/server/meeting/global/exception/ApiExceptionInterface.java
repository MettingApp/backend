package server.meeting.global.exception;

import server.meeting.global.error.ErrorCodeInterface;

public interface ApiExceptionInterface {

    ErrorCodeInterface getErrorCodeInterface();
    String getErrorDescription();
}

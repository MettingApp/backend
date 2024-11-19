package server.meeting.global.exception;

import lombok.Getter;
import server.meeting.global.error.ErrorCodeInterface;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionInterface{

    private final ErrorCodeInterface errorCodeInterface;
    private final String errorDescription;

    public ApiException(ErrorCodeInterface errorCodeInterface) {
        super(errorCodeInterface.getDescription());
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, String errorDescription) {
        super(errorDescription);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, Throwable tx) {
        super(tx);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();
    }

    public ApiException(ErrorCodeInterface errorCodeInterface, String errorDescription, Throwable tx) {
        super(tx);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;
    }
}

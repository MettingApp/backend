package server.meeting.global.exception;

public class ApiException extends RuntimeException {

    private final ErrorType errorType;

    public ApiException(ErrorType errorType) {
        super(errorType.getDescription());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}

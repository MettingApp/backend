package server.meeting.global.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ErrorType implements ErrorCodeInterface {

    _OK(OK, "200", "성공"),

    // --------------------------------------- S3 -----------------------------------
    _S3_UPLOAD_FAIL(BAD_REQUEST, "S4001", "업로드에 실패하였습니다."),
    _S3_NOT_EXISTS_KEY(BAD_REQUEST, "S4002", "존재하지 않는 파일 명입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String description;


}

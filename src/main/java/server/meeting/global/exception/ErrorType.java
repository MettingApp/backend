package server.meeting.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

    _OK(OK, "200", "성공"),
    _BAD_REQUEST(BAD_REQUEST, "400", "잘못된 입력값 형식 입니다."),
    _SERVER_ERROR(INTERNAL_SERVER_ERROR, "500", "internal server error"),
    // --------------------------------------- S3 -----------------------------------
    _S3_UPLOAD_FAIL(BAD_REQUEST, "S4001", "업로드에 실패하였습니다."),
    _S3_NOT_EXISTS_KEY(BAD_REQUEST, "S4002", "존재하지 않는 파일 명입니다."),
    // --------------------------------------- Member -----------------------------------
    _UNAUTHORIZED(UNAUTHORIZED, "401", "사용자 인증에 실패했습니다."),
    _BAD_REQUEST_PASSWORD(BAD_REQUEST, "400", "비밀번호와 재확인 비밀번호가 일치하지 않습니다."),
    _CONFLICT_USERNAME(CONFLICT, "409", "이미 존재하는 아이디 입니다."),
    _CONFLICT_NICKNAME(CONFLICT, "409", "이미 존재하는 닉네임 입니다."),
    _NOT_FOUND_MEMBER(NOT_FOUND, "404", "해당하는 사용자를 찾지 못했습니다."),
    // --------------------------------------- Team -----------------------------------
    _NOT_FOUND_TEAM(NOT_FOUND, "404", "해당하는 팀을 찾지 못했습니다."),
    _BAD_REQUEST_INVITE_CODE(BAD_REQUEST, "400", "인가코드가 일치하지 않습니다."),
    _ALREADY_JOINED_TEAM(CONFLICT, "409", "이미 팀에 가입했습니다."),
    // -------------------------------------- Meeting ------------------------------------
    _NOT_FOUND_MEETING(BAD_REQUEST, "M4001", "해당되는 회의를 찾지 못했습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String description;


}

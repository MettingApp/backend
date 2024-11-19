package server.meeting.global.api;

import lombok.*;
import org.springframework.http.HttpStatus;
import server.meeting.global.error.ErrorCodeInterface;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK() {
        return Result.builder()
                .resultCode(200)
                .resultMessage("OK")
                .resultDescription("성공하였습니다.")
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface){
        return Result.builder()
                .resultCode(errorCodeInterface.getStatus().value())
                .resultMessage(errorCodeInterface.getErrorCode())
                .resultDescription("오류가 발생하였습니다.")
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface, Throwable tx){
        return Result.builder()
                .resultCode(errorCodeInterface.getStatus().value())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface, String description){
        return Result.builder()
                .resultCode(errorCodeInterface.getStatus().value())
                .resultMessage(errorCodeInterface.getErrorCode())
                .resultDescription(errorCodeInterface.getDescription())
                .build();
    }
}

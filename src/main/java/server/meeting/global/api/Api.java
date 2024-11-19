package server.meeting.global.api;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.meeting.global.error.ErrorCodeInterface;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    public static <T> Api<T> success(T data) {
        Api<T> api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    public static <T> Api<T> ERROR(Result result) {
        Api<T> api = new Api<T>();
        api.result = result;
        return api;
    }

    public static <T> Api<T> ERROR(Result result, ErrorCodeInterface errorCode) {
        Api<T> api = new Api<T>();
        api.result = Result.ERROR(errorCode);
        return api;
    }

    public static <T> Api<T> ERROR(Result result, ErrorCodeInterface errorCode, Throwable tx) {
        Api<T> api = new Api<T>();
        api.result = Result.ERROR(errorCode, tx);
        return api;
    }

    public static <T> Api<T> ERROR(Result result, ErrorCodeInterface errorCode, String description) {
        Api<T> api = new Api<T>();
        api.result = Result.ERROR(errorCode, description);
        return api;
    }
}

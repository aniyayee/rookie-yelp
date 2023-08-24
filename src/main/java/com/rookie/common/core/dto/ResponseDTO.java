package com.rookie.common.core.dto;

import com.rookie.common.constants.ErrorCode;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * @author yayee
 */
@Data
@Builder
public class ResponseDTO<T> {

    /**
     * response timestamp.
     */
    private long timestamp;

    /**
     * response code, 200 -> OK.
     */
    private String status;

    /**
     * response message.
     */
    private String message;

    /**
     * response data.
     */
    private T data;

    /**
     * response success result wrapper.
     *
     * @param <T> type of data class
     * @return response result
     */
    public static <T> ResponseDTO<T> success() {
        return success(null);
    }

    /**
     * response success result wrapper.
     *
     * @param data response data
     * @param <T> type of data class
     * @return response result
     */
    public static <T> ResponseDTO<T> success(T data) {
        return ResponseDTO.<T>builder().data(data).message(ErrorCode.SUCCESS.getMsg())
            .status(ErrorCode.SUCCESS.getCode()).timestamp(System.currentTimeMillis()).build();
    }

    /**
     * response error result wrapper.
     *
     * @param message error message
     * @param <T> type of data class
     * @return response result
     */
    public static <T extends Serializable> ResponseDTO<T> fail(String message) {
        return fail(null, message);
    }

    /**
     * response error result wrapper.
     *
     * @param data response data
     * @param message error message
     * @param <T> type of data class
     * @return response result
     */
    public static <T> ResponseDTO<T> fail(T data, String message) {
        return ResponseDTO.<T>builder().data(data).message(message).status(ErrorCode.FAIL.getCode())
            .timestamp(System.currentTimeMillis()).build();
    }
}

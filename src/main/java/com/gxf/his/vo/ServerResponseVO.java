package com.gxf.his.vo;

import com.gxf.his.enmu.ServerResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Data
@NoArgsConstructor
public class ServerResponseVO<T> implements Serializable {
    private static final long serialVersionUID = -1005863670740860901L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 响应内容
     */
    private T data;

    private ServerResponseVO(ServerResponseEnum responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    private ServerResponseVO(ServerResponseEnum responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    private ServerResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回成功信息
     * @param data      信息内容
     * @param <T> 要返回的数据
     * @return 统一的响应返回数据
     */
    public static<T> ServerResponseVO success(T data) {
        return new ServerResponseVO<>(ServerResponseEnum.SUCCESS, data);
    }

    /**
     * 返回成功信息
     * @return 统一的响应返回数据
     */
    public static ServerResponseVO success() {
        return new ServerResponseVO(ServerResponseEnum.SUCCESS);
    }

    /**
     * 返回错误信息
     * @param responseCode      响应码
     * @return 统一的响应返回数据
     */
    public static ServerResponseVO error(ServerResponseEnum responseCode) {
        return new ServerResponseVO(responseCode);
    }
}

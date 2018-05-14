package com.sample.feignexample.base;

/**
 * Created by issac.hu on 2018/3/28.
 */
public class ApiResponse {

    private int code;
    private String message;
    private Object data;

    public ApiResponse(){

    }
    public ApiResponse(Object data){
      this.data=data;
      this.message="success";
    }

    public static ApiResponse success(Object data){
     return new ApiResponse(data);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

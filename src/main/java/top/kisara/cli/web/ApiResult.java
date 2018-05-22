package top.kisara.cli.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApiResult {
    private int code = 0;
    private Object data;
    private boolean success = false;
    private String info = "default info";

    public ApiResult success() {
        this.code = 1;
        this.info = "default success info";
        this.success = true;
        return this;
    }

    public ApiResult success(Object data) {
        this.data = data;
        this.success();
        return this;
    }

    public ApiResult success(Object data, String info) {
        this.data = data;
        this.success();
        this.info = info;
        return this;
    }

    public ApiResult failed() {
        this.code = 0;
        this.info = "default failed info";
        this.success = false;
        return this;
    }

    public ApiResult failed(String info) {
        this.failed();
        this.info = info;
        return this;
    }
}

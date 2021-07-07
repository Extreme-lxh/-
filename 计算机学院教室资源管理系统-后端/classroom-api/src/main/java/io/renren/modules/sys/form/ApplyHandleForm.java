package io.renren.modules.sys.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Auther zxl
 * @Date 2020/3/28 21:17
 * @Description
 **/
@Data
public class ApplyHandleForm {


    @NotNull
    private Integer useId;

    @ApiModelProperty("1：成功；2：失败")
    private Integer state;
    @NotEmpty
    private String handleMsg;

}

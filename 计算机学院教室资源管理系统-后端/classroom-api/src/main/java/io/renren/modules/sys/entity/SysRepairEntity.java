package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/30 11:34
 * @Description 维修西悉尼
 **/
@Data
@TableName("sys_repair")
public class SysRepairEntity {

    @TableId(type = IdType.AUTO)
    private Integer repairId;
    @NotNull(message = "设备id不能为空")
    private Integer materielId;

    @NotBlank(message = "维修内容不能为空")
    @ApiModelProperty("维修内容")
    private String repairContent;
    @ApiModelProperty("维修照片")
    private String repairImgs;

    private Long repairUserId;

    @ApiModelProperty("维修进度反馈(分号分隔)")
    private String feedProgress;
    @ApiModelProperty("0：待处理；1：处理完成；2：处理中")
    private Integer repairState;

    private Date createTime;

    private Date completeTime;

    @TableField(exist = false)
    private String repaireUserName;

    @TableField(exist = false)
    private String teachingBuildingName;
    @TableField(exist = false)
    private String roomName;
    @TableField(exist = false)
    private Integer materielType;

}

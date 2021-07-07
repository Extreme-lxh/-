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
 * @Date 2020/3/30 11:31
 * @Description 举报信息
 **/
@Data
@TableName("sys_report")
public class SysReportEntity {

    @TableId(type = IdType.AUTO)
    private Integer reportId;

    @NotBlank
    @ApiModelProperty("举报内容")
    private String reportContent;

    @ApiModelProperty("举报照片")
    private String reportImgs;

    @NotNull
    private Integer reportRoomId;
    private Long reportUserId;
    @ApiModelProperty("举报时间")
    private Date createTime;

    @TableField(exist = false)
    private String reportUsername;
    @TableField(exist = false)
    private String teachingBuildingName;
    @TableField(exist = false)
    private String roomName;



}

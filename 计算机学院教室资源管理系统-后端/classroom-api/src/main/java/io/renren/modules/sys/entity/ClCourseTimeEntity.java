package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/27 15:54
 * @Description 课表时间信息
 **/
@Data
@TableName("cl_course_time")
public class ClCourseTimeEntity {

    @TableId(type = IdType.AUTO)
    private Integer courseTimeId;

    @NotNull
    private Integer roomId;
    @NotNull
    private Integer courseId;
    @NotNull
    @ApiModelProperty("周几")
    private Integer week;

    @NotNull
    @ApiModelProperty("上课时间")
    private Integer beginTime;
    @NotNull
    @ApiModelProperty("下课时间")
    private Integer finishTime;

    private Date createTime;

    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String courseClass;

    @TableField(exist = false)
    private String teachingBuildingName;
    @TableField(exist = false)
    private String roomName;

    @TableField(exist = false)
//    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date courseStartDate;
    @TableField(exist = false)
//    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date courseFinishDate;

    public ClCourseTimeEntity() {
    }

    public ClCourseTimeEntity(@NotNull Integer roomId, @NotNull Integer week,
                              @NotNull Integer beginTime, @NotNull Integer finishTime) {
        this.roomId = roomId;
        this.week = week;
        this.beginTime = beginTime;
        this.finishTime = finishTime;
    }
}

package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/27 15:31
 * @Description 课程
 **/
@Data
@TableName("cl_course")
public class ClCourseEntity {

    @TableId(type = IdType.AUTO)
    private Integer courseId;
    @ApiModelProperty("课程名")
    private String courseName;
    @ApiModelProperty("班级名")
    private String courseClass;

//    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;
//    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date finishDate;

    private Date createTime;

}

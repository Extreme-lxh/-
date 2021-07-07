package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/27 10:24
 * @Description 教学楼
 **/
@Data
@TableName("cl_teaching_building")
public class ClTeachingBuildingEntity {

    @TableId(type = IdType.AUTO)
    private Integer teachingBuildingId;

    private String name;
    @ApiModelProperty("教室数量")
    private Integer roomCount;

    private Date createTime;

}

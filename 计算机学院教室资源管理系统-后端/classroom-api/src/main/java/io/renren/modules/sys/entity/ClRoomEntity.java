package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther zxl
 * @Date 2020/3/27 10:26
 * @Description 教室
 **/
@Data
@TableName("cl_room")
public class ClRoomEntity {

    @TableId(type = IdType.AUTO)
    private Integer roomId;

    private String roomName;

    private String teachingBuildingName;

    private Integer capacity;

    private Date createTime;


    @TableField(exist = false)
    List<ClCourseTimeEntity> courses;
    @TableField(exist = false)
    List<ClRoomUseEntity> uses;





}

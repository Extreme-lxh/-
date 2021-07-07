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
 * @Date 2020/3/28 17:02
 * @Description 预约使用信息
 **/
@Data
@TableName("cl_room_use")
public class ClRoomUseEntity {

    @TableId(type = IdType.AUTO)
    private Integer useId;

    @NotBlank(message = "用途不能为空")
    @ApiModelProperty("用途")
    private String useDo;
    @NotNull(message = "时间时间不能为空")
    @ApiModelProperty("哪一天使用")
    private Date useDate;
    @NotNull(message = "时间时间不能为空")
    @ApiModelProperty("开始使用时间")
    private Integer useStartTime;
    @NotNull(message = "时间时间不能为空")
    @ApiModelProperty("结束 使用时间")
    private Integer useEndTime;
    @NotNull(message = "人数不能为空")
    @ApiModelProperty("人数")
    private Integer peopleNum;

    private Long applyUserId;
    @NotBlank(message = "申请人职务不能为空")
    @ApiModelProperty("申请人职务")
    private String applyUserPost;
    @NotBlank(message = "申请人姓名不能为空")
    @ApiModelProperty("申请人姓名")
    private String applyUserName;
    @NotBlank(message = "申请人联系方式不能为空")
    @ApiModelProperty("申请人联系方式")
    private String applyUserMobile;

    @NotNull(message = "请选择申请的教室")
    @ApiModelProperty("申请的教室")
    private Integer roomId;
    @ApiModelProperty("0：待处理；1：成功；2：失败；3：退回")
    private Integer applyState;
    @ApiModelProperty("处理留言")
    private String handleMsg;
    @ApiModelProperty("处理时间")
    private Date handleTime;
    @ApiModelProperty("申请时间")
    private Date createTime;

    @TableField(exist = false)
    private String teachingBuildingName;
    @TableField(exist = false)
    private String roomName;

}

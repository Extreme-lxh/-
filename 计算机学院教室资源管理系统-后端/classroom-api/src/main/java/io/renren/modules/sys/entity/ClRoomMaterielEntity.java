package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/27 11:50
 * @Description 教室内设施
 **/
@Data
@TableName("cl_room_materiel")
public class ClRoomMaterielEntity {

    @TableId(type = IdType.AUTO)
    private Integer materielId;

    @ApiModelProperty("1：板凳；2：桌子；3：多媒体设备；4：风扇；5：空调")
    private Integer type;
    @ApiModelProperty("数量")
    private Integer num;

    private Integer roomId;

    private Date createTime;

}

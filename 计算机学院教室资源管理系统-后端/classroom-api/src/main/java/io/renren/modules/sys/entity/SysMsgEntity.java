package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/26 13:51
 * @Description
 **/
@Data
@TableName("sys_msg")
public class SysMsgEntity {

    @TableId(type = IdType.AUTO)
    private Integer msgId;

    private Long userId;

    private String userName;

    private String content;

    private Date createTime;

}

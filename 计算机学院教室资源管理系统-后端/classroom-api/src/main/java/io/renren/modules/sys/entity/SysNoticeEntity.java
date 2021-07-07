package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/26 13:46
 * @Description
 **/
@Data
@TableName("sys_notice")
public class SysNoticeEntity {


    @TableId(type = IdType.AUTO)
    private Integer noticeId;

    private String title;

    private String content;

    @ApiModelProperty("1：系统公告；2：安全检查公告")
    private Integer type;

    private Date createTime;

}

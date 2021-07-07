package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysMsgEntity;
import io.renren.modules.sys.entity.SysNoticeEntity;
import io.renren.modules.sys.service.SysMsgService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/26 14:04
 * @Description
 **/
@Api(value = "在线留言",tags = "在线留言")
@RestController
@RequestMapping("/sys/msg")
public class SysMsgController extends AbstractController {

    @Autowired
    SysMsgService msgService;

    @ApiOperation(value = "添加留言",notes = "添加留言")
    @PostMapping("/save")
    public R save(@RequestBody SysMsgEntity entity){

        entity.setCreateTime(new Date());
        entity.setUserId(getUserId());
        entity.setUserName(getUser().getUsername());
        msgService.save(entity);
        return R.ok();
    }

    @ApiOperation(value = "留言列表",notes = "留言列表",response = SysMsgEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName",value = "留言用户",required = false,dataType = "string",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){

        PageUtils page = msgService.queryPage(params);
        return R.ok().put("page", page);

    }
    /**
     * 删除
     */
    @ApiOperation(value = "删除留言",notes = "删除留言")
    @PostMapping("/delete")
    public R delete(@RequestBody @ApiParam  Integer[] noticeIds){

        msgService.removeByIds(Arrays.asList(noticeIds));

        return R.ok();
    }


}

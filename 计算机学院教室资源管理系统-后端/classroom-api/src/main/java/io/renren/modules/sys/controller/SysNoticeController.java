package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysNoticeEntity;
import io.renren.modules.sys.service.SysNoticeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/26 13:58
 * @Description
 **/
@Api(value = "通知公告",tags = "通知公告")
@RestController
@RequestMapping("/sys/notice")
public class SysNoticeController {

    @Autowired
    SysNoticeService noticeService;

    @ApiOperation(value = "添加通知",notes = "通知通知")
    @PostMapping("/save")
    public R save(@RequestBody SysNoticeEntity entity){

        entity.setCreateTime(new Date());
        noticeService.save(entity);
        return R.ok();
    }

    @ApiOperation(value = "通知列表列表",notes = "通知列表列表",response = SysNoticeEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "title",value = "标题",required = false,dataType = "string",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){

        PageUtils page = noticeService.queryPage(params);
        return R.ok().put("page", page);

    }
    /**
     * 删除
     */
    @ApiOperation(value = "删除通知公告",notes = "删除通知公告")
    @PostMapping("/delete")
    public R delete(@RequestBody @ApiParam  Integer[] noticeIds){

        noticeService.removeByIds(Arrays.asList(noticeIds));

        return R.ok();
    }

}

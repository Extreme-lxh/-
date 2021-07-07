package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysMsgEntity;
import io.renren.modules.sys.entity.SysReportEntity;
import io.renren.modules.sys.service.SysReportService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/30 11:45
 * @Description
 **/
@Api(value = "举报管理",tags = "举报管理")
@RestController
@RequestMapping("/sys/report")
public class SysReportController extends AbstractController{
    @Autowired
    SysReportService reportService;

    @ApiOperation(value = "添加举报",notes = "添加举报")
    @PostMapping("/save")
    public R save(@RequestBody SysReportEntity entity){

        ValidatorUtils.validateEntity(entity);
        entity.setCreateTime(new Date());
        entity.setReportUserId(getUserId());
        reportService.save(entity);
        return R.ok();
    }
    @ApiOperation(value = "我的举报记录",notes = "我的举报记录",response = SysReportEntity.class)
    @GetMapping("/log")
    public R log(){
        return R.ok().put("list",reportService.myLog(getUserId()));
    }

    @ApiOperation(value = "举报列表",notes = "举报列表",response = SysReportEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName",value = "举报用户",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "roomId",value = "教室id",required = false,dataType = "int",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){

        PageUtils page = reportService.queryPage(params);
        return R.ok().put("page", page);

    }

}

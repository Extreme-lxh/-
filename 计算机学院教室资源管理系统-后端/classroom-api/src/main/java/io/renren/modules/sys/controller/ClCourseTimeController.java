package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.ClCourseEntity;
import io.renren.modules.sys.entity.ClCourseTimeEntity;
import io.renren.modules.sys.service.ClCourseTimeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/27 17:04
 * @Description
 **/
@Api(value = "课程时间管理",tags = "课程时间管理")
@RestController
@RequestMapping("/sys/course/time")
public class ClCourseTimeController {

    @Autowired
    ClCourseTimeService timeService;

    @ApiOperation(value = "添加课程时间",notes = "添加课程时间，时间计算：时*60*60+分*60")
    @PostMapping("/save")
    public R save(@RequestBody List<ClCourseTimeEntity> list){

        timeService.saveList(list);
        return R.ok();
    }

    @ApiOperation(value = "课程时间列表",notes = "课程时间列表",response = ClCourseTimeEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "courseName",value = "课程",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "className",value = "上课班级",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "roomId",value = "教室",required = false,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "week",value = "周几",required = false,dataType = "int",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){

        PageUtils page = timeService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "删除课程时间",notes = "删除课程时间")
    @PostMapping("/delete")
    public R delete(@RequestBody @ApiParam  Integer[] ids){

        timeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }



}

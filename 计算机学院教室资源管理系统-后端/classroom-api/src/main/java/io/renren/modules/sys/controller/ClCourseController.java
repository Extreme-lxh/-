package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.ClCourseEntity;
import io.renren.modules.sys.entity.ClRoomEntity;
import io.renren.modules.sys.service.ClCourseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/27 16:22
 * @Description
 **/
@Api(value = "课程信息管理",tags = "课程信息管理")
@RestController
@RequestMapping("/sys/course")
public class ClCourseController extends AbstractController {

    @Autowired
    ClCourseService courseService;

    @ApiOperation(value = "添加课程",notes = "添加课程")
    @PostMapping("/save")
    public R save(@RequestBody List<ClCourseEntity> list){
        for (ClCourseEntity courseEntity: list) {
            if (courseService.count(new QueryWrapper<ClCourseEntity>()
                    .eq("course_name",courseEntity.getCourseName())
                    .eq("course_class",courseEntity.getCourseClass())) > 0){
                return R.error(courseEntity.getCourseClass()+" 已有 "+courseEntity.getCourseName());
            }
            courseEntity.setCreateTime(new Date());
        }
        courseService.saveBatch(list);
        return R.ok();
    }

    @ApiOperation(value = "课程列表",notes = "课程列表",response = ClCourseEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "courseName",value = "课程",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "className",value = "上课班级",required = false,dataType = "string",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){

        PageUtils page = courseService.queryPage(params);
        return R.ok().put("page", page);
    }
    @ApiOperation(value = "所有课程",notes = "查询所有课程的course_id、course_name",response = ClCourseEntity.class)
    @GetMapping("/all")
    public R all(@RequestParam(required = false) @ApiParam(value = "课程是否已经结束") Boolean isFinish){

        return R.ok().put("list",courseService.all(isFinish));

    }

    @ApiOperation(value = "删除课程",notes = "删除课程,课程时间也将删除")
    @PostMapping("/delete")
    public R delete(@RequestBody @ApiParam  Integer[] ids){

        courseService.delBatch(ids);
        return R.ok();
    }
}

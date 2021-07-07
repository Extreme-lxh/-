package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.ClTeachingBuildingEntity;
import io.renren.modules.sys.service.ClTeachingBuildingService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @Auther zxl
 * @Date 2020/3/27 10:37
 * @Description
 **/
@Api(value = "教学楼信息管理",tags = "教学楼信息管理")
@RestController
@RequestMapping("/sys/teachingBuilding")
public class ClTeachingBuildingController extends AbstractController {

    @Autowired
    ClTeachingBuildingService teachingBuildingService;

    @ApiOperation(value = "添加教学楼",notes = "添加教学楼，只传name")
    @PostMapping("/save")
    public R save(@RequestBody ClTeachingBuildingEntity entity){
        if (teachingBuildingService.count(new QueryWrapper<ClTeachingBuildingEntity>()
                .eq("name",entity.getName()))>0){
            return R.error("该教学楼已存在");
        }
        entity.setCreateTime(new Date());
        teachingBuildingService.save(entity);
        return R.ok();
    }
    @ApiOperation(value = "教学楼列表",notes = "教学楼列表",response = ClTeachingBuildingEntity.class)
    @GetMapping("/all")
    public R all(@RequestParam(required = false) String name){
        return R.ok().put("list",teachingBuildingService.
                list(new QueryWrapper<ClTeachingBuildingEntity>()
                        .like(StringUtils.isNotBlank(name),"name",name)
                        .orderByDesc("create_time")));
    }

    @ApiOperation(value = "删除教学楼",notes = "删除教学楼")
    @PostMapping("/delete")
    public R delete(@RequestBody @ApiParam Integer[] ids){

        teachingBuildingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }




}

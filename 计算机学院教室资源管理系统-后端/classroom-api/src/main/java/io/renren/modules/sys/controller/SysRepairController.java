package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysRepairEntity;
import io.renren.modules.sys.entity.SysReportEntity;
import io.renren.modules.sys.form.RepairFeedForm;
import io.renren.modules.sys.service.SysRepairService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/30 14:08
 * @Description
 **/
@Slf4j
@Api(value = "维修管理",tags = "维修管理")
@RestController
@RequestMapping("/sys/repair")
public class SysRepairController extends AbstractController {

    @Autowired
    SysRepairService repairService;
    @Autowired
    SysUserRoleService roleService;

    @ApiOperation(value = "报修",notes = "报修")
    @PostMapping("/save")
    public R save(@RequestBody SysRepairEntity repairEntity){

        ValidatorUtils.validateEntity(repairEntity);

        repairEntity.setCreateTime(new Date());
        repairEntity.setRepairUserId(getUserId());
        repairService.save(repairEntity);
        return R.ok();
    }

    @ApiOperation(value = "开始处理/完成报修",notes = "开始处理/完成报修，0=》2/2=》1")
    @GetMapping("/handle")
    public R handle(@RequestParam Integer repairId){
        SysRepairEntity repairEntity = repairService.getById(repairId);
        if (repairEntity == null || repairEntity.getRepairState() == 1){
            return R.error("无法操作");
        }else {
            if(repairEntity.getRepairState() == 0){
                repairEntity.setRepairState(2);
            }else if(repairEntity.getRepairState() == 2){
                repairEntity.setRepairState(1);
                repairEntity.setCompleteTime(new Date());
            }
            repairService.updateById(repairEntity);
            return R.ok();
        }
    }
    @ApiOperation(value = "更新报修进度反馈",notes = "更新报修进度反馈")
    @PostMapping("/feed")
    public R feed(@RequestBody RepairFeedForm feedForm){
        SysRepairEntity repairEntity = repairService.getById(feedForm.getRepairId());
        if (repairEntity == null || repairEntity.getRepairState() != 2){
            return R.error("无法操作");
        }else {
            repairEntity.setFeedProgress(repairEntity.getFeedProgress()+feedForm.getFeedContent()+";");
            repairService.updateById(repairEntity);
            return R.ok();
        }
    }

    @ApiOperation(value = "报修记录列表",notes = "报修记录列表",response = SysRepairEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roomId",value = "教室id",required = false,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "state",value = "0：待处理；1：处理完成；2：处理中",required = false,dataType = "int",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){


        List<Long> roles = roleService.queryRoleIdList(getUserId());

        if (getUserId() !=1 && !roles.contains(4L)){
            params.put("userId",getUserId());
        }
        PageUtils page = repairService.queryPage(params);
        return R.ok().put("page", page);

    }

}

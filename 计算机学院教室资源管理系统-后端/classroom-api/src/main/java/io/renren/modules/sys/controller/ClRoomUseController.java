package io.renren.modules.sys.controller;

import cn.hutool.core.date.DateUtil;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.ClRoomUseEntity;
import io.renren.modules.sys.form.ApplyHandleForm;
import io.renren.modules.sys.form.UserInfoSelectForm;
import io.renren.modules.sys.service.ClCourseTimeService;
import io.renren.modules.sys.service.ClRoomService;
import io.renren.modules.sys.service.ClRoomUseService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/28 17:13
 * @Description
 **/
@Slf4j
@Api(value = "教室申请使用管理",tags = "教室申请使用管理")
@RestController
@RequestMapping("/sys/room/use")
public class ClRoomUseController extends AbstractController{

    @Autowired
    ClRoomUseService useService;
    @Autowired
    ClCourseTimeService courseTimeService;
    @Autowired
    ClRoomService roomService;

    @ApiOperation(value = "申请教室",notes = "申请教室")
    @PostMapping("/save")
    public R apply(@RequestBody ClRoomUseEntity entity){
        ValidatorUtils.validateEntity(entity);
        entity.setApplyUserId(getUserId());
        useService.apply(entity);
        return R.ok();
    }
    @ApiOperation(value = "我的申请使用记录",notes = "我的教室申请使用记录",response = ClRoomUseEntity.class)
    @GetMapping("/list/my")
    public R all(){

        return R.ok().put("list",useService.myList(getUserId()));
    }
    @ApiOperation(value = "退回教室",notes = "退回教室")
    @GetMapping("/back")
    public R back(@RequestParam @ApiParam(required = true,value = "使用记录id") Integer useId){

        ClRoomUseEntity useEntity = useService.getById(useId);
        if (useEntity == null || !useEntity.getApplyUserId().equals(getUserId())
                || useEntity.getApplyState()!=1){
            return R.error("操作失败");
        }
        long endTime = useEntity.getUseDate().getTime() + useEntity.getUseEndTime() * 1000;
        if (endTime < System.currentTimeMillis()){
            // 已结束
            return R.error("使用已到期，无法操作");
        }
        useEntity.setApplyState(3);
        useService.updateById(useEntity);
        return R.ok();
    }

    @ApiOperation(value = "申请使用记录",notes = "教室申请使用记录列表",response = ClRoomUseEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "applyUserName",value = "申请者姓名",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "roomId",value = "教室",required = false,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "useDate",value = "使用时间",required = false,dataType = "date",paramType = "query"),
            @ApiImplicitParam(name = "applyState",value = "状态",required = false,dataType = "int",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){

        PageUtils page = useService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "处理申请",notes = "处理申请")
    @PostMapping("/handle")
    public R handle(@RequestBody ApplyHandleForm form){
        ValidatorUtils.validateEntity(form);

        ClRoomUseEntity useEntity = useService.getById(form.getUseId());
        if (useEntity == null || useEntity.getApplyState() != 0){
            return R.error("无法操作");
        }
        useEntity.setApplyState(form.getState());
        useEntity.setHandleMsg(form.getHandleMsg());
        useEntity.setHandleTime(new Date());
        useService.updateById(useEntity);
        return R.ok();
    }
    @ApiOperation(value = "教室使用信息",notes = "教室使用信息")
    @PostMapping("/info")
    public R freeRoom(@RequestBody UserInfoSelectForm form){

        if (form.getDate() == null){
            form.setDate(DateUtil.parseDate(DateUtil.today()));
        }
        log.info("form: {}",form);
        return R.ok().put("list",roomService.infoByDate(form.getDate(), form.getTeachingBuildingName(), form.getRoomName()));

    }



}

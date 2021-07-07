package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.ClRoomEntity;
import io.renren.modules.sys.service.ClRoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/27 11:01
 * @Description
 **/
@Api(value = "教室信息管理",tags = "教室信息管理")
@RestController
@RequestMapping("/sys/room")
public class ClRoomController extends AbstractController {

    @Autowired
    ClRoomService roomService;

    @ApiOperation(value = "添加教室",notes = "添加教室，只传roomName、teachingBuildingName")
    @PostMapping("/save")
    public R save(@RequestBody List<ClRoomEntity> list){

        for (ClRoomEntity room: list) {
            if (roomService.count(new QueryWrapper<ClRoomEntity>()
                    .eq("teaching_building_name",room.getTeachingBuildingName())
                    .eq("room_name",room.getRoomName())) > 0){
                return R.error(room.getTeachingBuildingName()+"已存在教室"+room.getRoomName());
            }
            room.setCreateTime(new Date());
        }
        roomService.saveBatch(list);
        return R.ok();
    }

    @ApiOperation(value = "教室列表",notes = "教室列表",response = ClRoomEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "一页多少条数据",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roomName",value = "教室",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "teachingBuildingName",value = "教学楼",required = false,dataType = "string",paramType = "query")
    })
    @GetMapping("/list")
    public R list(@ApiParam(hidden = true,required = false) @RequestParam Map<String, Object> params){

        PageUtils page = roomService.queryPage(params);
        return R.ok().put("page", page);

    }

    @ApiOperation(value = "删除教室",notes = "删除教室")
    @PostMapping("/delete")
    public R delete(@RequestBody @ApiParam  Integer[] roomIds){

        roomService.removeByIds(Arrays.asList(roomIds));

        return R.ok();
    }
    @ApiOperation(value = "教室占用信息",notes = "教室占用信息")
    @GetMapping("/useInfo")
    public R useInfo(@RequestParam(required = false) @ApiParam(value = "时间") Date date,
                     @RequestParam(required = false) @ApiParam(value = "教学楼") String teachingBuildingName,
                     @RequestParam(required = false) @ApiParam(value = "教室") String roomName){

        return R.ok();
    }

}

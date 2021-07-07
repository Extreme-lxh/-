package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.ClRoomMaterielEntity;
import io.renren.modules.sys.service.ClRoomMaterielService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Auther zxl
 * @Date 2020/3/27 11:57
 * @Description
 **/
@Api(value = "教室设施管理",tags = "教室设施管理")
@RestController
@RequestMapping("/sys/room/materiel")
public class ClRoomMaterielController {

    @Autowired
    ClRoomMaterielService materielService;


    @ApiOperation(value = "添加教室设施",notes = "只传roomId、type、num")
    @PostMapping("/save")
    public R save(@RequestBody List<ClRoomMaterielEntity> list){

        for (ClRoomMaterielEntity materielEntity: list) {
            if (materielService.count(new QueryWrapper<ClRoomMaterielEntity>()
                    .eq("room_id",materielEntity.getRoomId())
                    .eq("type",materielEntity.getType())) > 0){
                return R.error("请勿重复添加教室设施");
            }
            materielEntity.setCreateTime(new Date());
        }
        materielService.saveBatch(list);
        return R.ok();
    }
    @ApiOperation(value = "教室设施列表",response = ClRoomMaterielEntity.class)
    @PostMapping("/list")
    public R all(@RequestParam(required = true) Integer roomId,
                 @RequestParam(required = false) Integer type){

        return R.ok().put("list",materielService.list(new QueryWrapper<ClRoomMaterielEntity>()
                .eq("room_id",roomId)
                .eq(type != null,"type",type)));

    }
    @ApiOperation(value = "修改教室设施")
    @PostMapping("/update")
    public R update(@RequestBody ClRoomMaterielEntity entity){
        if (materielService.count(new QueryWrapper<ClRoomMaterielEntity>()
                .eq("room_id",entity.getRoomId())
                .eq("type",entity.getType())) > 0){
            return R.error("请勿重复添加教室设施");
        }
        materielService.updateById(entity);
        return R.ok();
    }

    @ApiOperation(value = "删除教室设施",notes = "删除教室设施")
    @PostMapping("/delete")
    public R delete(@RequestBody @ApiParam Integer[] ids){

        materielService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

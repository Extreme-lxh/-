package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ClRoomEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ClRoomService extends IService<ClRoomEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ClRoomEntity> infoByDate(Date date,String teachingBuildingName,String roomName);

}

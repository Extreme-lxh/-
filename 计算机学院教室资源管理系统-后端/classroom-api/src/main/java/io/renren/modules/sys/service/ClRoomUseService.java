package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ClRoomUseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ClRoomUseService extends IService<ClRoomUseEntity> {

    /**
     * 申请教室
     * @param entity
     */
    void apply(ClRoomUseEntity entity);

    PageUtils queryPage(Map<String, Object> params);

    List<ClRoomUseEntity> myList(Long userId);

    List<ClRoomUseEntity> useByDate(Integer roomId, Date date);
}

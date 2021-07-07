package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ClCourseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ClCourseService extends IService<ClCourseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Date getStartTime(Integer courseId);

    void delBatch(Integer[] ids);

    List<ClCourseEntity> all(Boolean isFinish);
}

package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ClCourseTimeEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ClCourseTimeService extends IService<ClCourseTimeEntity> {


    void saveList(List<ClCourseTimeEntity> list);

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 判断教室是否有课
     * @param date
     * @param startTime
     * @param endTime
     * @param roomId
     * @return
     */
    boolean haveCourse(Date date,Integer startTime,Integer endTime,Integer roomId);

    /**
     * 查询教室某日的课程
     * @param date
     * @return
     */
    List<ClCourseTimeEntity> courseByDate(Integer roomId,Date date);
}

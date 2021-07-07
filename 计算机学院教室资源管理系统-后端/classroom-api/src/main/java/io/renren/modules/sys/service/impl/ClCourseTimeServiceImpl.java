package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dao.ClCourseTimeDao;
import io.renren.modules.sys.entity.ClCourseTimeEntity;
import io.renren.modules.sys.service.ClCourseService;
import io.renren.modules.sys.service.ClCourseTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/27 16:09
 * @Description
 **/
@Slf4j
@Service
public class ClCourseTimeServiceImpl extends ServiceImpl<ClCourseTimeDao, ClCourseTimeEntity>
        implements ClCourseTimeService {

    @Autowired
    ClCourseService courseService;

    @Transactional
    @Override
    public void saveList(List<ClCourseTimeEntity> list) {
        for (ClCourseTimeEntity entity : list){
            // 参数校验
            ValidatorUtils.validateEntity(entity);
            Date startDate = courseService.getStartTime(entity.getCourseId());
            // 判断是否存在教室使用冲突的情况
            if (baseMapper.checkCourseTime(entity,startDate) > 0){
                throw new RRException("操作失败，存在教室使用冲突情况");
            }
            entity.setCreateTime(new Date());
        }
        this.saveBatch(list);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long current = Long.parseLong((String) params.get("page"));
        Long size = Long.parseLong((String) params.get("limit"));
        String courseName = (String)params.get("courseName");
        String className = (String)params.get("className");
        Object roomId = params.get("roomId");
        Object week = params.get("week");
        Page<ClCourseTimeEntity> page = new Page<ClCourseTimeEntity>(current,size);
        IPage<ClCourseTimeEntity> pages = baseMapper.getPageList(page,courseName,className,roomId,week);
        return new PageUtils(page);
    }

    @Override
    public boolean haveCourse(Date date, Integer startTime, Integer endTime, Integer roomId) {
        ClCourseTimeEntity entity = new ClCourseTimeEntity(roomId, DateUtils.dateToWeek(date),startTime,endTime);
        return baseMapper.checkCourseTime(entity,date) > 0;
    }

    @Override
    public List<ClCourseTimeEntity> courseByDate(Integer roomId,Date date) {
        int week = DateUtils.dateToWeek(date);
        log.info("roomId：{},date：{}，week：{}",roomId,date,week);
        return baseMapper.courseByDate(roomId,date,week);
    }
}

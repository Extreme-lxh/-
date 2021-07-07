package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ClCourseDao;
import io.renren.modules.sys.entity.ClCourseEntity;
import io.renren.modules.sys.entity.ClCourseTimeEntity;
import io.renren.modules.sys.service.ClCourseService;
import io.renren.modules.sys.service.ClCourseTimeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/27 15:59
 * @Description
 **/
@Service
public class ClCourseServiceImpl extends ServiceImpl<ClCourseDao, ClCourseEntity>
        implements ClCourseService {

    @Autowired
    ClCourseTimeService courseTimeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String courseName = (String)params.get("courseName");
        String className = (String)params.get("className");

        IPage<ClCourseEntity> page = this.page(
                new Query<ClCourseEntity>().getPage(params),
                new QueryWrapper<ClCourseEntity>()
                        .like(StringUtils.isNotBlank(courseName),"course_name", courseName)
                        .like(StringUtils.isNotBlank(className),"course_class",className)
                        .orderByDesc("create_time")
        );
        return new PageUtils(page);
    }

    @Override
    public Date getStartTime(Integer courseId) {
        return this.getOne(new QueryWrapper<ClCourseEntity>().select("course_id","start_date")
                .eq("course_id",courseId)).getStartDate();
    }

    @Transactional
    @Override
    public void delBatch(Integer[] ids) {
        for (Integer courseId: ids) {
            courseTimeService.remove(new QueryWrapper<ClCourseTimeEntity>().eq("course_id",courseId));
        }
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public List<ClCourseEntity> all(Boolean isFinish) {
        return baseMapper.all(isFinish);
    }
}

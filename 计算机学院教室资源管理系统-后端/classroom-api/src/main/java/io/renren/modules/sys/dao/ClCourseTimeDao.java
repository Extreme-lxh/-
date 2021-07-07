package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.entity.ClCourseTimeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Auther zxl
 * @Date 2020/3/27 16:01
 * @Description
 **/
@Mapper
public interface ClCourseTimeDao extends BaseMapper<ClCourseTimeEntity> {


    int checkCourseTime(@Param("entity") ClCourseTimeEntity entity,
                        @Param("startDate") Date startDate);

    IPage<ClCourseTimeEntity> getPageList(Page<ClCourseTimeEntity> page, @Param("courseName") String courseName,
                                          @Param("className") String className,
                                          @Param("roomId") Object roomId,@Param("week") Object week);

    List<ClCourseTimeEntity> courseByDate(@Param("roomId") Integer roomId,@Param("date") Date date,@Param("week") int week);
}

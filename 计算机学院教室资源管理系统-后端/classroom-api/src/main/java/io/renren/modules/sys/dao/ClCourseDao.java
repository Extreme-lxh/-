package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.ClCourseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClCourseDao extends BaseMapper<ClCourseEntity> {

    List<ClCourseEntity> all(@Param("isFinish") Boolean isFinish);
}

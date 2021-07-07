package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.entity.ClCourseTimeEntity;
import io.renren.modules.sys.entity.ClRoomUseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ClRoomUseDao extends BaseMapper<ClRoomUseEntity> {

    /**
     * 判断教室是否已经被预约
     * @param date
     * @param startTime
     * @param roomId
     * @return
     */
    int checkHaveUse(@Param("date") Date date, @Param("startTime") int startTime,@Param("rommId") Integer roomId);

    IPage<ClRoomUseEntity> getPageList(Page<ClRoomUseEntity> page, @Param("applyUserName") String applyUserName,
                                          @Param("useDate") Object useDate,
                                          @Param("roomId") Object roomId, @Param("applyState") Object applyState);

    List<ClRoomUseEntity> mylist(Long userId);

    List<ClRoomUseEntity> useByDate(@Param("roomId") Integer roomId,@Param("date") Date date);
}

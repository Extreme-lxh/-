package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ClRoomDao;
import io.renren.modules.sys.entity.ClRoomEntity;
import io.renren.modules.sys.entity.SysNoticeEntity;
import io.renren.modules.sys.service.ClCourseService;
import io.renren.modules.sys.service.ClCourseTimeService;
import io.renren.modules.sys.service.ClRoomService;
import io.renren.modules.sys.service.ClRoomUseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/27 10:33
 * @Description
 **/
@Service
public class ClRoomServiceImpl extends ServiceImpl<ClRoomDao, ClRoomEntity>
        implements ClRoomService {

    @Autowired
    ClCourseTimeService courseTimeService;
    @Autowired
    ClRoomUseService useService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roomName = (String)params.get("roomName");

        String teachingBuildingName = (String)params.get("teachingBuildingName");

        IPage<ClRoomEntity> page = this.page(
                new Query<ClRoomEntity>().getPage(params),
                new QueryWrapper<ClRoomEntity>()
                        .like(StringUtils.isNotBlank(teachingBuildingName),"teaching_building_name", teachingBuildingName)
                        .like(StringUtils.isNotBlank(roomName),"room_name",roomName)
                        .orderByDesc("create_time")
        );
        return new PageUtils(page);
    }

    @Override
    public List<ClRoomEntity> infoByDate(Date date,String teachingBuildingName,String roomName) {
        List<ClRoomEntity> list = this.list(new QueryWrapper<ClRoomEntity>()
                .like(StringUtils.isNotBlank(teachingBuildingName),"teaching_building_name",teachingBuildingName)
                .like(StringUtils.isNotBlank(roomName),"room_name",roomName)
                .orderByAsc("teaching_building_name","room_name"));
        for (ClRoomEntity roomEntity : list){
            // 课程信息
            roomEntity.setCourses(courseTimeService.courseByDate(roomEntity.getRoomId(),date));
            // 预约使用信息
            roomEntity.setUses(useService.useByDate(roomEntity.getRoomId(),date));
        }
        return list;
    }
}

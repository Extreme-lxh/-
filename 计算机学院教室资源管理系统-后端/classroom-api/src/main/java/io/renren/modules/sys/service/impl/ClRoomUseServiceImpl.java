package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dao.ClRoomUseDao;
import io.renren.modules.sys.entity.ClRoomUseEntity;
import io.renren.modules.sys.service.ClCourseTimeService;
import io.renren.modules.sys.service.ClRoomUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/28 17:13
 * @Description
 **/
@Service
public class ClRoomUseServiceImpl extends ServiceImpl<ClRoomUseDao, ClRoomUseEntity>
        implements ClRoomUseService {

    @Autowired
    ClCourseTimeService courseTimeService;

    @Transactional
    @Override
    public void apply(ClRoomUseEntity entity) {
        boolean haveCourse = courseTimeService.haveCourse(entity.getUseDate(),entity.getUseStartTime(),
                entity.getUseEndTime(),entity.getRoomId());
        if (haveCourse){
            throw new RRException("该时间段有课哦");
        }
        boolean haveUser = baseMapper.checkHaveUse(entity.getUseDate(),entity.getUseStartTime(),
                entity.getRoomId())>0;
        if (haveUser){
            throw new RRException("该时间段已被预约啦");
        }
        entity.setCreateTime(new Date());
        this.save(entity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long current = Long.parseLong((String) params.get("page"));
        Long size = Long.parseLong((String) params.get("limit"));
        String applyUserName = (String)params.get("applyUserName");
        Object roomId = params.get("roomId");
        Object applyState = params.get("applyState");
        Object useDate = params.get("useDate");
        Page<ClRoomUseEntity> page = new Page<ClRoomUseEntity>(current,size);
        IPage<ClRoomUseEntity> pages = baseMapper.getPageList(page,applyUserName,useDate,roomId,applyState);
        return new PageUtils(page);
    }

    @Override
    public List<ClRoomUseEntity> myList(Long userId) {
        return baseMapper.mylist(userId);
    }

    @Override
    public List<ClRoomUseEntity> useByDate(Integer roomId, Date date) {

        return baseMapper.useByDate(roomId, date);
    }
}

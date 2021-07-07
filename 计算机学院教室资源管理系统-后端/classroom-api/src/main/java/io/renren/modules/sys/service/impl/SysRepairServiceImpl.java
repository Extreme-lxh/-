package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dao.SysRepairDao;
import io.renren.modules.sys.entity.SysRepairEntity;
import io.renren.modules.sys.entity.SysReportEntity;
import io.renren.modules.sys.service.SysRepairService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/30 11:44
 * @Description
 **/
@Service
public class SysRepairServiceImpl extends ServiceImpl<SysRepairDao, SysRepairEntity>
        implements SysRepairService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long current = Long.parseLong((String) params.get("page"));
        Long size = Long.parseLong((String) params.get("limit"));
        Object userId = params.get("userId");
        Object roomId = params.get("roomId");
        Object state = params.get("state");
        Page<SysRepairEntity> page = new Page<SysRepairEntity>(current,size);
        IPage<SysRepairEntity> pages = baseMapper.getPageList(page,roomId,userId,state);
        return new PageUtils(page);
    }
}

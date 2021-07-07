package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dao.SysReportDao;
import io.renren.modules.sys.entity.SysReportEntity;
import io.renren.modules.sys.service.SysReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/30 11:42
 * @Description
 **/
@Service
public class SysReportServiceImpl extends ServiceImpl<SysReportDao, SysReportEntity>
        implements SysReportService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long current = Long.parseLong((String) params.get("page"));
        Long size = Long.parseLong((String) params.get("limit"));
        String userName = (String)params.get("userName");
        Object roomId = params.get("roomId");
        Page<SysReportEntity> page = new Page<SysReportEntity>(current,size);
        IPage<SysReportEntity> pages = baseMapper.getPageList(page,roomId,userName);
        return new PageUtils(page);
    }

    @Override
    public List<SysReportEntity> myLog(Long userId) {
        return baseMapper.myLog(userId);
    }
}

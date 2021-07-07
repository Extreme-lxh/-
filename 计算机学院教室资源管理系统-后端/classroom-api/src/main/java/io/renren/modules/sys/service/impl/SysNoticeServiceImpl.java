package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysNoticeDao;
import io.renren.modules.sys.entity.SysNoticeEntity;
import io.renren.modules.sys.service.SysNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/26 13:57
 * @Description
 **/
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNoticeEntity>
        implements SysNoticeService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");

        IPage<SysNoticeEntity> page = this.page(
                new Query<SysNoticeEntity>().getPage(params),
                new QueryWrapper<SysNoticeEntity>()
                        .like(StringUtils.isNotBlank(title),"title", title)
                        .orderByDesc("create_time")
        );
        return new PageUtils(page);
    }
}

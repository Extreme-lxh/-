package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysMsgDao;
import io.renren.modules.sys.entity.SysMsgEntity;
import io.renren.modules.sys.entity.SysNoticeEntity;
import io.renren.modules.sys.service.SysMsgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther zxl
 * @Date 2020/3/26 13:55
 * @Description
 **/
@Service
public class SysMsgServiceImpl extends ServiceImpl<SysMsgDao,SysMsgEntity>
        implements SysMsgService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userName = (String)params.get("userName");

        IPage<SysMsgEntity> page = this.page(
                new Query<SysMsgEntity>().getPage(params),
                new QueryWrapper<SysMsgEntity>()
                        .like(StringUtils.isNotBlank(userName),"user_name", userName)
                        .orderByDesc("create_time")
        );
        return new PageUtils(page);
    }
}

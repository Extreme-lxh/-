package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysNoticeEntity;

import java.util.Map;

public interface SysNoticeService extends IService<SysNoticeEntity> {

    /**
     * 列表
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
}

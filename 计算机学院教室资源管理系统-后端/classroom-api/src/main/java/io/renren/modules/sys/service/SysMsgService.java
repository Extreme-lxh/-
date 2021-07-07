package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysMsgEntity;

import java.util.Map;

public interface SysMsgService extends IService<SysMsgEntity> {

    /**
     * 列表
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
}

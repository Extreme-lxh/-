package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysRepairEntity;

import java.util.Map;

public interface SysRepairService extends IService<SysRepairEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

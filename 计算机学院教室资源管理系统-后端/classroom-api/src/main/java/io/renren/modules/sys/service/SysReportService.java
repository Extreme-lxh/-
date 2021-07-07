package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysReportEntity;

import java.util.List;
import java.util.Map;

public interface SysReportService extends IService<SysReportEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SysReportEntity> myLog(Long userId);
}

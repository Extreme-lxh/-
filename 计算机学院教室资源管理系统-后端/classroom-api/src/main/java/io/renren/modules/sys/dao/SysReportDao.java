package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.entity.SysReportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysReportDao extends BaseMapper<SysReportEntity> {

    IPage<SysReportEntity> getPageList(Page<SysReportEntity> page,@Param("roomId") Object roomId,
                                       @Param("userName") String userName);

    List<SysReportEntity> myLog(Long userId);
}

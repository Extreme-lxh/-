package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.entity.SysRepairEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRepairDao extends BaseMapper<SysRepairEntity> {

    IPage<SysRepairEntity> getPageList(Page<SysRepairEntity> page, @Param("roomId") Object roomId,
                                       @Param("userId") Object userId,@Param("state") Object state);
}

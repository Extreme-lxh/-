package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysMsgEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysMsgDao extends BaseMapper<SysMsgEntity> {
}

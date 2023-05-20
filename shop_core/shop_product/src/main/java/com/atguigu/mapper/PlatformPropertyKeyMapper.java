package com.atguigu.mapper;

import com.atguigu.entity.PlatformPropertyKey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 属性表 Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2023-05-15
 */
public interface PlatformPropertyKeyMapper extends BaseMapper<PlatformPropertyKey> {

    //List<PlatformPropertyKey> getPlatformPropertyKeyByCategoryId(Long category1Id, Long category2Id, Long category3Id);

    List<PlatformPropertyKey> getPlatformPropertyByCategoryId(Long category1Id, Long category2Id, Long category3Id);
}

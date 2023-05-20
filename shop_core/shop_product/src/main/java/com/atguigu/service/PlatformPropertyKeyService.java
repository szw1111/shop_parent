package com.atguigu.service;

import com.atguigu.entity.PlatformPropertyKey;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 属性表 服务类
 * </p>
 *
 * @author zw
 * @since 2023-05-15
 */
public interface PlatformPropertyKeyService extends IService<PlatformPropertyKey> {

    List<PlatformPropertyKey> getPlatformPropertyByCategoryId(Long category1Id, Long category2Id, Long category3Id);

    void savePlatformProperty(PlatformPropertyKey platformPropertyKey);
}

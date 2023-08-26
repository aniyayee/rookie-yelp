package com.rookie.domain.shop.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.domain.shop.dto.ShopDTO;

/**
 * <p>
 * 商铺信息表 服务类
 * </p>
 *
 * @author yayee
 */
public interface ShopService extends IService<ShopEntity> {

    ShopDTO queryById(Long id);
}

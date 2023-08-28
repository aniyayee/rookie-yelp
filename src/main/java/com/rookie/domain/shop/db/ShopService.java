package com.rookie.domain.shop.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.common.core.page.PageDTO;
import com.rookie.domain.shop.command.AddShopCommand;
import com.rookie.domain.shop.command.UpdateShopCommand;
import com.rookie.domain.shop.dto.ShopDTO;
import com.rookie.domain.shop.query.ShopQuery;

/**
 * <p>
 * 商铺信息表 服务类
 * </p>
 *
 * @author yayee
 */
public interface ShopService extends IService<ShopEntity> {

    PageDTO<ShopDTO> getShopList(ShopQuery query);

    ShopDTO getShopInfo(Long id);

    void addShop(AddShopCommand command);

    void updateShop(UpdateShopCommand command);

    void deleteById(Long id);
}

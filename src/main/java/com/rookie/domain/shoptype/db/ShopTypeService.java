package com.rookie.domain.shoptype.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.domain.shoptype.command.AddShopTypeCommand;
import com.rookie.domain.shoptype.command.UpdateShopTypeCommand;
import com.rookie.domain.shoptype.dto.ShopTypeDTO;
import java.util.List;

/**
 * <p>
 * 商铺类型表 服务类
 * </p>
 *
 * @author yayee
 */
public interface ShopTypeService extends IService<ShopTypeEntity> {

    List<ShopTypeDTO> findList();

    ShopTypeDTO getShopTypeInfo(Long id);

    void addShopType(AddShopTypeCommand command);

    void updateShopType(UpdateShopTypeCommand command);

    void deleteById(Long id);
}

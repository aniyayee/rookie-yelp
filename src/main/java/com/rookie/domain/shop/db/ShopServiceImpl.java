package com.rookie.domain.shop.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.constants.RedisConstants;
import com.rookie.common.core.page.PageDTO;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.domain.shop.command.AddShopCommand;
import com.rookie.domain.shop.command.UpdateShopCommand;
import com.rookie.domain.shop.dto.ShopDTO;
import com.rookie.domain.shop.query.ShopQuery;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商铺信息表 服务实现类
 * </p>
 *
 * @author yayee
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, ShopEntity> implements ShopService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageDTO<ShopDTO> getShopList(ShopQuery query) {
        Page<ShopEntity> page = this.page(query.toPage(), query.toQueryWrapper());
        List<ShopDTO> records = page.getRecords().stream().map(ShopDTO::new).collect(Collectors.toList());
        return new PageDTO<>(page.getTotal(), records);
    }

    @Override
    public ShopDTO getShopInfo(Long id) {
        // 1.查询redis缓存
        String shopKey = RedisConstants.CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(shopKey);
        if (StrUtil.isNotBlank(shopJson)) {
            // 2.命中 返回
            return JSONUtil.toBean(shopJson, ShopDTO.class);
        }
        // 3.判断空值
        if (StrUtil.equals("", shopJson)) {
            return null;
        }
        // 4.非空值 查数据库
        ShopEntity entity = baseMapper.selectById(id);
        if (ObjectUtil.isEmpty(entity)) {
            // 5.不存在 返回错误信息 记录空值
            stringRedisTemplate.opsForValue().set(shopKey, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.SECONDS);
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "商铺");
        }
        // 6.存在 存入缓存 返回
        ShopDTO dto = BeanUtil.copyProperties(entity, ShopDTO.class);
        stringRedisTemplate.opsForValue()
            .set(shopKey, JSONUtil.toJsonStr(dto), RedisConstants.CACHE_SHOP_TTL, TimeUnit.SECONDS);
        return dto;
    }

    @Override
    public void addShop(AddShopCommand command) {
        ShopEntity entity = BeanUtil.copyProperties(command, ShopEntity.class);
        baseMapper.insert(entity);
    }

    @Override
    public void updateShop(UpdateShopCommand command) {

    }

    @Override
    public void deleteById(Long id) {

    }
}

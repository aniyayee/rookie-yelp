package com.rookie.domain.shop.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.constants.RedisConstants;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.domain.shop.dto.ShopDTO;
import java.util.concurrent.TimeUnit;
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
    public ShopDTO queryById(Long id) {
        // 1.查询redis缓存
        String shopKey = RedisConstants.CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(shopKey);
        if (StrUtil.isNotBlank(shopJson)) {
            // 2.命中 返回
            return JSONUtil.toBean(shopJson, ShopDTO.class);
        }
        // 3.未命中 查数据库
        ShopEntity entity = baseMapper.selectById(id);
        if (ObjectUtil.isEmpty(entity)) {
            // 4.不存在 返回错误信息
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "商铺");
        }
        // 5.存在 存入缓存 返回
        ShopDTO dto = BeanUtil.copyProperties(entity, ShopDTO.class);
        stringRedisTemplate.opsForValue()
            .set(shopKey, JSONUtil.toJsonStr(dto), RedisConstants.CACHE_SHOP_TTL, TimeUnit.SECONDS);
        return dto;
    }
}

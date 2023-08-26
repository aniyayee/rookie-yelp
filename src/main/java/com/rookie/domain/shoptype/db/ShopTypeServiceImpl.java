package com.rookie.domain.shoptype.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.constants.RedisConstants;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode.Internal;
import com.rookie.domain.shoptype.dto.ShopTypeDTO;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商铺类型表 服务实现类
 * </p>
 *
 * @author yayee
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopTypeEntity> implements ShopTypeService {

    @Resource
    private RedisTemplate<String, ShopTypeDTO> redisTemplate;

    @Override
    public List<ShopTypeDTO> findList() {
        // 1.查询redis缓存
        String shopTypeKey = RedisConstants.CACHE_SHOP_TYPE_KEY;
        ListOperations<String, ShopTypeDTO> opsForList = redisTemplate.opsForList();
        if (ObjectUtil.isNotEmpty(opsForList)) {
            // 2.命中 返回
            List<ShopTypeDTO> dtoList = opsForList.range(shopTypeKey, 0, -1);
            if (ObjectUtil.isNotEmpty(dtoList)) {
                return dtoList;
            }
        }
        // 3.未命中 查数据库
        List<ShopTypeEntity> entityList = baseMapper.selectList(null);
        if (ObjectUtil.isEmpty(entityList)) {
            // 4.不存在 返回错误信息
            throw new ApiException(Internal.DB_INTERNAL_ERROR);
        }
        // 5.存在 存入缓存 返回
        List<ShopTypeDTO> dtoList = BeanUtil.copyToList(entityList, ShopTypeDTO.class);
        opsForList.leftPushAll(shopTypeKey, dtoList);
        redisTemplate.expire(shopTypeKey, RedisConstants.CACHE_SHOP_TYPE_TTL, TimeUnit.SECONDS);
        return dtoList;
    }
}

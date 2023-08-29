package com.rookie.domain.shoptype.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.constants.RedisConstants;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.common.exception.error.ErrorCode.Internal;
import com.rookie.domain.shoptype.command.AddShopTypeCommand;
import com.rookie.domain.shoptype.command.UpdateShopTypeCommand;
import com.rookie.domain.shoptype.dto.ShopTypeDTO;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<String> stringList = dtoList.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());
        stringRedisTemplate.opsForList().rightPushAll(shopTypeKey, stringList);
        stringRedisTemplate.expire(shopTypeKey, RedisConstants.CACHE_SHOP_TYPE_TTL, TimeUnit.SECONDS);
        return dtoList;
    }

    @Override
    public ShopTypeDTO getShopTypeInfo(Long id) {
        ShopTypeEntity entity = baseMapper.selectById(id);
        if (ObjectUtil.isEmpty(entity)) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "商铺类型");
        }
        return BeanUtil.copyProperties(entity, ShopTypeDTO.class);
    }

    @Override
    public void addShopType(AddShopTypeCommand command) {
        ShopTypeEntity entity = BeanUtil.copyProperties(command, ShopTypeEntity.class);
        baseMapper.insert(entity);
    }

    @Transactional
    @Override
    public void updateShopType(UpdateShopTypeCommand command) {
        Long id = command.getId();
        ShopTypeEntity byId = baseMapper.selectById(id);
        if (ObjectUtil.isEmpty(byId)) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "商铺类型");
        }
        String shopTypeKey = RedisConstants.CACHE_SHOP_TYPE_KEY;
        ShopTypeEntity entity = BeanUtil.copyProperties(command, ShopTypeEntity.class);
        baseMapper.updateById(entity);
        stringRedisTemplate.delete(shopTypeKey);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        String shopTypeKey = RedisConstants.CACHE_SHOP_TYPE_KEY;
        baseMapper.deleteById(id);
        stringRedisTemplate.delete(shopTypeKey);
    }
}

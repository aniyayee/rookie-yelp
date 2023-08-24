package com.rookie.domain.user.db;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.constants.ErrorCode;
import com.rookie.common.constants.ErrorCode.Business;
import com.rookie.common.exception.ApiException;
import com.rookie.domain.user.command.AddUserCommand;
import com.rookie.domain.user.command.UpdateUserCommand;
import com.rookie.domain.user.command.UpdateUserPasswordCommand;
import com.rookie.domain.user.dto.SysUserDTO;
import com.rookie.domain.user.query.UserQuery;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author yayee
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {

    @Override
    public void addUser(AddUserCommand command) {
        SysUserEntity entity = BeanUtil.copyProperties(command, SysUserEntity.class);
        if (this.isUserNameDuplicated(entity.getUsername())) {
            throw new ApiException(ErrorCode.Business.USER_NAME_IS_NOT_UNIQUE.getMsg());
        }
        if (this.isPhoneNumberDuplicated(entity.getPhoneNumber(), entity.getUserId())) {
            throw new ApiException(Business.PHONE_NUMBER_IS_NOT_UNIQUE.getMsg());
        }
        baseMapper.insert(entity);
    }

    @Override
    public void updateUser(UpdateUserCommand command) {
        SysUserEntity entity = BeanUtil.copyProperties(command, SysUserEntity.class);
        if (this.isPhoneNumberDuplicated(entity.getPhoneNumber(), entity.getUserId())) {
            throw new ApiException(Business.PHONE_NUMBER_IS_NOT_UNIQUE.getMsg());
        }
        baseMapper.updateById(entity);
    }

    @Override
    public void deleteById(Long userId) {
        baseMapper.deleteById(userId);
    }

    @Override
    public SysUserDTO queryById(Long userId) {
        SysUserEntity entity = baseMapper.selectById(userId);
        return BeanUtil.copyProperties(entity, SysUserDTO.class);
    }

    @Override
    public List<SysUserDTO> findList(UserQuery query) {
        return baseMapper.findList(query);
    }

    @Override
    public void updatePassword(UpdateUserPasswordCommand command) {
        SysUserEntity entity = baseMapper.selectById(command.getUserId());
        if (StringUtils.equals(command.getNewPassword(), command.getOldPassword())) {
            throw new ApiException(ErrorCode.Business.SAME_NEW_AND_OLD_PASSWORDS.getMsg());
        }
        if (StringUtils.equals(entity.getPassword(), command.getOldPassword())) {
            entity.setPassword(command.getNewPassword());
            baseMapper.updateById(entity);
        } else {
            throw new ApiException(ErrorCode.Business.USER_PASSWORD_IS_NOT_CORRECT.getMsg());
        }
    }

    @Override
    public SysUserDTO queryByUsername(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        SysUserEntity entity = baseMapper.selectOne(queryWrapper);
        return BeanUtil.copyProperties(entity, SysUserDTO.class);
    }

    @Override
    public boolean isUserNameDuplicated(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public boolean isPhoneNumberDuplicated(String phoneNumber, Long userId) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(userId != null, "user_id", userId)
            .eq("phone_number", phoneNumber);
        return baseMapper.exists(queryWrapper);
    }
}

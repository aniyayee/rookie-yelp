package com.rookie.customize.service.login;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.rookie.common.constants.RedisConstants;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.customize.service.login.command.LoginCommand;
import com.rookie.domain.user.db.ISysUserService;
import com.rookie.domain.user.dto.SysUserDTO;
import com.rookie.utils.RegexUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yayee
 */
@Slf4j
@Component
public class LoginService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ISysUserService sysUserService;

    public void generateCaptcha(String phone) {
        // 1.校验手机号
        if (RegexUtil.isPhoneInvalid(phone)) {
            throw new ApiException(Business.USER_PHONE_FORMAT_ERROR);
        }
        // 2.生成验证码
        String captcha = RandomUtil.randomString(6);
        // 3.redis缓存
        stringRedisTemplate.opsForValue()
            .set(RedisConstants.LOGIN_CAPTCHA_KEY + phone, captcha, RedisConstants.LOGIN_CAPTCHA_TTL, TimeUnit.SECONDS);
        log.info("{} - 验证码是: {}", phone, captcha);
    }

    public String login(LoginCommand loginCommand) {
        // 1.校验手机号和验证码
        String phone = loginCommand.getPhone();
        String captcha = loginCommand.getCaptcha();
        String cacheCaptcha = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CAPTCHA_KEY + phone);
        if (StringUtils.isBlank(cacheCaptcha) || !StringUtils.equals(cacheCaptcha, captcha)) {
            // 2.校验不通过
            throw new ApiException(Business.LOGIN_CAPTCHA_CODE_WRONG);
        }
        // 3.校验通过 查询用户是否注册
        SysUserDTO userDTO = sysUserService.queryByPhone(phone);
        if (ObjectUtil.isEmpty(userDTO)) {
            // 4.用户不存在 创建默认用户
            userDTO = sysUserService.createDefaultUserWithPhone(phone);
        }
        // 5.保存用户信息到redis
        // 5.1 生成token
        String token = UUID.fastUUID().toString(true);
        // 5.2 dto转化位hash
        String[] ignoreProperties = {"password", "nickname", "email", "remark"};
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
            CopyOptions.create().setIgnoreNullValue(true)
                .setIgnoreProperties(ignoreProperties)
                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 5.2 记录用户信息到redis
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 5.3 设置token过期时间
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.SECONDS);
        return token;
    }
}

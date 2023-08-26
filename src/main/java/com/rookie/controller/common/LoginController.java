package com.rookie.controller.common;

import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.customize.service.login.LoginService;
import com.rookie.customize.service.login.command.LoginCommand;
import com.rookie.domain.user.command.AddUserCommand;
import com.rookie.domain.user.db.ISysUserService;
import com.rookie.domain.user.dto.SysUserDTO;
import com.rookie.utils.CurrentUserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yayee
 */
@Api(value = "Login Interfaces", tags = "Login Interfaces")
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private ISysUserService userService;

    @ApiOperation("验证码")
    @GetMapping("/captcha/{phone}")
    public ResponseDTO<Void> getCaptcha(@PathVariable String phone) {
        loginService.generateCaptcha(phone);
        return ResponseDTO.ok();
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseDTO<String> login(@RequestBody LoginCommand loginCommand) {
        String token = loginService.login(loginCommand);
        return ResponseDTO.ok(token);
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/getLoginUserInfo")
    public ResponseDTO<SysUserDTO> getLoginUserInfo() {
        Long userId = CurrentUserHolder.getCurrentUser().getUserId();
        return ResponseDTO.ok(userService.queryById(userId));
    }

    @ApiOperation("注册接口")
    @PostMapping("/register")
    public ResponseDTO<Void> register(@RequestBody AddUserCommand command) {
        throw new ApiException(Business.COMMON_UNSUPPORTED_OPERATION);
    }
}

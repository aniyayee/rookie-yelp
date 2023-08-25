package com.rookie.customize.service.login.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class LoginCommand {

    @NotBlank(message = "could not be empty")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "invalid phone")
    private String phone;

    @NotBlank(message = "could not be empty")
    @Size(min = 6, max = 6, message = "captcha should be 6")
    private String captcha;
}

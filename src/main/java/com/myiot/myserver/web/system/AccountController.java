package com.myiot.myserver.web.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.vo.system.PasswordRequest;
import com.myiot.myserver.data.vo.system.RegisterRequest;
import com.myiot.myserver.service.system.AccountService;
import com.myiot.myserver.web.basic.BasicController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author origin
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BasicController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @ResponseBody
    public Response register(@RequestBody RegisterRequest request) {
        if (null == request || StringUtils.isBlank(request.getAccountName())
            || StringUtils.isBlank(request.getPassword())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = accountService.register(request);
        return fromActionResult(result);
    }


    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestBody RegisterRequest request) {
        if (null == request || StringUtils.isBlank(request.getAccountName())
                || StringUtils.isBlank(request.getPassword())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = accountService.login(request);
        return fromActionResult(result);
    }

    @ApiOperation(value = "重置密码")
    @PutMapping("/resetPwd/{userId}")
    @ResponseBody
    public Response resetPwd(@PathVariable String userId) {
        ActionResult result = accountService.resetPassword(userId);
        return fromActionResult(result);
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/updatePwd")
    @ResponseBody
    public Response resetPwd(@RequestBody PasswordRequest passwordRequest) {
        if (null == passwordRequest || StringUtils.isBlank(passwordRequest.getUserId())
                || StringUtils.isBlank(passwordRequest.getPwdOld())
                || StringUtils.isBlank(passwordRequest.getPwdNew())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = accountService.updatePassword(passwordRequest);
        return fromActionResult(result);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete")
    @ResponseBody
    public Response deleteAccounts(@RequestBody List<String> userIds) {
        return fromActionResult(accountService.deleteAccount(userIds));
    }
}

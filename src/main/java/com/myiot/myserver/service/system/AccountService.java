package com.myiot.myserver.service.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.vo.system.PasswordRequest;
import com.myiot.myserver.data.vo.system.RegisterRequest;

import java.util.List;

/**
 * @author origin
 */
public interface AccountService {

    /**
     * 用户注册
     * @param request
     * @return
     */
    ActionResult register(RegisterRequest request);

    /**
     * 用户登录
     * @param request
     * @return
     */
    ActionResult login(RegisterRequest request);

    /**
     * 重置密码
     * @param userId
     * @return
     */
    ActionResult resetPassword(String userId);

    /**
     * 修改密码
     * @param passwordRequest
     * @return
     */
    ActionResult updatePassword(PasswordRequest passwordRequest);

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    ActionResult deleteAccount(List<String> userIds);

}

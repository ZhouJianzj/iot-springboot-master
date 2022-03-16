package com.myiot.myserver.service.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.vo.system.UserInfo;
import com.myiot.myserver.data.vo.system.UserQuery;

/**
 * @author origin
 */
public interface UserService {

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    ActionResult addUser(UserInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    ActionResult modifyUser(UserInfo userInfo);

    /**
     * 分页查询用户
     * @param query
     * @return
     */
    ActionResult queryUserByPage(UserQuery query);

    /**
     * 查询用户列表
     * @param query
     * @return
     */
    ActionResult queryUserList(UserQuery query);

    /**
     * 查询用户的菜单
     * @param userId
     * @return
     */
    ActionResult queryUserMenus(String userId);

}

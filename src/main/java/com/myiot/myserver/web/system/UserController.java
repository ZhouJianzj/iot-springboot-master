package com.myiot.myserver.web.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.vo.system.UserInfo;
import com.myiot.myserver.data.vo.system.UserQuery;
import com.myiot.myserver.service.system.UserService;
import com.myiot.myserver.web.basic.BasicController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author orign
 */
@RestController
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "创建用户")
    @PostMapping("/create")
    public Response createUser(@RequestBody UserInfo userInfo) {
        if (null == userInfo || StringUtils.isBlank(userInfo.getUserName())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = userService.addUser(userInfo);
        return fromActionResult(result);
    }

    @ApiOperation(value = "编辑用户")
    @PutMapping("/modify")
    public Response modifyUser(@RequestBody UserInfo userInfo) {
        if (null == userInfo || StringUtils.isBlank(userInfo.getId())
                || StringUtils.isBlank(userInfo.getUserName())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = userService.modifyUser(userInfo);
        return fromActionResult(result);
    }

    @ApiOperation(value = "分页查询用户")
    @PostMapping("/query")
    @ResponseBody
    public Response queryUserByPage(@RequestBody UserQuery query) {
        return fromActionResult(userService.queryUserByPage(query));
    }

    @ApiOperation(value = "列表查询用户")
    @PostMapping("/queryList")
    @ResponseBody
    public Response queryUserList(@RequestBody UserQuery query) {
        return fromActionResult(userService.queryUserList(query));
    }

    @ApiOperation(value = "查询用户的菜单")
    @GetMapping("/queryMenus/{userId}")
    @ResponseBody
    public Response queryUserMenus(@PathVariable String userId) {
        return fromActionResult(userService.queryUserMenus(userId));
    }

}

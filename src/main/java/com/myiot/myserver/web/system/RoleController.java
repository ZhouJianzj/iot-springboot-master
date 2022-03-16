package com.myiot.myserver.web.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.data.vo.system.RoleInfo;
import com.myiot.myserver.service.system.impl.RoleServiceImpl;
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
@RequestMapping("/role")
public class RoleController extends BasicController {

    @Autowired
    private RoleServiceImpl roleService;

    @ApiOperation(value = "创建角色")
    @PostMapping("/create")
    @ResponseBody
    public Response addRole(@RequestBody RoleInfo roleInfo) {
        if (StringUtils.isBlank(roleInfo.getName())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = roleService.addRole(roleInfo);
        return fromActionResult(result);
    }

    @ApiOperation(value = "编辑角色")
    @PutMapping("/modify")
    @ResponseBody
    public Response modifyRole(@RequestBody RoleInfo roleInfo) {
        if (StringUtils.isBlank(roleInfo.getId()) ||
                StringUtils.isBlank(roleInfo.getName())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = roleService.modifyRole(roleInfo);
        return fromActionResult(result);
    }

    @ApiOperation(value = "查询角色")
    @GetMapping("/query/{roleId}")
    @ResponseBody
    public Response queryRole(@PathVariable String roleId) {
        return fromActionResult(roleService.queryById(roleId));
    }

    @ApiOperation(value = "分页查询角色")
    @PostMapping("/query")
    @ResponseBody
    public Response queryRolesByPage(@RequestBody BaseQuery query) {
        return fromActionResult(roleService.queryRoles(query));
    }

    @ApiOperation(value = "查询角色配置的菜单")
    @GetMapping("/queryMenus/{roleId}")
    @ResponseBody
    public Response queryMenusByRole(@PathVariable String roleId) {
        return fromActionResult(roleService.queryMenuByRoleId(roleId));
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete")
    @ResponseBody
    public Response deleteRoles(@RequestBody List<String> roleIds) {
        return fromActionResult(roleService.deleteRoles(roleIds));
    }
}

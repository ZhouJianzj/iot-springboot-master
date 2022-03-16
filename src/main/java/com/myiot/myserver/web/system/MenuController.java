package com.myiot.myserver.web.system;

import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.po.system.Menu;
import com.myiot.myserver.service.system.impl.MenuServiceImpl;
import com.myiot.myserver.web.basic.BasicController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author origin
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BasicController {

    @Autowired
    private MenuServiceImpl menuService;

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/queryAll")
    @ResponseBody
    public Response queryAll() {
        return fromActionResult(menuService.queryAllMenus());
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("/create")
    @ResponseBody
    public Response addMenu(@RequestBody Menu menu) {
        if (StringUtils.isBlank(menu.getCode()) ||
                StringUtils.isBlank(menu.getName())) {
            return failInvalidInput("非法输入");
        }

        if (null == menu.getParentId()) {
            menu.setParentId(0L);
        }
        return fromActionResult(menuService.addMenu(menu));
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/remove")
    @ResponseBody
    public Response removeMenus(@RequestBody List<Long> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return success("操作成功");
        }

        return fromActionResult(menuService.deleteMenu(menuIds));
    }

    @ApiOperation(value = "查询菜单")
    @GetMapping("/queryById/{menuId}")
    @ResponseBody
    public Response queryById(@PathVariable String menuId) {
        return fromActionResult(menuService.queryById(Long.valueOf(menuId)));
    }

    @ApiOperation(value = "编辑菜单")
    @PutMapping("/modify")
    @ResponseBody
    public Response modifyMenu(@RequestBody Menu menu) {
        if (null == menu.getId() ||
                StringUtils.isBlank(menu.getCode()) ||
                StringUtils.isBlank(menu.getName())) {
            return failInvalidInput("非法输入");
        }

        if (null == menu.getParentId()) {
            menu.setParentId(0L);
        }
        return fromActionResult(menuService.modifyMenu(menu));
    }
}

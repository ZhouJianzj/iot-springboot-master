package com.myiot.myserver.service.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.po.system.Menu;

import java.util.List;

/**
 * @author origin
 */
public interface MenuService {

    /**
     * 查询所有菜单
     * @return
     */
    ActionResult queryAllMenus();

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    ActionResult addMenu(Menu menu);

    /**
     * 删除菜单
     * @param menuIds
     * @return
     */
    ActionResult deleteMenu(List<Long> menuIds);

    /**
     * 根据ID查询菜单
     * @param menuId
     * @return
     */
    ActionResult queryById(Long menuId);

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    ActionResult modifyMenu(Menu menu);

}

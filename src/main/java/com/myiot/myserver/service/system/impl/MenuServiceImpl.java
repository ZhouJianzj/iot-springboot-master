package com.myiot.myserver.service.system.impl;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.system.Menu;
import com.myiot.myserver.data.vo.system.MenuInfo;
import com.myiot.myserver.mapper.system.MenuMapper;
import com.myiot.myserver.service.system.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author origin
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ActionResult queryAllMenus() {
        try {
            List<MenuInfo> retList = menuMapper.queryAll();
            return new ActionResult(Constant.RESULT_SUCC, "", retList);
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult addMenu(Menu menu) {
        if (0 < menuMapper.countByName(menu.getParentId(), menu.getName())) {
            return new ActionResult(Constant.RESULT_FAIL,  "菜单名称重复");
        }

        try {
            menuMapper.insert(menu);
            return new ActionResult(Constant.RESULT_SUCC, "添加成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult deleteMenu(List<Long> menuIds) {
        ActionResult actionResult = null;

        try {
            menuMapper.deleteMenus(menuIds);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "删除成功");
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult queryById(Long menuId) {
        try {
            MenuInfo menu = menuMapper.queryById(menuId);
            return new ActionResult(Constant.RESULT_SUCC, "", menu);
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult modifyMenu(Menu menu) {
        Menu menuDB = menuMapper.selectByPrimaryKey(menu.getId());
        if (null == menuDB) {
            return new ActionResult(Constant.RESULT_FAIL,  "菜单已不存在");
        }

        if (!StringUtils.equals(menuDB.getName(), menu.getName())
            && 0 < menuMapper.countByName(menu.getParentId(), menu.getName())) {
            return new ActionResult(Constant.RESULT_FAIL,  "菜单名称重复");
        }

        try {
            menuMapper.updateByPrimaryKey(menu);
            return new ActionResult(Constant.RESULT_SUCC, "编辑成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }
}

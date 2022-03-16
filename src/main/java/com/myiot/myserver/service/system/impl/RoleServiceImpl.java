package com.myiot.myserver.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.system.Menu;
import com.myiot.myserver.data.po.system.Role;
import com.myiot.myserver.data.po.system.RoleMenu;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.data.vo.system.RoleInfo;
import com.myiot.myserver.mapper.system.RoleMapper;
import com.myiot.myserver.mapper.system.RoleMenuMapper;
import com.myiot.myserver.service.system.RoleService;
import com.myiot.myserver.utils.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author origin
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public ActionResult addRole(RoleInfo roleInfo) {
        if (0 < roleMapper.countByName(roleInfo.getName())) {
            return new ActionResult(Constant.RESULT_FAIL, "角色名称已存在");
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleInfo, role);
        role.setId(UuidUtil.createDecimalId());

        try {
            roleMapper.insert(role);
            if (CollectionUtils.isNotEmpty(roleInfo.getMenuList())) {
                batchAddMenu(role.getId(), roleInfo.getMenuList());
            }
            return new ActionResult(Constant.RESULT_SUCC, role.getId());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }

    }

    @Override
    public ActionResult modifyRole(RoleInfo roleInfo) {
        Role roleDB = roleMapper.selectByPrimaryKey(roleInfo.getId());
        if (null == roleDB) {
            return new ActionResult(Constant.RESULT_FAIL, "角色已不存在");
        }

        if (!StringUtils.equals(roleDB.getName(), roleInfo.getName())
           && 0 < roleMapper.countByName(roleInfo.getName())) {
            return new ActionResult(Constant.RESULT_FAIL, "角色名称重复");
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleInfo, role);

        try {
            roleMapper.updateByPrimaryKey(role);
            roleMenuMapper.removeByRoleId(role.getId());
            if (CollectionUtils.isNotEmpty(roleInfo.getMenuList())) {
                batchAddMenu(role.getId(), roleInfo.getMenuList());
            }
            return new ActionResult(Constant.RESULT_SUCC, role.getId());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    private void batchAddMenu(String roleId, List<Menu> menuList) {
        List<RoleMenu> roleMenus = new ArrayList<>(menuList.size());
        for (Menu menu : menuList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menu.getId());
            roleMenus.add(roleMenu);
        }

        roleMenuMapper.insertBatch(roleMenus);
    }

    @Override
    public ActionResult queryById(String roleId) {
        try {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if (null == role) {
                return new ActionResult(Constant.RESULT_FAIL, "角色已不存在");
            }

            List<Menu> menuList = roleMenuMapper.queryRoleMenus(roleId);

            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setId(role.getId());
            roleInfo.setName(role.getName());
            roleInfo.setRemark(role.getRemark());
            roleInfo.setMenuList(menuList);

            return new ActionResult(Constant.RESULT_SUCC, "查询成功", roleInfo);
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult queryRoles(BaseQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());

        List<RoleInfo> roleList;
        ActionResult actionResult = null;

        try {
            roleList = roleMapper.queryRoles(query);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "查询成功", new PageInfo<>(roleList));
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult queryMenuByRoleId(String roleId) {
        ActionResult actionResult = null;

        try {
            List<Menu> menuList = roleMenuMapper.queryRoleMenus(roleId);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "", menuList);
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult deleteRoles(List<String> roleIds) {
        ActionResult actionResult = null;

        try {
            roleMapper.deleteRoles(roleIds);
            roleMenuMapper.deleteRoleMenus(roleIds);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "删除成功");
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }
}

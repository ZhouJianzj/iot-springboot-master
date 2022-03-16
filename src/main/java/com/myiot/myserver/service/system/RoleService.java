package com.myiot.myserver.service.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.data.vo.system.RoleInfo;

import java.util.List;

/**
 * @author origin
 */
public interface RoleService {

    /**
     * 创建角色
     * @param roleInfo
     * @return
     */
    ActionResult addRole(RoleInfo roleInfo);

    /**
     * 编辑角色
     * @param roleInfo
     * @return
     */
    ActionResult modifyRole(RoleInfo roleInfo);

    /**
     * 根据ID查询角色
     * @param roleId
     * @return
     */
    ActionResult queryById(String roleId);

    /**
     * 分页查询角色
     * @return
     */
    ActionResult queryRoles(BaseQuery query);

    /**
     * 查询角色对应的菜单
     * @param roleId
     * @return
     */
    ActionResult queryMenuByRoleId(String roleId);

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    ActionResult deleteRoles(List<String> roleIds);

}

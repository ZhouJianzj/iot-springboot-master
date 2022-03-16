package com.myiot.myserver.service.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.vo.system.DepartmentInfo;
import com.myiot.myserver.data.vo.system.DepartmentQuery;

import java.util.List;

/**
 * @author origin
 */
public interface DepartmentService {

    /**
     * 新增部门
     * @param departmentInfo
     * @return
     */
    ActionResult addDepartment(DepartmentInfo departmentInfo);

    /**
     * 编辑部门
     * @param departmentInfo
     * @return
     */
    ActionResult modifyDepartment(DepartmentInfo departmentInfo);

    /**
     * 查询符合条件的部门
     * @param departmentQuery
     * @return
     */
    ActionResult queryDepartments(DepartmentQuery departmentQuery);

    /**
     * 查询部门列表
     * @param departmentQuery
     * @return
     */
    ActionResult queryList(DepartmentQuery departmentQuery);

    /**
     * 为部门设置角色
     * @param deptId
     * @param roleId
     * @return
     */
    ActionResult updateDepartmentWithRole(String deptId, String roleId);

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    ActionResult deleteDepartment(String deptId);

}

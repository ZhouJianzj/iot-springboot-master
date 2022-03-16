package com.myiot.myserver.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.system.Department;
import com.myiot.myserver.data.vo.system.DepartmentInfo;
import com.myiot.myserver.data.vo.system.DepartmentQuery;
import com.myiot.myserver.mapper.system.DepartmentMapper;
import com.myiot.myserver.service.system.DepartmentService;
import com.myiot.myserver.utils.UuidUtil;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author origin
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public ActionResult addDepartment(DepartmentInfo departmentInfo) {
        if (0 > departmentMapper.countByDeptName(departmentInfo.getParentId(), departmentInfo.getName())) {
            return new ActionResult(Constant.RESULT_FAIL, "同级别部门名称不能重复");
        }

        Department department = new Department();
        BeanUtils.copyProperties(departmentInfo, department);
        department.setLeaf(0);

        department.setId(UuidUtil.createDecimalId());

        try {
            departmentMapper.insert(department);
            departmentMapper.updateDeptLeaf(department.getParentId(), departmentMapper.countChildDept(department.getParentId()));
            return new ActionResult(Constant.RESULT_SUCC, department.getId());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult modifyDepartment(DepartmentInfo departmentInfo) {
        Department department = departmentMapper.findByName(departmentInfo.getParentId(), departmentInfo.getName());
        if (department != null && !StringUtils.equals(department.getId(), departmentInfo.getId())) {
            return new ActionResult(Constant.RESULT_FAIL, "同级别部门名称不能重复");
        }

        Department deptNew = new Department();
        BeanUtils.copyProperties(departmentInfo, deptNew);
        deptNew.setLeaf(departmentMapper.countChildDept(deptNew.getId()));

        try {
            departmentMapper.updateByPrimaryKey(deptNew);
            departmentMapper.updateDeptLeaf(deptNew.getParentId(), departmentMapper.countChildDept(deptNew.getParentId()));
            return new ActionResult(Constant.RESULT_SUCC, deptNew.getId());
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
    }

    @Override
    public ActionResult queryDepartments(DepartmentQuery departmentQuery) {
        PageHelper.startPage(departmentQuery.getPageNum(), departmentQuery.getPageSize());

        List<DepartmentInfo> depList;
        ActionResult actionResult = null;

        try {
            depList = departmentMapper.queryByPage(departmentQuery);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "", new PageInfo<>(depList));
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult queryList(DepartmentQuery departmentQuery) {
        List<DepartmentInfo> depList;
        ActionResult actionResult = null;

        try {
            depList = departmentMapper.queryByPage(departmentQuery);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "", depList);
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult updateDepartmentWithRole(String deptId, String roleId) {
        Department dept = departmentMapper.selectByPrimaryKey(deptId);
        if (null == dept) {
            return new ActionResult(Constant.RESULT_FAIL, "该部门已不存在");
        }

        dept.setRoleId(roleId);
        ActionResult actionResult = null;

        try {
            departmentMapper.updateByPrimaryKey(dept);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "",  deptId);
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }

    @Override
    public ActionResult deleteDepartment(String deptId) {
        ActionResult actionResult = null;

        try {
            if (0 < departmentMapper.countChildDept(deptId)) {
                return new ActionResult(Constant.RESULT_FAIL, "该部门存在下级部门，不能删除");
            }
            departmentMapper.deleteDepartment(deptId);
            actionResult = new ActionResult(Constant.RESULT_SUCC, "删除成功");
        } catch (Exception e) {
            actionResult = new ActionResult(Constant.RESULT_ERROR,  e.getLocalizedMessage());
        }
        return actionResult;
    }
}

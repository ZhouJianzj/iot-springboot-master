package com.myiot.myserver.web.system;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.vo.system.DepartmentInfo;
import com.myiot.myserver.data.vo.system.DepartmentQuery;
import com.myiot.myserver.data.vo.system.DepartmentRoleRequest;
import com.myiot.myserver.service.system.DepartmentService;
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
@RequestMapping("/department")
public class DepartmentController extends BasicController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "创建部门")
    @PostMapping("/create")
    @ResponseBody
    public Response addDepartment(@RequestBody DepartmentInfo departmentInfo) {
        if (StringUtils.isBlank(departmentInfo.getCode()) ||
            StringUtils.isBlank(departmentInfo.getName()) || StringUtils.isBlank(departmentInfo.getLeaderId()) ) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = departmentService.addDepartment(departmentInfo);
        return fromActionResult(result);
    }

    @ApiOperation(value = "编辑部门")
    @PutMapping("/modify")
    @ResponseBody
    public Response modifyDepartment(@RequestBody DepartmentInfo departmentInfo) {
        if (StringUtils.isBlank(departmentInfo.getId()) ||
                StringUtils.isBlank(departmentInfo.getCode()) ||
                StringUtils.isBlank(departmentInfo.getName()) ||
                StringUtils.isBlank(departmentInfo.getLeaderId()) ) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = departmentService.modifyDepartment(departmentInfo);
        return fromActionResult(result);
    }

    @ApiOperation(value = "分页查询部门")
    @PostMapping("/query")
    @ResponseBody
    public Response queryDepartmentByPage(@RequestBody DepartmentQuery query) {
        return fromActionResult(departmentService.queryDepartments(query));
    }

    @ApiOperation(value = "查询部门")
    @PostMapping("/queryList")
    @ResponseBody
    public Response queryDepartmentByList(@RequestBody DepartmentQuery query) {
        return fromActionResult(departmentService.queryList(query));
    }

    @ApiOperation(value = "为部门绑定角色")
    @PutMapping("/bindRole")
    public Response bindWithRole(@RequestBody DepartmentRoleRequest deptRole) {
        if (StringUtils.isBlank(deptRole.getDeptId())) {
            return failInvalidInput("非法输入");
        }

        ActionResult result = departmentService.updateDepartmentWithRole(deptRole.getDeptId(), deptRole.getRoleId());
        return fromActionResult(result);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/delete/{deptId}")
    @ResponseBody
    public Response deleteDepartments(@PathVariable String deptId) {
        return fromActionResult(departmentService.deleteDepartment(deptId));
    }

}

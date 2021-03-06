package com.myiot.myserver.mapper.system;

import com.myiot.myserver.data.po.system.Department;
import com.myiot.myserver.data.vo.system.DepartmentInfo;
import com.myiot.myserver.data.vo.system.DepartmentQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table myiot_department
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table myiot_department
     *
     * @mbg.generated
     */
    int insert(Department record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table myiot_department
     *
     * @mbg.generated
     */
    Department selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table myiot_department
     *
     * @mbg.generated
     */
    List<Department> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table myiot_department
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Department record);

    /**
     * 同级别的同名部门数量
     * @param parentId
     * @param name
     * @return
     */
    int countByDeptName(String parentId, String name);

    /**
     * 查询同级别指定名称的部门
     * @param parentId
     * @param name
     * @return
     */
    Department findByName(String parentId, String name);

    /**
     * 查询直接下级部门的个数
     * @param parentId
     * @return
     */
    int countChildDept(String parentId);

    /**
     * 分页查询符合条件的部门
     * @param departmentQuery
     * @return
     */
    List<DepartmentInfo> queryByPage(DepartmentQuery departmentQuery);

    /**
     * 更新部门是否有下级部门
     * @param deptId
     * @param leaf
     * @return
     */
    int updateDeptLeaf(@Param("deptId") String deptId, @Param("leaf") int leaf);

    /**
     * 删除部门
     * @param deptId
     */
    void deleteDepartment(String deptId);

}

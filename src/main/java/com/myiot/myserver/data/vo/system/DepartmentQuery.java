package com.myiot.myserver.data.vo.system;

import com.myiot.myserver.data.vo.BasePageQuery;
import lombok.Data;

/**
 *
 * 接受控制器数据对象带有分页功能的
 * @author origin
 */
@Data
public class DepartmentQuery extends BasePageQuery {

    private String name;

    private String parentId;

}

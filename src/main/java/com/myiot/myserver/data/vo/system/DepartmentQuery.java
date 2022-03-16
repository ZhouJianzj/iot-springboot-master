package com.myiot.myserver.data.vo.system;

import com.myiot.myserver.data.vo.BasePageQuery;
import lombok.Data;

/**
 * @author origin
 */
@Data
public class DepartmentQuery extends BasePageQuery {

    private String name;

    private String parentId;

}

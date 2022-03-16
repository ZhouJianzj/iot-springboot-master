package com.myiot.myserver.data.vo.system;

import com.myiot.myserver.data.po.system.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author origin
 */
@Data
public class RoleInfo {

    private String id;

    private String name;

    private String remark;

    private List<Menu> menuList;
}

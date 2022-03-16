package com.myiot.myserver.data.vo.system;

import lombok.Data;

/**
 *
 * info 结尾表示请求返回数据对象
 * @author origin
 */
@Data
public class DepartmentInfo {

    private String id;

    private String name;

    private String code;

    private String parentId;

    private String parentName;

    private String leaderId;

    private String leaderName;

    private String leaderMobile;

    private Boolean status;

    private String roleId;
}

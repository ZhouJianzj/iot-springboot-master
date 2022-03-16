package com.myiot.myserver.data.vo.system;

import lombok.Data;

/**
 * @author origin
 */
@Data
public class MenuInfo {

    private String id;

    private String name;

    private String code;

    private Integer seq;

    private String icon;

    private String url;

    private Integer leaf;

    private String parentId;

}

package com.myiot.myserver.data.vo.system;

import lombok.Data;

/**
 * @author origin
 */
@Data
public class MenuDetailInfo {

    private String id;

    private String name;

    private String code;

    private Integer seq;

    private String icon;

    private String url;

    private Integer leaf;

    private String parentId;

    private String parentName;

    private String parentCode;

    private String parentSeq;

    private String parentIcon;

    private String parentUrl;

}

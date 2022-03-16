package com.myiot.myserver.data.vo.system;

import com.myiot.myserver.data.vo.BasePageQuery;
import lombok.Data;

/**
 * @author origin
 */
@Data
public class UserQuery extends BasePageQuery {

    private String accountName;

    private String userName;

    private Integer sex;

    private String depId;

}

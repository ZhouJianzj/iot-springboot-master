package com.myiot.myserver.data.vo.system;

import lombok.Data;

/**
 * @author origin
 */
@Data
public class PasswordRequest {

    private String userId;

    private String pwdOld;

    private String pwdNew;

}

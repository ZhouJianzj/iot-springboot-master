package com.myiot.myserver.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author origin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePageQuery {

    protected int pageNum;

    protected int pageSize;

}

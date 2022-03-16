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
public class BaseQuery extends BasePageQuery {

    private String name;

}

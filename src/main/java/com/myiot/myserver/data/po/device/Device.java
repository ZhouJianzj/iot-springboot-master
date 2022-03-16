package com.myiot.myserver.data.po.device;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device implements Serializable {

    private Integer id;
    private String name;
}

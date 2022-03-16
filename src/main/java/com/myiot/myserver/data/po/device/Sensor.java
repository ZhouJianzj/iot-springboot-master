package com.myiot.myserver.data.po.device;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor implements Serializable {
    private Integer id;
    private String name;
}

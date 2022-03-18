package com.myiot.myserver.data.po.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSensor implements Serializable {
    private Integer id;
    private String  deviceName;
    private List<SensorT> sensors;

}

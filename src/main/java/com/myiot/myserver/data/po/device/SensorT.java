package com.myiot.myserver.data.po.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorT extends Sensor{
    private Integer sensorId;
}

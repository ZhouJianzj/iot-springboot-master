package com.myiot.myserver.service.device;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.po.device.Sensor;
import com.myiot.myserver.data.vo.BasePageQuery;
import com.myiot.myserver.data.vo.BaseQuery;

public interface SenserServer {
    ActionResult selectSensor(BasePageQuery basePageQuery);

    ActionResult selectByName(BaseQuery baseQuery);

    ActionResult deleteSensor(String sensorId);

    ActionResult modifySensor(Sensor sensor);

    ActionResult addSensor(Sensor sensor);
}

package com.myiot.myserver.web.device;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.po.device.Sensor;
import com.myiot.myserver.data.vo.BasePageQuery;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.service.device.SenserServer;
import com.myiot.myserver.web.basic.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhoujian
 */
@RestController
@RequestMapping("/sensor")
public class SensorController extends BasicController {

    @Autowired
    private SenserServer senserServer;

    @GetMapping("/selectSensor")
    public Response selectSensor(BasePageQuery basePageQuery) {
        ActionResult res = senserServer.selectSensor(basePageQuery);
        return fromActionResult(res);
    }

    @GetMapping("/selectByName")
    public Response selectByName(BaseQuery baseQuery) {
        ActionResult result = senserServer.selectByName(baseQuery);
        return fromActionResult(result);
    }

    @DeleteMapping("/deleteSensor")
    public Response deleteSensor(String sensorId) {
        ActionResult result =   senserServer.deleteSensor(sensorId);
        return fromActionResult(result);

    }

    @PutMapping("/modifySensor")
    public Response modifySensor( @RequestBody Sensor sensor) {
        ActionResult result =   senserServer.modifySensor(sensor);
        return fromActionResult(result);
    }


    @PostMapping("/addSensor")
    public Response addSensor(@RequestBody Sensor sensor) {
        ActionResult result =   senserServer.addSensor(sensor);
        return fromActionResult(result);
    }


}

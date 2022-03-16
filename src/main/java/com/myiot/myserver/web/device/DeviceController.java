package com.myiot.myserver.web.device;


import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.common.Response;
import com.myiot.myserver.data.vo.BasePageQuery;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.data.vo.device.AddDevice;
import com.myiot.myserver.data.vo.device.ModifyDevice;
import com.myiot.myserver.service.device.DeviceServer;
import com.myiot.myserver.web.basic.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
public class DeviceController extends BasicController {

    @Autowired
    private DeviceServer deviceServer;

    /**
     * 查询所有的设备以及他们的所拥有的传感器
     * @return
     */
    @GetMapping("/deviceAndSensor")
    public Response getDevices(BasePageQuery query){
        ActionResult devices = deviceServer.getDevices(query);
        return fromActionResult(devices);
    }

    /**
     * 设备的模糊查询
     * @param device
     * @return
     */
    @GetMapping("/selectByName")
    public Response selectByName(BaseQuery device){
        ActionResult devices = deviceServer.selectByName(device);
        return fromActionResult(devices);
    }

    /**
     * 删除单个设备，以及设备传感器中对应的关系
     * @return
     */
    @DeleteMapping("deleteDevice")
    public Response deleteDevice(String deviceId){
        ActionResult res = deviceServer.deleteDevice(deviceId);
        return fromActionResult(res);
    }


    /**
     * 添加设备
     * @return
     */
    @PostMapping("addDevice")
    public Response addDevice(@RequestBody AddDevice addDevice){
        ActionResult res = deviceServer.addDevice(addDevice);
        return fromActionResult(res);
    }

    /**
     * 修改设备
     * @return
     */
    @PutMapping("modifyDevice")
    public Response modifyDevice(@RequestBody ModifyDevice modifyDevice){
        ActionResult res = deviceServer.modifyDevice(modifyDevice);
        return fromActionResult(res);
    }



}

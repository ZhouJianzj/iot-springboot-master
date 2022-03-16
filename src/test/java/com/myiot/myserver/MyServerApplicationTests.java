package com.myiot.myserver;

import com.myiot.myserver.data.po.device.Device;
import com.myiot.myserver.data.po.device.Sensor;
import com.myiot.myserver.data.vo.BasePageQuery;
import com.myiot.myserver.data.vo.device.ModifyDevice;
import com.myiot.myserver.mapper.device.DeviceMapper;
import com.myiot.myserver.mapper.device.SensorMapper;
import com.myiot.myserver.service.device.impl.DeviceServerImpl;
import com.myiot.myserver.utils.UuidUtil;
import com.myiot.myserver.web.device.DeviceController;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.SocketTimeoutException;

@SpringBootTest
@Slf4j
class MyServerApplicationTests {

    @Autowired
    private DeviceServerImpl deviceServer;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private SensorMapper sensorMapper;

    @Autowired
    private DeviceController deviceController;

    @Test
    void testSelectDeviceByName(){
        BasePageQuery basePageQuery = new BasePageQuery(0,2);
        System.out.println(deviceController.getDevices(basePageQuery));
    }

    @Test
    void testGetDevice() {
        BasePageQuery basePageQuery = new BasePageQuery(0,2);
        System.out.println(deviceServer.getDevices(basePageQuery));
    }
    @Test
    void testSelectByName() {
        System.out.println(deviceMapper.selectByName("床"));
    }

    @Test
    void testDeleteDevice() {
        System.out.println(deviceServer.deleteDevice("5"));
    }


    @Test
    void testAddDevice() {
        Device device = new Device();
        device.setName("adsasdas");
        deviceMapper.addDevice(device);
        System.out.println(device.getId());
    }


    @Test
    void testAddDeviceAndSenor() {
        Device device = new Device();
        device.setName("adsasdas");
        deviceMapper.addDevice(device);
        System.out.println(device.getId());

        String[] sensorsId = {"1","2","3"};
        System.out.println(deviceMapper.addDeviceRelationSensor(device.getId(), sensorsId));

    }

    @Test
    void  testUpdateDevice() {

        String[] strings  = {"1"};
        ModifyDevice test = new ModifyDevice(new Device(15, "飞机"), strings);
        System.out.println(deviceServer.modifyDevice(test));
    }

    @Test
    void  testSelectSensor() {
        System.out.println(sensorMapper.selectSensor());
    }

    @Test
    void  testSelectSensorByNameLike() {
        System.out.println(sensorMapper.selectByName("传感器"));
    }

    @Test
    void  testDeleteSensor() {
        System.out.println(sensorMapper.deleteSensor("6"));
    }

    @Test
    void  testDeleteSensorRelationDevice() {
        System.out.println(sensorMapper.deleteSensorRelationDevice("6"));
    }

    @Test
    void  testModifySensor() {
        System.out.println(sensorMapper.modifySensor(new Sensor(7,"zhoujian")));
    }

    @Test
    void  testaddSensor() {
        Sensor zhoujian = new Sensor(10, "test");
        System.out.println(sensorMapper.addSensor(zhoujian));
    }

    @Test
    void testUUIDUtils(){

        log.info(UuidUtil.createDecimalId());
        log.info("=============================================");
        log.info(UuidUtil.getUniqueId());

    }


    public static void main(String[] args) {
        System.out.println(test());
    }

    static String test(){
        try {
            if (false){
                System.out.println("=====ture====");
                return "ture";
            }else {
                System.out.println("=====false====");
                return "false";
            }
        }finally {
            System.out.println("finally");
        }
//        return "false";
    }
}

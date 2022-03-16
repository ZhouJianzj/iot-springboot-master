package com.myiot.myserver.mapper.device;

import com.myiot.myserver.data.po.device.Device;
import com.myiot.myserver.data.po.device.DeviceSensor;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import javax.naming.Name;
import java.util.List;


@Mapper
public interface DeviceMapper {

    List<DeviceSensor> getDevices();

    Boolean deleteDevice(@Param("deviceId") String deviceId);

    @Delete("delete from myiot_device_sensor where deviceId = #{deviceId}")
    void deleteDeviceSensor(@Param("deviceId") String deviceId);

    Integer addDevice(@Param("device") Device device);

    Boolean addDeviceRelationSensor(@Param("id") Integer id, @Param("sensors") String[] sensors);


    List<DeviceSensor> selectByName(@Param("name") String name);
}

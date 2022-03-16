package com.myiot.myserver.mapper.device;

import com.myiot.myserver.data.po.device.Sensor;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SensorMapper {

    @Select("select * from myiot_sensor")
    List<Sensor> selectSensor();


    @Select("select * from myiot_sensor where name like concat('%',#{name},'%')")
    List<Sensor> selectByName(@Param("name") String name);

    @Delete("delete from myiot_sensor where id = #{sensorId}")
    Boolean deleteSensor(@Param("sensorId") String sensorId);

    @Delete("delete  from myiot_device_sensor where sensorId = #{sensorId}")
    Boolean deleteSensorRelationDevice(@Param("sensorId") String sensorId);


    @Update("update myiot_sensor set name = #{sensor.name} where id = #{sensor.id}")
    Boolean modifySensor(@Param("sensor") Sensor sensor);

    @Insert("insert into myiot_sensor(name) values (#{name})")
    Boolean addSensor(Sensor sensor);
}

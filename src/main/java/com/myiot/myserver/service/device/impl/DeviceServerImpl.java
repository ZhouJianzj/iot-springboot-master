package com.myiot.myserver.service.device.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.device.Device;
import com.myiot.myserver.data.po.device.DeviceSensor;
import com.myiot.myserver.data.vo.BasePageQuery;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.data.vo.device.AddDevice;
import com.myiot.myserver.data.vo.device.ModifyDevice;
import com.myiot.myserver.mapper.device.DeviceMapper;
import com.myiot.myserver.service.device.DeviceServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoujian
 */

@Service
public class DeviceServerImpl implements DeviceServer {

    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 查询所哟设备以及受拥有的传感器
     *
     * @return
     */
    @Override
    public ActionResult getDevices(BasePageQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());

        try {
            List<DeviceSensor> devices = deviceMapper.getDevices();
            return new ActionResult(Constant.RESULT_SUCC, "查询所有设备以及所拥有的传感器", new PageInfo(devices));
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "查询所有设备以及所拥有的传感器失败");
        }

    }


    /**
     * 删除设备以及对应传感器
     *
     * @return
     */
    @Transactional
    @Override
    public ActionResult deleteDevice(String deviceId) {
        try {
            deviceMapper.deleteDevice(deviceId);
            deviceMapper.deleteDeviceSensor(deviceId);
            return new ActionResult(Constant.RESULT_SUCC, "设备删除成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "设备删除失败");
        }
    }

    /**
     * 添加设备以及对应的传感器
     *
     * @param addDevice
     * @return
     */
    @Transactional
    @Override
    public ActionResult addDevice(AddDevice addDevice) {

        try {
            Device device = new Device();
            device.setName(addDevice.getName());
            deviceMapper.addDevice(device);

            deviceMapper.addDeviceRelationSensor(device.getId(), addDevice.getSensors());
            return new ActionResult(Constant.RESULT_SUCC, "设备添加成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "设备添加失败");
        }
    }

    /**
     * 修改设备
     *
     * @param modifyDevice
     * @return
     */
    @Transactional
    @Override
    public ActionResult modifyDevice(ModifyDevice modifyDevice) {
        try {
            deviceMapper.deleteDevice(modifyDevice.getDevice().getId().toString());
            deviceMapper.deleteDeviceSensor(modifyDevice.getDevice().getId().toString());
            AddDevice addDevice = new AddDevice();
            addDevice.setName(modifyDevice.getDevice().getName());
            addDevice.setSensors(modifyDevice.getSensorsId());
            addDevice(addDevice);
            return new ActionResult(Constant.RESULT_SUCC, "设备修改成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "设备修改失败");
        }

    }

    @Override
    public ActionResult selectByName(BaseQuery device) {
        PageHelper.startPage(device.getPageNum(),device.getPageSize());
        try {
            List<DeviceSensor> devices = deviceMapper.selectByName(device.getName());
            return new ActionResult(Constant.RESULT_SUCC, "设备查询成功",new PageInfo(devices));
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "设备查询失败");
        }
    }
}

package com.myiot.myserver.service.device.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.constant.Constant;
import com.myiot.myserver.data.po.device.Sensor;
import com.myiot.myserver.data.vo.BasePageQuery;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.mapper.device.SensorMapper;
import com.myiot.myserver.service.device.SenserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SenserServerImpl implements SenserServer {

    @Autowired
    private SensorMapper sensorMapper;


    /**
     * 查询所有的传感器
     *
     * @return
     */
    @Override
    public ActionResult selectSensor(BasePageQuery basePageQuery) {
        PageHelper.startPage(basePageQuery.getPageNum(), basePageQuery.getPageSize());

        try {
            List<Sensor> sensors = sensorMapper.selectSensor();
            return new ActionResult(Constant.RESULT_SUCC, "查询传感器成功", new PageInfo(sensors));
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "查询传感器失败");
        }
    }


    @Override
    public ActionResult selectByName(BaseQuery baseQuery) {
        PageHelper.startPage(baseQuery.getPageNum(), baseQuery.getPageSize());

        try {
            List<Sensor> sensors = sensorMapper.selectByName(baseQuery.getName());
            return new ActionResult(Constant.RESULT_SUCC, "查询传感器成功", new PageInfo<>(sensors));
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "查询传感器失败");
        }
    }

    /**
     * 删除单个传感器
     *
     * @param sensorId
     * @return
     */
    @Transactional
    @Override
    public ActionResult deleteSensor(String sensorId) {
        try {
            Boolean b = sensorMapper.deleteSensor(sensorId);
            Boolean a = sensorMapper.deleteSensorRelationDevice(sensorId);
            return new ActionResult(Constant.RESULT_SUCC, "删除传感器成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "删除传感器失败");
        }
    }

    @Override
    public ActionResult modifySensor(Sensor sensor) {

        try {
            Boolean b = sensorMapper.modifySensor(sensor);
            return new ActionResult(Constant.RESULT_SUCC, "修改传感器成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "修改传感器失败");
        }

    }

    /**
     * 添加传感器
     *
     * @param sensor
     * @return
     */
    @Override
    public ActionResult addSensor(Sensor sensor) {
        try {
            Boolean b = sensorMapper.addSensor(sensor);
            return new ActionResult(Constant.RESULT_SUCC, "添加传感器成功");
        } catch (Exception e) {
            return new ActionResult(Constant.RESULT_ERROR, "添加传感器失败");
        }
    }
}

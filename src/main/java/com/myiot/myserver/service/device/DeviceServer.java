package com.myiot.myserver.service.device;

import com.myiot.myserver.data.common.ActionResult;
import com.myiot.myserver.data.vo.BasePageQuery;
import com.myiot.myserver.data.vo.BaseQuery;
import com.myiot.myserver.data.vo.device.AddDevice;
import com.myiot.myserver.data.vo.device.ModifyDevice;

public interface DeviceServer {

    ActionResult getDevices(BasePageQuery query);

    ActionResult deleteDevice(String deviceId);

    ActionResult addDevice(AddDevice addDevice);

    ActionResult modifyDevice(ModifyDevice modifyDevice);

    ActionResult selectByName(BaseQuery device);
}

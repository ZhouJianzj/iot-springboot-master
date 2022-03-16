package com.myiot.myserver.data.vo.device;

import com.myiot.myserver.data.po.device.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoujian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDevice {
    private Device device;
    private String[] sensorsId;
}

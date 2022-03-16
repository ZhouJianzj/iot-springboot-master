package com.myiot.myserver.data.vo.device;

import com.alibaba.druid.sql.visitor.functions.Right;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDevice {
    private String name;
    private String[] sensors;
}

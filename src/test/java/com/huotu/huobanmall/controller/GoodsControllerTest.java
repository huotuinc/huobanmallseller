package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.test.WebTestBase;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import org.junit.Before;
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Administrator on 2015/9/1.
 */
public class GoodsControllerTest extends WebTestBase {

    private Device device;

    @Before
    public  void  prepareDevice() {

        device = Device.newDevice(DeviceType.Android);
    }
    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(
                device.getApi("index")
                        .build()).andDo(print());
    }
}
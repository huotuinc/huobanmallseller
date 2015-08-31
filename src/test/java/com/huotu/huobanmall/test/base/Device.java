package com.huotu.huobanmall.test.base;

import com.huotu.huobanmall.config.CommonEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * todo rewrite
 * Created by lgh on 2015/8/28.
 */
public class Device {

    private static final Log log = LogFactory.getLog(Device.class);

    private double lat, lng;
    private int cityCode;
    private DeviceType type;
    private String version;
    private String imei, token;
    private String cpaCode = "default";

    private long mockTime;

    /**
     * 提供一种便捷的方式 迅速校验HTTP结果是否符合粉猫项目的一般规范
     *
     * @param code 期望的错误码
     * @return
     */
    public static ResultMatcher fanmoreStatus(final CommonEnum.AppCode code) {
        return new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                status().isOk().match(result);
                jsonPath("$.systemResultCode").value(CommonEnum.AppCode.SUCCESS.getValue()).match(result);
                jsonPath("$.resultCode").value(code.getValue()).match(result);
            }
        };
    }

    private Device() {
    }

    public Device setMockTime(long time) {
        this.mockTime = time;
        return this;
    }

    public int getCityCode() {
        return cityCode;
    }

    public Device setCityCode(int cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public String getCpaCode() {
        return cpaCode;
    }

    public Device setCpaCode(String cpaCode) {
        this.cpaCode = cpaCode;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public Device setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Device setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Device setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Device setToken(String token) {
        this.token = token;
        return this;
    }

    public DeviceType getType() {
        return type;
    }

    public Device setType(DeviceType type) {
        this.type = type;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Device setVersion(String version) {
        this.version = version;
        return this;
    }

    public static Device newDevice(DeviceType type, String version, String imei, String token) {
        Device d = new Device();
        d.type = type;
        d.version = version;
        d.imei = imei;
        d.token = token;
        return d;
    }

    public static Device newDevice(DeviceType type, String version, String imei) {
        return newDevice(type, version, imei, null);
    }

    public static Device newDevice(DeviceType type, String version) {
        return newDevice(type, version, UUID.randomUUID().toString());
    }

    public static Device newDevice(DeviceType type) {
        return newDevice(type, "3.0.0");
    }

    public DeviceRequestBuilder getApi(String api) {
        DeviceRequestBuilder builder = new DeviceRequestBuilder(api, HttpMethod.GET);
        pushParameters(builder);
        return builder;
    }

    public DeviceRequestBuilder postApi(String api) {
        DeviceRequestBuilder builder = new DeviceRequestBuilder(api, HttpMethod.POST);
        pushParameters(builder);
        return builder;
    }

    private void pushParameters(DeviceRequestBuilder builder) {
        builder.param("appKey", "b73ca64567fb49ee963477263283a1bf");
        ///java.text.NumberFormat.

        builder.param("lat", Double.toString(lat));
        builder.param("lng", Double.toString(lng));

//        builder.param("lat","116");
//        builder.param("lng", "116");

        builder.param("cityCode", Integer.toString(cityCode));

        long time;
        if (mockTime > 0)
            time = mockTime;
        else
            time = System.currentTimeMillis();
        builder.param("timestamp", Long.toString(time));
//        builder.param("timestamp","123");

        switch (type) {
            case Android:
                builder.param("operation", "FM2015AD");
                break;
            default:
                builder.param("operation", "FM2015AP");
        }
        builder.param("version", version);
        if (token != null) {
            builder.param("token", token);
        }
        builder.param("imei", imei);
        builder.param("cpaCode", cpaCode);

    }
}

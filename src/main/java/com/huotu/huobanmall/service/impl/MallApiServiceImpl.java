/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.huotu.common.HttpHelper;
import com.huotu.huobanmall.model.MallApiResultModel;
import com.huotu.huobanmall.service.CommonConfigService;
import com.huotu.huobanmall.service.MallApiService;
import com.huotu.huobanplus.sdk.mall.service.MallInfoService;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 商城服务接口
 * Created by lgh on 2015/9/23.
 */
@Service
public class MallApiServiceImpl implements MallApiService {

    @Autowired
    private CommonConfigService commonConfigService;
    @Autowired
    private MallInfoService mallInfoService;
    private String appsecret;
    private String appid;

    @PostConstruct
    public void init(){
        appsecret =commonConfigService.getAppsecret();
        appid = commonConfigService.getAppId();
    }


    @Override
    public String getMsiteUrl(Integer customerId) throws IOException {
        return mallInfoService.customerURL(customerId);
    }

    @Override
    public String upladPic(Integer customerId, String images, Integer type) throws IOException {
        String url = commonConfigService.getMallApiServerUrl() + "/gallery/upladpic";

        Map<String, String> map = new TreeMap<>();
        map.put("timestamp", String.valueOf(new Date().getTime()));
        map.put("appid", appid);
        map.put("storeid", String.valueOf(customerId));
        map.put("image", images);
        map.put("type", String.valueOf(type));
        map.put("sign", getSign(map));

//        String strUrl = getSignUrl(map);
        String response = HttpHelper.postRequest(url, map);
        MallApiResultModel resultModel = JSON.parseObject(response, MallApiResultModel.class);
        if (resultModel.getCode() == 200 && !StringUtils.isEmpty(resultModel.getData().toString())) {
            return JsonPath.read(resultModel.getData().toString(), "$.imgurl").toString();
        }
        return null;
    }

    private String getSign(Map<String, String> map) {
        String result = "";
        for (String key : map.keySet()) {
            result += key + "=" + map.get(key).toString() + "&";
        }
        String before=result.substring(0, result.length() - 1) + appsecret;
        return DigestUtils.md5DigestAsHex((result.substring(0, result.length() - 1) + appsecret).getBytes());
    }

    private String getSignUrl(Map<String, String> map) {
        String result = "";
        for (String key : map.keySet()) {
            result += key + "=" + map.get(key).toString() + "&";
        }
        return "?" + result.substring(0, result.length() - 1);
    }

}

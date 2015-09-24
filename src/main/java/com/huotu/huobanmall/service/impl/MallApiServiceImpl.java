package com.huotu.huobanmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.huotu.common.HttpHelper;
import com.huotu.huobanmall.model.MallApiResultModel;
import com.huotu.huobanmall.service.CommonConfigService;
import com.huotu.huobanmall.service.MallApiService;


import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

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

    private String appsecret = "9389e8a5c32eefa3134340640fb4ceaa";
    private String appid = "73d29a4c9a6d389a0b7288ec27b4c4c4";

    @Autowired
    private CommonConfigService commonConfigService;

    @Override
    public String getMsiteUrl(Integer customerId) throws IOException {
        String url = commonConfigService.getMallApiServerUrl() + "activity/getmsiteurl";

        Map<String, String> map = new TreeMap<>();
        map.put("timestamp", String.valueOf(new Date().getTime()));
        map.put("appid", appid);
        map.put("customerid", String.valueOf(customerId));
        map.put("sign", getSign(map));

        String strUrl = getSignUrl(map);
        String response = HttpHelper.getRequest(url + strUrl);
        MallApiResultModel resultModel = JSON.parseObject(response, MallApiResultModel.class);
        if (resultModel.getCode() == 200 && !StringUtils.isEmpty(resultModel.getData().toString())) {
            return JsonPath.read(resultModel.getData().toString(), "$.msiteUrl").toString();
        }
        return null;
    }

    @Override
    public String upladPic(Integer customerId, String images, Integer type) throws IOException {
        String url = commonConfigService.getMallApiServerUrl() + "gallery/upladpic";

        Map<String, String> map = new TreeMap<>();
        map.put("timestamp", String.valueOf(new Date().getTime()));
        map.put("appid", appid);
        map.put("storeId", String.valueOf(customerId));
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

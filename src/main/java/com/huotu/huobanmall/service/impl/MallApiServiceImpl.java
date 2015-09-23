package com.huotu.huobanmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.huotu.common.HttpHelper;
import com.huotu.huobanmall.model.MallApiResultModel;
import com.huotu.huobanmall.service.CommonConfigService;
import com.huotu.huobanmall.service.MallApiService;
import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.json.Json;
import java.io.IOException;

/**
 * 商城服务接口
 * Created by lgh on 2015/9/23.
 */
@Service
public class MallApiServiceImpl implements MallApiService {

    @Autowired
    private CommonConfigService commonConfigService;

    @Override
    public String getMsiteUrl(Integer customerId) throws IOException {
        String url = commonConfigService.getMallApiServerUrl() + "activity/getmsiteurl";


        String response = HttpHelper.getRequest(url + "?customerid=" + customerId);

        MallApiResultModel resultModel = JSON.parseObject(response, MallApiResultModel.class);
        if (resultModel.getCode() == 200 && !StringUtils.isEmpty(resultModel.getData().toString())) {
            return JsonPath.read(resultModel.getData().toString(), "$.msiteUrl").toString();
        }
        return null;
    }

    @Override
    public String upladPic(Integer customerId, byte[] images, Integer type) {
        return null;
    }


}

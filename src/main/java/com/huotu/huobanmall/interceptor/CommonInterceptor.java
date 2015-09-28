package com.huotu.huobanmall.interceptor;

import com.alibaba.fastjson.JSON;

import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.Shop;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OperatorRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import com.huotu.huobanmall.service.DeviceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * app通用监听 sign验证
 */
public class CommonInterceptor implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(CommonInterceptor.class);

    private String appSecret = "1165a8d240b29af3f418b8d10599d0da";
    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private DeviceService deviceService;

//    @Autowired
//    private DeviceService deviceService;
//    @Autowired
//    private ConfigAreaRepository configAreaRepository;
//    @Autowired
//    private ConfigMobileRepository configMobileRepository;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        logger.debug("==============执行顺序: 0、preHandle================");

        String sign = request.getParameter("sign");

        //logger.info("sign:" + getSign(request));
        if (sign == null || !sign.equals(getSign(request))) {
            PhysicalApiResult result = new PhysicalApiResult();
            result.setSystemResultCode(1);
            result.setResultCode(CommonEnum.AppCode.SYSTEM_BAD_REQUEST_50601.getValue());
            result.setResultDescription("系统请求失败");
            PrintWriter out = null;
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                out = response.getWriter();
                out.append(JSON.toJSONString(result));
            } finally {
                if (out != null) {
                    out.close();
                }
            }

            return false;
        }

        AppPublicModel appPublicModel = initPublicParam(request);
        PublicParameterHolder.putParameters(appPublicModel);

        //其他设备登录，用户token失效
        if (!StringUtils.isEmpty(appPublicModel.getToken()) && appPublicModel.getCurrentUser() == null) {
            PhysicalApiResult result = new PhysicalApiResult();
            result.setSystemResultCode(1);
            result.setResultCode(CommonEnum.AppCode.ERROR_USER_TOKEN_FAIL.getValue());
            result.setResultDescription(CommonEnum.AppCode.ERROR_USER_TOKEN_FAIL.getName());
            PrintWriter out = null;
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                out = response.getWriter();
                out.append(JSON.toJSONString(result));
            } finally {
                if (out != null) {
                    out.close();
                }
            }

            return false;
        }

//        logger.debug("==============执行顺序: 1、preHandle================");
        // String url = request.getRequestURL().toString();
        // logger.info(url);

        return true;
    }

    private AppPublicModel initPublicParam(HttpServletRequest request) {

        AppPublicModel model = new AppPublicModel();
        String sign = StringUtils.isEmpty(request.getParameter("sign")) ? "" : request.getParameter("sign");
        String appKey = StringUtils.isEmpty(request.getParameter("appKey")) ? "" : request.getParameter("appKey");
//        Float lat = StringUtils.isEmpty(request.getParameter("lat")) ? new Float(0) : new Float(request.getParameter("lat"));
//        Float lng = StringUtils.isEmpty(request.getParameter("lng")) ? new Float(0) : new Float(request.getParameter("lng"));

        String cityCode = StringUtils.isEmpty(request.getParameter("cityCode")) ? "" : request.getParameter("cityCode");
        String imei = StringUtils.isEmpty(request.getParameter("imei")) ? "" : request.getParameter("imei");
        String operation = StringUtils.isEmpty(request.getParameter("operation")) ? "" : request.getParameter("operation");

        long timestamp = StringUtils.isEmpty(request.getParameter("timestamp")) ? 0 : Long.parseLong(request.getParameter("timestamp"));
        String token = StringUtils.isEmpty(request.getParameter("token")) ? "" : request.getParameter("token");

        if (!StringUtils.isEmpty(token)) {
            Merchant merchant = merchantRepository.findByToken(token);
            if (merchant != null) {
                model.setCurrentUser(merchant);
                model.setCurrentShop(shopRepository.findByMerchant(merchant));
            } else {
                Operator operator = operatorRepository.findByToken(token);
                if (operator != null) {
                    model.setCurrentOprator(operator);
                    model.setCurrentUser(operator.getMerchant());
                    model.setCurrentShop(shopRepository.findByMerchant(operator.getMerchant()));
                }
            }
        }

        String version = StringUtils.isEmpty(request.getParameter("version")) ? "" : request.getParameter("version");
        // String loginCode =
        // StringUtils.isEmpty(request.getParameter("loginCode")) ? "" :
        // request.getParameter("loginCode");
        String cpacode = StringUtils.isEmpty(request.getParameter("cpaCode")) ? "" : request.getParameter("cpaCode");
        String ip = getIp(request);


        model.setSign(sign);
        model.setAppKey(appKey);
//        model.setLat(lat);
//        model.setLng(lng);

        model.setCityCode(cityCode);
        model.setImei(imei);

        model.setOperation(operation);

        model.setTimestamp(timestamp);
        model.setToken(token);
        model.setVersion(version);
        // public_param.setLoginCode(loginCode);
        model.setCpaCode(cpacode);
        model.setIp(ip);

        //设置当前用户设备
        model.setCurrentDevice(deviceService.fetchDevice(model.getImei(), model.getCpaCode(), model.getOperation()));
        //如果当前用户存在 并且设备不同于之前的设备那么发布设备变化事件
        if (model.getCurrentUser() != null) {
            if (model.getCurrentOprator() != null) {
                if (model.getCurrentDevice() != model.getCurrentOprator().getDevice()) {
                    deviceService.userChanged(model.getCurrentDevice(), null, model.getCurrentOprator(), model.getVersion(), model.getIp());
                    model.getCurrentOprator().setDevice(model.getCurrentDevice());
                }
            } else {
                if (model.getCurrentDevice() != model.getCurrentUser().getDevice()) {
                    deviceService.userChanged(model.getCurrentDevice(), model.getCurrentUser(), null, model.getVersion(), model.getIp());
                    model.getCurrentUser().setDevice(model.getCurrentDevice());
                }
            }
        }

        return model;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.debug("==============执行顺序: 2、postHandle================");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.debug("==============执行顺序: 3、afterCompletion================");

    }

    private String getSign(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> resultMap = new TreeMap<String, String>();
        resultMap.put("appSecret", appSecret);
        Map map = request.getParameterMap();
        for (Object key : map.keySet()) {
            resultMap.put(key.toString(), request.getParameter(key.toString()));
        }

        StringBuilder strB = new StringBuilder();

        resultMap.keySet().stream().filter(key -> !"sign".equals(key)).forEach(key -> strB.append(resultMap.get(key)));

        return DigestUtils.md5DigestAsHex(strB.toString().getBytes("UTF-8")).toLowerCase();
    }


    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


}

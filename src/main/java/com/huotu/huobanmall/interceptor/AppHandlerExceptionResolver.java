package com.huotu.huobanmall.interceptor;

import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.exception.ShopCloseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/10/15.
 */
public class AppHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (requestURI.startsWith("/app/")) {
            ModelAndView mv = new ModelAndView();
            PhysicalApiResult result = new PhysicalApiResult();
            result.setSystemResultDescription(ex.getLocalizedMessage());
            try {
                throw ex;
            } catch (ShopCloseException e) {
                result.setSystemResultCode(CommonEnum.AppCode.ERROR_USER_SHOPCLOSE.getValue());
                result.setSystemResultDescription("商城已经关闭");
            }
            catch (Exception e) {
                result.setSystemResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

            mv.addObject("__realResult", result);
            MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

            jsonView.setContentType("application/json;charset=UTF-8");
            jsonView.setModelKey("__realResult");
            jsonView.setExtractValueFromSingleKeyModel(true);
            mv.setView(jsonView);
            return mv;
        }
        return null;
    }
}

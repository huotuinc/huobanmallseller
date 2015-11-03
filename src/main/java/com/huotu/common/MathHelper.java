/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.common;

import java.math.BigDecimal;

/**
 * 数学计算通用方法
 * Created by SLT on 2015/11/2.
 */
public class MathHelper {

    /**
     * 保留小数位数
     * @param n     小数位数
     * @param data  需要保留的数据
     * @return
     */
    public static double retainDecimal(double data,int n){
        BigDecimal bg = new BigDecimal(data);
        return bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
    }




}

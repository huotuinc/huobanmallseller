/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;

import com.huotu.common.model.CodeType;
import com.huotu.common.model.VerificationType;
import com.huotu.huobanmall.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/8/27.
 */
@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {

    /**
     * 根据手机和类型返回验证码
     * @param mobile
     * @param type
     * @param codeType
     * @return
     */
    VerificationCode findByMobileAndTypeAndCodeType(String mobile,VerificationType type,CodeType codeType);

    /**
     * 根据手机和类型返回验证码
     * @param mobile
     * @param type
     * @param last 最晚许可的发送时间
     * @return
     */
    List<VerificationCode> findByMobileAndTypeAndSendTimeGreaterThan(String mobile,VerificationType type,Date last);
}

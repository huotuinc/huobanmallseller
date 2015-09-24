package com.huotu.huobanmall.service.impl;

import com.huotu.common.FileHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.service.MallApiService;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Base64;

/**
 * Created by lgh on 2015/9/24.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class MallApiServiceImplTest extends TestCase {

    private Log log = LogFactory.getLog(MallApiServiceImpl.class);

    @Autowired
    private MallApiService mallApiService;

    @Test
    public void testGetMsiteUrl() throws Exception {

        String url = mallApiService.getMsiteUrl(5);
        log.info("地址：" + url);
    }


    @Test
    public void testUpladPic() throws Exception {
        File file = new File("d:\\1.jpg");
        byte[] bytes = FileHelper.getBytes(file);

        byte[] result = org.apache.commons.codec.binary.Base64.encodeBase64(bytes);

//       Base64.getMimeDecoder(bytes.toString());

        String text = mallApiService.upladPic(5, result.toString(), 1);
        log.info(text);
    }
}
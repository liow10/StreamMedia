package com.micro.iotclouds.modules.rtmp.server.server.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: PanNaiZhao
 * @DATE: 2018-05-02 上午 9:41
 * @projectName: gateway
 */
public class HttpsKeyStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpsKeyStore.class);

    /**
     * 读取密钥
     * @date 2012-9-11
     * @version V1.0.0
     * @return InputStream
     */
    public static InputStream getKeyStoreStream() {
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(VarConstant.keystorePath);
        } catch (FileNotFoundException e) {
            LOGGER.error("读取密钥文件失败", e);
        }
        return inStream;
    }

    /**
     * 获取安全证书密码 (用于创建KeyManagerFactory)
     * @date 2012-9-11
     * @version V1.0.0
     * @return char[]
     */
    public static char[] getCertificatePassword() {
        return VarConstant.certificatePassword.toCharArray();
    }

    /**
     * 获取密钥密码(证书别名密码) (用于创建KeyStore)
     * @date 2012-9-11
     * @version V1.0.0
     * @return char[]
     */
    public static char[] getKeyStorePassword() {
        return VarConstant.keystorePassword.toCharArray();
    }
}
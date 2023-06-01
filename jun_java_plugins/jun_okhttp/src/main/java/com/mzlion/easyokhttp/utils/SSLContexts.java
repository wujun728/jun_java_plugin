/*
 * Copyright (C) 2016-2017 mzlion(mzllon@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mzlion.easyokhttp.utils;

import com.mzlion.core.io.IOUtils;
import com.mzlion.core.lang.ArrayUtils;

import javax.net.ssl.*;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * SSL证书
 *
 * @author mzlion on 2016/12/7.
 */
public class SSLContexts {

    /**
     * 尝试解析SSL证书
     *
     * @param certificates     远程服务端的证书
     * @param x509TrustManager 自定义证书管理器
     * @param pfxCertificate   客户端证书
     * @param pfxPassword      客户端证书密码
     * @return {@link SSLConfig}
     */
    public static SSLConfig tryParse(InputStream[] certificates, X509TrustManager x509TrustManager,
                                     InputStream pfxCertificate, char[] pfxPassword) {
        try {
            SSLConfig sslConfig = new SSLConfig();

            KeyManager[] keyManagers = prepareKeyManagers(pfxCertificate, pfxPassword);
            X509TrustManager trustManager;
            if (x509TrustManager != null) {
                //优先使用用户自定义的TrustManager
                trustManager = x509TrustManager;
            } else {
                //然后使用默认的TrustManager
                trustManager = prepareX509TrustManager(certificates);
                if (trustManager == null) {
                    trustManager = unSafeTrustManager;
                }
            }

            //创建TLS类型的SSLContext对象.
            SSLContext sslContext = SSLContext.getInstance("TLS");
            // 用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            sslContext.init(keyManagers, new TrustManager[]{trustManager}, new SecureRandom());

            sslConfig.setSslSocketFactory(sslContext.getSocketFactory());
            sslConfig.setX509TrustManager(trustManager);
            return sslConfig;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    /**
     * 不对证书进行验证
     */
    private static X509TrustManager unSafeTrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    /**
     * 根据证书文件生成{@linkplain X509TrustManager}
     *
     * @param certificates 证书文件
     * @return {@link X509TrustManager}
     */
    private static X509TrustManager prepareX509TrustManager(InputStream... certificates) throws Exception {
        if (ArrayUtils.isEmpty(certificates)) {
            return null;
        }

        //创建证书工厂
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        //创建一个默认类型的KeyStore,存储我们信任的证书
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);

        //开始处理证书
        for (int i = 0, length = certificates.length; i < length; i++) {
            //将证书对象作为可信证书放入到keyStore中
            keyStore.setCertificateEntry(String.valueOf(i + 1), factory.generateCertificate(certificates[i]));
            IOUtils.closeQuietly(certificates[i]);
        }

        // Use it to build an X509 trust manager.
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        //用我们之前的keyStore实例初始化TrustManagerFactory,这样tmf就会信任keyStore中的证书
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + ArrayUtils.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyManager[] prepareKeyManagers(InputStream pfxCertificate, char[] pfxPassword) throws Exception {
        if (pfxCertificate == null || ArrayUtils.isEmpty(pfxPassword)) {
            return null;
        }
        KeyStore clientKS = KeyStore.getInstance("PKCS12");
        clientKS.load(pfxCertificate, pfxPassword);
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(clientKS, pfxPassword);
        return factory.getKeyManagers();
    }

    public static final class SSLConfig {

        private SSLSocketFactory sslSocketFactory;
        private X509TrustManager x509TrustManager;

        public SSLSocketFactory getSslSocketFactory() {
            return sslSocketFactory;
        }

        void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
        }

        public X509TrustManager getX509TrustManager() {
            return x509TrustManager;
        }

        void setX509TrustManager(X509TrustManager x509TrustManager) {
            this.x509TrustManager = x509TrustManager;
        }
    }
}

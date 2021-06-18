package org.frameworkset.spi.remote.http.ssl;
/**
 * Copyright 2008 biaoping.yin
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.frameworkset.spi.remote.http.HttpRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2020/1/15 9:56
 * @author Wujun
 * @version 1.0
 */
public class SSLHelper {
	private static final Logger log = LoggerFactory.getLogger(SSLHelper.class);
	private static final String DEFAULT_STORE_TYPE = "JKS";
	static final String PROTOCOL = "TLS";

	static TrustManagerFactory buildTrustManagerFactory(
			X509Certificate[] certCollection, TrustManagerFactory trustManagerFactory)
			throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(null, null);

		int i = 1;
		for (X509Certificate cert: certCollection) {
			String alias = Integer.toString(i);
			ks.setCertificateEntry(alias, cert);
			i++;
		}

		// Set up trust manager factory to use our key store.
		if (trustManagerFactory == null) {
			trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		}
		trustManagerFactory.init(ks);

		return trustManagerFactory;
	}
	static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChain, PrivateKey key, String keyPassword,
													KeyManagerFactory kmf)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
		if (algorithm == null) {
			algorithm = "SunX509";
		}
		return buildKeyManagerFactory(certChain, algorithm, key, keyPassword, kmf);
	}
	/**
	 * Generates a new {@link KeyStore}.
	 *
	 * @param certChain a X.509 certificate chain
	 * @param key a PKCS#8 private key
	 * @param keyPasswordChars the password of the {@code keyFile}.
	 *                    {@code null} if it's not password-protected.
	 * @return generated {@link KeyStore}.
	 */
	static KeyStore buildKeyStore(X509Certificate[] certChain, PrivateKey key, char[] keyPasswordChars)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(null, null);
		ks.setKeyEntry("key", key, keyPasswordChars, certChain);
		return ks;
	}
	static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChainFile,
													String keyAlgorithm, PrivateKey key,
													String keyPassword, KeyManagerFactory kmf)
			throws KeyStoreException, NoSuchAlgorithmException, IOException,
			CertificateException, UnrecoverableKeyException {
		char[] keyPasswordChars = keyPassword == null ? EmptyArrays.EMPTY_CHARS : keyPassword.toCharArray();
		KeyStore ks = buildKeyStore(certChainFile, key, keyPasswordChars);
		// Set up key manager factory to use our key store
		if (kmf == null) {
			kmf = KeyManagerFactory.getInstance(keyAlgorithm);
		}
		kmf.init(ks, keyPasswordChars);

		return kmf;
	}

	public static SSLContext newSSLContext(String PROTOCOL,Provider sslContextProvider, X509Certificate[] trustCertCollection,
											TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain,
											PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory,
											long sessionCacheSize, long sessionTimeout)
			throws SSLException {
		if (key == null && keyManagerFactory == null) {
			throw new NullPointerException("key, keyManagerFactory");
		}

		try {
			if (trustCertCollection != null) {
				trustManagerFactory = buildTrustManagerFactory(trustCertCollection, trustManagerFactory);
			}
			if (key != null) {
				keyManagerFactory = buildKeyManagerFactory(keyCertChain, key, keyPassword, keyManagerFactory);
			}

			// Initialize the SSLContext to work with our key managers.
			SSLContext ctx = sslContextProvider == null ? SSLContext.getInstance(PROTOCOL)
					: SSLContext.getInstance(PROTOCOL, sslContextProvider);
			ctx.init(keyManagerFactory.getKeyManagers(),
					trustManagerFactory == null ? null : trustManagerFactory.getTrustManagers(),
					null);

			SSLSessionContext sessCtx = ctx.getServerSessionContext();
			if (sessionCacheSize > 0) {
				sessCtx.setSessionCacheSize((int) Math.min(sessionCacheSize, Integer.MAX_VALUE));
			}
			if (sessionTimeout > 0) {
				sessCtx.setSessionTimeout((int) Math.min(sessionTimeout, Integer.MAX_VALUE));
			}
			return ctx;
		} catch (Exception e) {
			if (e instanceof SSLException) {
				throw (SSLException) e;
			}
			throw new SSLException("failed to initialize the server-side SSL context", e);
		}
	}

	public static SSLContext initSSLConfig(String keystore,String keyPassword) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
		SSLContextBuilder builder = SSLContexts.custom()
				.loadTrustMaterial(new File(keystore), keyPassword.toCharArray(),
						new TrustSelfSignedStrategy());
		return builder.build();
	}

	public static SSLContext initSSLConfig(String PROTOCOL,String keystoreFilePath,String keystoreType,
							   String keystorePassword ,String keystoreAlias,
							   String truststoreFilePath,String truststoreType,String truststorePassword,String truststoreAlias) {





//			final String rawKeystoreFilePath = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, null);
//			final String rawPemCertFilePath = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_PEMCERT_FILEPATH, null);
//			final ClientAuth httpClientAuthMode = ClientAuth.valueOf(settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_CLIENTAUTH_MODE, ClientAuth.OPTIONAL.toString()));

			if(keystoreFilePath != null) {

//				final String keystoreFilePath = resolve(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, true);
//				final String keystoreType = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_TYPE, DEFAULT_STORE_TYPE);
//				final String keystorePassword = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_PASSWORD, SSLConfigConstants.DEFAULT_STORE_PASSWORD);
//				final String keystoreAlias = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_ALIAS, null);

//				log.info("HTTPS client auth mode {}", httpClientAuthMode);

//				if(settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, null) == null) {
//					throw new ElasticsearchException(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH
//							+ " must be set if https is reqested.");
//				}

//				if (httpClientAuthMode == ClientAuth.REQUIRE) {
//
//					if(settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_FILEPATH, null) == null) {
//						throw new ElasticsearchException(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_FILEPATH
//								+ " must be set if http ssl and client auth is reqested.");
//					}
//
//				}

				try {

					final KeyStore ks = KeyStore.getInstance(keystoreType);
					try {
						FileInputStream fin = new FileInputStream(new File(keystoreFilePath));
						ks.load(fin, (keystorePassword == null || keystorePassword.length() == 0) ? null:keystorePassword.toCharArray());
					}
					catch (Exception e){
						throw new HttpRuntimeException(e) ;
					}

					final X509Certificate[] httpKeystoreCert = SSLCertificateHelper.exportServerCertChain(ks, keystoreAlias);
					final PrivateKey httpKeystoreKey = SSLCertificateHelper.exportDecryptedKey(ks, keystoreAlias, (keystorePassword==null || keystorePassword.length() == 0) ? null:keystorePassword.toCharArray());


					if(httpKeystoreKey == null) {
						throw new HttpRuntimeException("No key found in "+keystoreFilePath+" with alias "+keystoreAlias);
					}


					if(httpKeystoreCert != null && httpKeystoreCert.length > 0) {

						//TODO create sensitive log property
                        /*for (int i = 0; i < httpKeystoreCert.length; i++) {
                            X509Certificate x509Certificate = httpKeystoreCert[i];

                            if(x509Certificate != null) {
                                log.info("HTTP keystore subject DN no. {} {}",i,x509Certificate.getSubjectX500Principal());
                            }
                        }*/
					} else {
						throw new HttpRuntimeException("No certificates found in "+keystoreFilePath+" with alias "+keystoreAlias);
					}

					X509Certificate[] trustedHTTPCertificates = null;

					if(truststoreFilePath != null) {

//						final String truststoreFilePath = resolve(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_FILEPATH, true);

//						final String truststoreType = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_TYPE, DEFAULT_STORE_TYPE);
//						final String truststorePassword = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_PASSWORD, SSLConfigConstants.DEFAULT_STORE_PASSWORD);
//						final String truststoreAlias = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_ALIAS, null);

						final KeyStore ts = KeyStore.getInstance(truststoreType);
						try{
							FileInputStream fin = new FileInputStream(new File(truststoreFilePath));
							ts.load(fin, (truststorePassword == null || truststorePassword.length() == 0) ?null:truststorePassword.toCharArray());
						}
						catch (Exception e){
							throw new HttpRuntimeException(e) ;
						}
						trustedHTTPCertificates = SSLCertificateHelper.exportRootCertificates(ts, truststoreAlias);
					}

//					httpSslContext = buildSSLServerContext(httpKeystoreKey, httpKeystoreCert, trustedHTTPCertificates, getEnabledSSLCiphers(this.sslHTTPProvider, true), sslHTTPProvider, httpClientAuthMode);
					SSLContext sslContext = newSSLContext(PROTOCOL,null,trustedHTTPCertificates,
							(TrustManagerFactory)null, httpKeystoreCert,
							httpKeystoreKey, keystorePassword, (KeyManagerFactory)null,
							0l, 0l);
					return sslContext;
				} catch (final Exception e) {
					throw new HttpRuntimeException("Error while initializing HTTP SSL layer: "+e.toString(), e);
				}

			}
			else {
				throw new HttpRuntimeException("Error while initializing HTTP SSL layer: keystoreFilePath is null.");
			}



	}




	/**
	 * SSLHelper.initSSLConfig("TLS", pemKey, "JKS", this.pemkeyPassword, pemCert, pemtrustedCA),
	 * @param PROTOCOL
	 * @param pemKey
	 * @param pemKeyPassword
	 * @param pemCert
	 * @param pemtrustCA
	 * @return
	 */
	public static SSLContext initSSLConfig(String PROTOCOL,String pemKey,
										   String pemKeyPassword ,String pemCert,String pemtrustCA) {

//			final String rawKeystoreFilePath = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, null);
//			final String rawPemCertFilePath = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_PEMCERT_FILEPATH, null);
//			final ClientAuth httpClientAuthMode = ClientAuth.valueOf(settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_CLIENTAUTH_MODE, ClientAuth.OPTIONAL.toString()));

		if(pemKey != null) {

//				final String keystoreFilePath = resolve(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, true);
//				final String keystoreType = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_TYPE, DEFAULT_STORE_TYPE);
//				final String keystorePassword = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_PASSWORD, SSLConfigConstants.DEFAULT_STORE_PASSWORD);
//				final String keystoreAlias = settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_ALIAS, null);

//				log.info("HTTPS client auth mode {}", httpClientAuthMode);

//				if(settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, null) == null) {
//					throw new ElasticsearchException(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH
//							+ " must be set if https is reqested.");
//				}

//				if (httpClientAuthMode == ClientAuth.REQUIRE) {
//
//					if(settings.get(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_FILEPATH, null) == null) {
//						throw new ElasticsearchException(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_FILEPATH
//								+ " must be set if http ssl and client auth is reqested.");
//					}
//
//				}

			try {


				final X509Certificate[] httpKeystoreCert = SSLCertificateHelper.toX509Certificates(new File(pemCert));//SSLCertificateHelper.exportServerCertChain(ks, keystoreAlias);
				final PrivateKey httpKeystoreKey = SSLCertificateHelper.toPrivateKey(new File(pemKey), pemKeyPassword);//SSLCertificateHelper.exportDecryptedKey(ks, keystoreAlias, (keystorePassword==null || keystorePassword.length() == 0) ? null:keystorePassword.toCharArray());


				if(httpKeystoreKey == null) {
					throw new HttpRuntimeException("No key found in pemKey "+pemKey+" with pemKeyPassword "+pemKeyPassword);
				}


				if(httpKeystoreCert != null && httpKeystoreCert.length > 0) {

					//TODO create sensitive log property
                        /*for (int i = 0; i < httpKeystoreCert.length; i++) {
                            X509Certificate x509Certificate = httpKeystoreCert[i];

                            if(x509Certificate != null) {
                                log.info("HTTP keystore subject DN no. {} {}",i,x509Certificate.getSubjectX500Principal());
                            }
                        }*/
				} else {
					throw new HttpRuntimeException("No certificates found in pemCert "+pemCert);
				}

				X509Certificate[] trustedHTTPCertificates = null;

				if(pemtrustCA != null) {
					trustedHTTPCertificates = SSLCertificateHelper.toX509Certificates(new File(pemtrustCA));
				}

//					httpSslContext = buildSSLServerContext(httpKeystoreKey, httpKeystoreCert, trustedHTTPCertificates, getEnabledSSLCiphers(this.sslHTTPProvider, true), sslHTTPProvider, httpClientAuthMode);
				SSLContext sslContext = newSSLContext(PROTOCOL,null,trustedHTTPCertificates,
						(TrustManagerFactory)null, httpKeystoreCert,
						httpKeystoreKey, pemKeyPassword, (KeyManagerFactory)null,
						0l, 0l);
				return sslContext;
			} catch (final Exception e) {
				throw new HttpRuntimeException("Error while initializing HTTP SSL layer: "+e.toString(), e);
			}

		}
		else {
			throw new HttpRuntimeException("Error while initializing HTTP SSL layer: keystoreFilePath is null.");
		}

	}

}

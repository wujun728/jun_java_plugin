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

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2020/1/15 9:57
 * @author biaoping.yin
 * @version 1.0
 */

import com.frameworkset.util.SimpleStringUtil;
import org.frameworkset.util.encoder.Base64Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SSLCertificateHelper {

	private static final Logger logger = LoggerFactory.getLogger(SSLCertificateHelper.class);
	private static boolean stripRootFromChain = true; //TODO check

	static X509Certificate[] toX509Certificates(File file) throws CertificateException {
		if (file == null) {
			return null;
		}
		return getCertificatesFromBuffers(readCertificates(file));
	}
	public static X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");

		return (X509Certificate)factory.generateCertificate(new ByteArrayInputStream(certBytes));
	}

	static List<byte[]> readCertificates(File file) throws CertificateException {
		try {
			InputStream in = new FileInputStream(file);

			try {
				return readCertificates(in);
			} finally {
				safeClose(in);
			}
		} catch (FileNotFoundException e) {
			throw new CertificateException("could not find certificate file: " + file);
		}
	}
	private static final Pattern CERT_PATTERN = Pattern.compile(
			"-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+" + // Header
					"([a-z0-9+/=\\r\\n]+)" +                    // Base64 text
					"-+END\\s+.*CERTIFICATE[^-]*-+",            // Footer
			Pattern.CASE_INSENSITIVE);
	static List<byte[]> readCertificates(InputStream in) throws CertificateException {
		String content;
		try {
			content = readContent(in);
		} catch (IOException e) {
			throw new CertificateException("failed to read certificate input stream", e);
		}

		List<byte[]> certs = new ArrayList<byte[]>();
		Matcher m = CERT_PATTERN.matcher(content);
		int start = 0;
		for (;;) {
			if (!m.find(start)) {
				break;
			}

			byte[] der = Base64Commons.decodeBase64(SimpleStringUtil.getBytesUsAscii(m.group(1)));;

			certs.add(der);

			start = m.end();
		}

		if (certs.isEmpty()) {
			throw new CertificateException("found no certificates in input stream");
		}

		return certs;
	}
	private static X509Certificate[] getCertificatesFromBuffers(List<byte[]> certs) throws CertificateException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate[] x509Certs = new X509Certificate[certs.size()];

		int i = 0;
		try {
			for (; i < certs.size(); i++) {
				InputStream is = new ByteArrayInputStream(certs.get(i));
				try {
					x509Certs[i] = (X509Certificate) cf.generateCertificate(is);
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						// This is not expected to happen, but re-throw in case it does.
						throw new RuntimeException(e);
					}
				}
			}
		} finally {
			certs.clear();
		}
		return x509Certs;
	}
	static PrivateKey toPrivateKey(File keyFile, String keyPassword) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException,
			KeyException, IOException {
		if (keyFile == null) {
			return null;
		}
		return getPrivateKeyFromByteBuffer(readPrivateKey(keyFile), keyPassword);
	}
	private static PrivateKey getPrivateKeyFromByteBuffer(byte[] encodedKey, String keyPassword)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
			InvalidAlgorithmParameterException, KeyException, IOException {

//		byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
//		encodedKeyBuf.readBytes(encodedKey).release();

		PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(
				keyPassword == null ? null : keyPassword.toCharArray(), encodedKey);
		try {
			return KeyFactory.getInstance("RSA").generatePrivate(encodedKeySpec);
		} catch (InvalidKeySpecException ignore) {
			try {
				return KeyFactory.getInstance("DSA").generatePrivate(encodedKeySpec);
			} catch (InvalidKeySpecException ignore2) {
				try {
					return  KeyFactory.getInstance("EC").generatePrivate(encodedKeySpec);
				} catch (InvalidKeySpecException e) {
					throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", e);
				}
			}
		}
	}

	static byte[] readPrivateKey(File file) throws KeyException {
		try {
			InputStream in = new FileInputStream(file);

			try {
				return readPrivateKey(in);
			} finally {
				safeClose(in);
			}
		} catch (FileNotFoundException e) {
			throw new KeyException("could not find key file: " + file);
		}
	}
	private static final Pattern KEY_PATTERN = Pattern.compile(
			"-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+" + // Header
					"([a-z0-9+/=\\r\\n]+)" +                       // Base64 text
					"-+END\\s+.*PRIVATE\\s+KEY[^-]*-+",            // Footer
			Pattern.CASE_INSENSITIVE);
	public static final Charset US_ASCII = Charset.forName("US-ASCII");
	private static String readContent(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] buf = new byte[8192];
			for (;;) {
				int ret = in.read(buf);
				if (ret < 0) {
					break;
				}
				out.write(buf, 0, ret);
			}
			return out.toString(US_ASCII.name());
		} finally {
			safeClose(out);
		}
	}
	private static void safeClose(OutputStream out) {
		try {
			out.close();
		} catch (IOException e) {
			logger.warn("Failed to close a stream.", e);
		}
	}

	private static void safeClose(InputStream in) {
		try {
			in.close();
		} catch (IOException e) {
			logger.warn("Failed to close a stream.", e);
		}
	}
	static byte[] readPrivateKey(InputStream in) throws KeyException {
		String content;
		try {
			content = readContent(in);
		} catch (IOException e) {
			throw new KeyException("failed to read key input stream", e);
		}

		Matcher m = KEY_PATTERN.matcher(content);
		if (!m.find()) {
			throw new KeyException("could not find a PKCS #8 private key in input stream" +
					" (see http://netty.io/wiki/sslcontextbuilder-and-private-key.html for more information)");
		}

		byte[] base64 = Base64Commons.decodeBase64(SimpleStringUtil.getBytesUsAscii(m.group(1)));

		return base64;
	}
	/**
	 * Generates a key specification for an (encrypted) private key.
	 *
	 * @param password characters, if {@code null} an unencrypted key is assumed
	 * @param key bytes of the DER encoded private key
	 *
	 * @return a key specification
	 *
	 * @throws IOException if parsing {@code key} fails
	 * @throws NoSuchAlgorithmException if the algorithm used to encrypt {@code key} is unknown
	 * @throws NoSuchPaddingException if the padding scheme specified in the decryption algorithm is unknown
	 * @throws InvalidKeySpecException if the decryption key based on {@code password} cannot be generated
	 * @throws InvalidKeyException if the decryption key based on {@code password} cannot be used to decrypt
	 *                             {@code key}
	 * @throws InvalidAlgorithmParameterException if decryption algorithm parameters are somehow faulty
	 */
	protected static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key)
			throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
			InvalidKeyException, InvalidAlgorithmParameterException {

		if (password == null) {
			return new PKCS8EncodedKeySpec(key);
		}

		EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName());
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
		SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);

		Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
		cipher.init(Cipher.DECRYPT_MODE, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());

		return encryptedPrivateKeyInfo.getKeySpec(cipher);
	}

	public static X509Certificate[] exportRootCertificates(final KeyStore ks, final String alias) throws KeyStoreException {
		logKeyStore(ks);

		final List<X509Certificate> trustedCerts = new ArrayList<X509Certificate>();

		if (SimpleStringUtil.isEmpty(alias)) {

			if(logger.isDebugEnabled()) {
				logger.debug("No alias given, will trust all of the certificates in the store");
			}

			final List<String> aliases = toList(ks.aliases());

			for (final String _alias : aliases) {

				if (ks.isCertificateEntry(_alias)) {
					final X509Certificate cert = (X509Certificate) ks.getCertificate(_alias);
					if (cert != null) {
						trustedCerts.add(cert);
					} else {
						logger.error("Alias {} does not exist", _alias);
					}
				}
			}
		} else {
			if (ks.isCertificateEntry(alias)) {
				final X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
				if (cert != null) {
					trustedCerts.add(cert);
				} else {
					logger.error("Alias {} does not exist", alias);
				}
			} else {
				logger.error("Alias {} does not contain a certificate entry", alias);
			}
		}

		return trustedCerts.toArray(new X509Certificate[0]);
	}

	public static X509Certificate[] exportServerCertChain(final KeyStore ks, String alias) throws KeyStoreException {
		logKeyStore(ks);
		final List<String> aliases = toList(ks.aliases());

		if (SimpleStringUtil.isEmpty(alias)) {
			if(aliases.isEmpty()) {
				logger.error("Keystore does not contain any aliases");
			} else {
				alias = aliases.get(0);
				logger.info("No alias given, use the first one: {}", alias);
			}
		}

		final Certificate[] certs = ks.getCertificateChain(alias);
		if (certs != null && certs.length > 0) {
			X509Certificate[] x509Certs = Arrays.copyOf(certs, certs.length, X509Certificate[].class);

			final X509Certificate lastCertificate = x509Certs[x509Certs.length - 1];

			if (lastCertificate.getBasicConstraints() > -1
					&& lastCertificate.getSubjectX500Principal().equals(lastCertificate.getIssuerX500Principal())) {
				logger.warn("Certificate chain for alias {} contains a root certificate", alias);

				if(stripRootFromChain ) {
					x509Certs = Arrays.copyOf(certs, certs.length-1, X509Certificate[].class);
				}
			}

			return x509Certs;
		} else {
			logger.error("Alias {} does not exist or contain a certificate chain", alias);
		}

		return new X509Certificate[0];
	}

	public static PrivateKey exportDecryptedKey(final KeyStore ks, final String alias, final char[] password) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		logKeyStore(ks);
		final List<String> aliases = toList(ks.aliases());

		String evaluatedAlias = alias;

		if (alias == null && aliases.size() > 0) {
			evaluatedAlias = aliases.get(0);
		}

		if (evaluatedAlias == null) {
			throw new KeyStoreException("null alias, current aliases: " + aliases);
		}

		final Key key = ks.getKey(evaluatedAlias, (password == null || password.length == 0) ? null:password);

		if (key == null) {
			throw new KeyStoreException("no key alias named " + evaluatedAlias);
		}

		if (key instanceof PrivateKey) {
			return (PrivateKey) key;
		}

		return null;
	}

	private static void logKeyStore(final KeyStore ks) {
		try {
			final List<String> aliases = toList(ks.aliases());
			if (logger.isDebugEnabled()) {
				logger.debug("Keystore has {} entries/aliases", ks.size());
				for (String _alias : aliases) {
					logger.debug("Alias {}: is a certificate entry?{}/is a key entry?{}", _alias, ks.isCertificateEntry(_alias),
							ks.isKeyEntry(_alias));
					Certificate[] certs = ks.getCertificateChain(_alias);

					if (certs != null) {
						logger.debug("Alias {}: chain len {}", _alias, certs.length);
						for (int i = 0; i < certs.length; i++) {
							X509Certificate certificate = (X509Certificate) certs[i];
							logger.debug("cert {} of type {} -> {}", certificate.getSubjectX500Principal(), certificate.getBasicConstraints(),
									certificate.getSubjectX500Principal().equals(certificate.getIssuerX500Principal()));
						}
					}

					X509Certificate cert = (X509Certificate) ks.getCertificate(_alias);

					if (cert != null) {
						logger.debug("Alias {}: single cert {} of type {} -> {}", _alias, cert.getSubjectX500Principal(),
								cert.getBasicConstraints(), cert.getSubjectX500Principal().equals(cert.getIssuerX500Principal()));
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error logging keystore due to "+e, e);
		}
	}

	private static List<String> toList(final Enumeration<String> enumeration) {
		final List<String> aliases = new ArrayList<String>();

		while (enumeration.hasMoreElements()) {
			aliases.add(enumeration.nextElement());
		}

		return Collections.unmodifiableList(aliases);
	}
}

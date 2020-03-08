/*
 * Copyright (C) 2015 Square, Inc.
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
 * 
 * modified by Geoffrey -- 22 Feb 2020
 */

package com.developersOfTheMillennium.motm.ssl;


import android.content.Context;

import com.developersOfTheMillennium.motm.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.CertificatePinner;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ConnectionSpec;
import okio.Buffer;

import javax.net.ssl.HostnameVerifier;




/* The puspose of this class is host static (or just public) SSL functions
 * to configure the https connection (for example pinning, specifying trusted CAs)
 * 
 * see README.txt for resources as needed
 * 
 * sources:
 *   https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/CustomTrust.java
 */

public class SecureHTTPClient
{
    private final OkHttpClient client;
    private Context context;

  class KeyAndTrustManagers {
    final KeyManager[] keyManagers;
    final TrustManager[] trustManagers;

    KeyAndTrustManagers(KeyManager[] keyManagers, TrustManager[] trustManagers) {
      this.keyManagers = keyManagers;
      this.trustManagers = trustManagers;
    }
  }

  public SecureHTTPClient(final String ADDR, Context current) {
    this.context = current;

    SSLSocketFactory sslSocketFactory;
    KeyAndTrustManagers keyAndTrustManagers;
    try {
      keyAndTrustManagers = trustManagerForCertificates(trustedCertificatesInputStream());
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(keyAndTrustManagers.keyManagers, keyAndTrustManagers.trustManagers, null);
      sslSocketFactory = sslContext.getSocketFactory();

      X509TrustManager trustManager = (X509TrustManager) keyAndTrustManagers.trustManagers[0];

      /* this is not necessary */
      /*CertificatePinner certPinner = new CertificatePinner.Builder()
              //.add(ADDR, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=") //this is used to obtain the proper hash as shown below
              .add(ADDR, "sha256/5JYuRPkxVH6KnMhxOcxA17Kx5hGcJGhWmu06rZyunCw=")
              .build();*/
      client = new OkHttpClient().newBuilder()
          .connectTimeout(3, TimeUnit.SECONDS)
          .readTimeout(5, TimeUnit.SECONDS)
          .sslSocketFactory(sslSocketFactory, trustManager)
          .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
          //.certificatePinner(certPinner)
          .hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
              //System.out.println(ADDR);
              //System.out.println(hostname);
              //System.out.println(session.getPeerHost());
              return hostname.equalsIgnoreCase(ADDR) ? true : hostname.equalsIgnoreCase(session.getPeerHost());
            }
          }) //NOTE: this is very unsafe, only for dev purposes
          .build();
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }





  }

  public Response run(Request request) throws Exception {
    System.out.println("== executing request on secure client");
    return client.newCall(request).execute();
  }

  /**
   * Returns an input stream containing one or more certificate PEM files. This implementation just
   * embeds the PEM files in Java strings; most applications will instead read this from a resource
   * file that gets bundled with the application.
   */
  private InputStream trustedCertificatesInputStream() {
    // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
    // Typically developers will need to get a PEM file from their organization's TLS administrator.
    //SELF SIGNED CERTIFICATE
    return context.getResources().openRawResource(R.raw.motm_cert);

  }

  /**
   * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
   * certificates have not been signed by these certificates will fail with a {@code
   * SSLHandshakeException}.
   *
   * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
   * set. This is useful in development where certificate authority-trusted certificates aren't
   * available. Or in production, to avoid reliance on third-party certificate authorities.
   *
   * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
   * the host platform's built-in trust store.
   *
   * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
   *
   * <p>Relying on your own trusted certificates limits your server team's ability to update their
   * TLS certificates. By installing a specific set of trusted certificates, you take on additional
   * operational complexity and limit your ability to migrate between certificate authorities. Do
   * not use custom trusted certificates in production without the blessing of your server's TLS
   * administrator.
   */
  private KeyAndTrustManagers trustManagerForCertificates(InputStream in)
      throws GeneralSecurityException {
    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
    if (certificates.isEmpty()) {
      throw new IllegalArgumentException("expected non-empty set of trusted certificates");
    }

    // Put the certificates a key store.
    char[] password = "password".toCharArray(); // Any password will work.
    KeyStore keyStore = newEmptyKeyStore(password);
    int index = 0;
    for (Certificate certificate : certificates) {
      String certificateAlias = Integer.toString(index++);
      keyStore.setCertificateEntry(certificateAlias, certificate);
    }

    // Use it to build an X509 trust manager.
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
        KeyManagerFactory.getDefaultAlgorithm());
    keyManagerFactory.init(keyStore, password);
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
        TrustManagerFactory.getDefaultAlgorithm());
    trustManagerFactory.init(keyStore);
    return new KeyAndTrustManagers(
            keyManagerFactory.getKeyManagers(),
            trustManagerFactory.getTrustManagers());
  }

  private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
    try {
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      InputStream in = null; // By convention, 'null' creates an empty key store.
      keyStore.load(in, password);
      return keyStore;
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}

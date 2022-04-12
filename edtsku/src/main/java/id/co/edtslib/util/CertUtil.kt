package id.co.edtslib.util

import java.io.InputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.TrustManagerFactory


object CertUtil {
    fun getTrustManager(cert: InputStream): TrustManagerFactory {
        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")

        val ca: Certificate = cf.generateCertificate(cert)
        cert.close()

        val keyStoreType: String = KeyStore.getDefaultType()
        val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

        return tmf
        //sslContext = SSLContext.getInstance("TLS")
        //sslContext.init(null, tmf.getTrustManagers(), null)
    }
}
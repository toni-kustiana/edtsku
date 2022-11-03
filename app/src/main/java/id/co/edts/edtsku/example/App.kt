package id.co.edts.edtsku.example

import android.app.Application
import id.co.edts.edtsku.example.di.appModule
import id.co.edts.edtsku.example.di.repositoryModule
import id.co.edts.edtsku.example.di.viewModelModule
import id.co.edtslib.EdtsKu
import id.co.edtslib.util.CommonUtil
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //val cert: InputStream = baseContext.resources.openRawResource(R.raw.elevenia_co_id)

        Timber.plant(Timber.DebugTree())
        EdtsKu.debugging = true
        //EdtsKu.trustManagerFactory = CertUtil.getTrustManager(cert)
        EdtsKu.sslPinner =
            CommonUtil.hexToAscii("7368613235362f71316e6673616d64614d666a44464b6e54367574315433614c73783250383851754257445a6a595a7048733d")
        EdtsKu.sslDomain =
            CommonUtil.hexToAscii("656c6576656e69612e636f2e6964")

        EdtsKu.init(
            this,
            "http://gurihmart-staging.sg-edts.co.id",
            "/customer/api/mobile/user/refresh",
            listOf(appModule, repositoryModule, viewModelModule)
        )
    }
}
package org.sopar.presentation.base

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import org.sopar.BuildConfig

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}
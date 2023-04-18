package com.tiger.ar.museum.data

import okhttp3.Interceptor

class FqaRetrofitConfig : BaseRetrofitConfig() {
    override fun getUrl(): String {
        return ApiConfig.MOCK_URL
    }

    override fun getInterceptorList(): Array<Interceptor> {
        return arrayOf()
    }
}

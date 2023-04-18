package com.tiger.ar.museum.data

import android.util.Log
import com.tiger.ar.museum.common.exception.APIException
import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap

object RetrofitFactory {
    private val TAG = RetrofitFactory::class.java.simpleName
    private const val FQA = "FQA"
    private const val CRM = "CRM"
    private const val AUTH = "AUTH"

    private val builderMap = ConcurrentHashMap<String, RetrofitBuilderInfo>()

    fun <T> createFqaService(service: Class<T>): T {
        synchronized(RetrofitBuilderInfo::class.java) {
            var builderInfo = builderMap[FQA]
            if (builderInfo == null) {
                builderInfo = RetrofitBuilderInfo()

                builderInfo.builder = FqaRetrofitConfig().getRetrofitBuilder()

                builderMap[FQA] = builderInfo
                Log.d(TAG, "Create new domain retrofit builder for ${ApiConfig.MOCK_URL}")
            }
            Log.e(TAG, "Reuse domain retrofit builder for ${ApiConfig.MOCK_URL}")
            val serviceApi = builderInfo.builder?.build()?.create(service)

            return serviceApi ?: throw APIException(APIException.CREATE_INSTANCE_SERVICE_ERROR)
        }
    }

    class RetrofitBuilderInfo {
        var builder: Retrofit.Builder? = null
        var accessToken: String? = null

        fun valid(accessToken: String?): Boolean {
            return this.accessToken == accessToken
        }
    }
}

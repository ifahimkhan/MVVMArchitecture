package com.example.mvvmarchitecture.data.api

import com.example.mvvmarchitecture.utils.AppConstant
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            proceed(
                request().newBuilder()
                    .addHeader(
                        AppConstant.ApiHeaders.Key.API_KEY,
                        AppConstant.ApiHeaders.Value.API_KEY
                    )
                    .build()
            )
        }
    }
}
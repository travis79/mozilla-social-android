package org.mozilla.social.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.mozilla.social.core.network.interceptors.AuthCredentialInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

fun networkModule(isDebug: Boolean) = module {
    single { AuthCredentialInterceptor() }
    single(
        named(AUTHORIZED_CLIENT)
    ) {
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = if (isDebug) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor(get<AuthCredentialInterceptor>())
            .build()
    }

    single(named(RECCS_CLIENT)) {
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = if (isDebug) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }
    single(
        named(RECCS_SERVICE)
    ) {
        Retrofit.Builder()
            .baseUrl("https://firefox-api-proxy.cdn.mozilla.net/")
            .client(get(qualifier = named(RECCS_CLIENT)))
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://mozilla.social/")
            .client(get(qualifier = named(AUTHORIZED_CLIENT)))
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .build()
    }

    single { get<Retrofit>().create(AccountApi::class.java) }
    single { get<Retrofit>().create(MediaApi::class.java) }
    single { get<Retrofit>().create(OauthApi::class.java) }
    single { get<Retrofit>().create(SearchApi::class.java) }
    single { get<Retrofit>().create(StatusApi::class.java) }
    single { get<Retrofit>().create(TimelineApi::class.java) }
    single { get<Retrofit>().create(InstanceApi::class.java) }
    single { get<Retrofit>().create(ReportApi::class.java) }
    single { get<Retrofit>(named(RECCS_SERVICE)).create(RecommendationApi::class.java) }
    single { get<Retrofit>().create(AppApi::class.java) }
}

private var json: Json = Json { ignoreUnknownKeys = true }
private const val AUTHORIZED_CLIENT = "authorizedClient"
private const val RECCS_CLIENT = "reccsClient"
private const val RECCS_SERVICE = "reccsService"

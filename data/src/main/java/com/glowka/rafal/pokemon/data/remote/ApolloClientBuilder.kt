package com.glowka.rafal.pokemon.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

object ApolloClientBuilder {

  private const val BASE_URL = "https://dex-server.herokuapp.com/"

  fun create(okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient.Builder()
      .serverUrl(BASE_URL)
      .okHttpClient(okHttpClient = okHttpClient)
      .build()
  }

}
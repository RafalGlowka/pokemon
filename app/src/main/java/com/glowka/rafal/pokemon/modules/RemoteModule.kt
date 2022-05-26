package com.glowka.rafal.pokemon.modules

import com.apollographql.apollo3.ApolloClient
import com.glowka.rafal.pokemon.data.remote.*
import okhttp3.OkHttpClient
import org.koin.dsl.module

/**
 * Created by Rafal on 13.04.2021.
 */
val remoteModule = module {

  factory<OkHttpClient> {
    OkHttpClient.Builder().build()
  }

  single<JSONSerializer> {
    JSONSerializerImpl()
  }

  single<ApolloClient> {
    ApolloClientBuilder.create(okHttpClient = get())
  }

}
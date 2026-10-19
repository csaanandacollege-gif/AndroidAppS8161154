package com.example.assignment2.di
import com.example.assignment2.data.AssignmentApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
 @Provides @Singleton
 fun provideApi(): AssignmentApi {
  val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
  return Retrofit.Builder().baseUrl("https://nit3213apinew.onrender.com/")
   .addConverterFactory(MoshiConverterFactory.create(moshi)).build().create(AssignmentApi::class.java)
 }
}

package com.example.assignment2.data
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
interface AssignmentApi {
 @POST("br/auth") suspend fun login(@Body request: LoginRequest): LoginResponse
 @GET("dashboard/{keypass}") suspend fun dashboard(@Path("keypass") keypass: String): DashboardResponse
}

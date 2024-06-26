package com.example.myapplication.retrofit

import com.example.myapplication.response.DetailResponse
import com.example.myapplication.response.EditProfileResponse
import com.example.myapplication.response.FileAddResponse
import com.example.myapplication.response.HistoryResponse
import com.example.myapplication.response.HistoryResponseItem

import com.example.myapplication.response.LoginResponse1
import com.example.myapplication.response.ProfileResponse
import com.example.myapplication.response.RegisterResponse
import com.example.myapplication.response.ScanResponse
import com.example.myapplication.response.StoryResponse
import com.example.myapplication.ui.history.ScanHistory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse1

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<StoryResponse>

    @GET("stories/{id}")
    fun getDetailStories(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DetailResponse>

//    @Multipart
//    @POST("sharing")
//    fun uploadImage(
//        @Header("Authorization") token: String,
//        @Part("content") content: RequestBody,
//        //@Part imgUrl: MultipartBody.Part?
//    ): Call<FileAddResponse>

    @GET("profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<ProfileResponse>

    @Multipart
    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part profileImage: MultipartBody.Part?
    ): Response<EditProfileResponse>

    @PUT("profile")
    suspend fun editUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body userInfo: Map<String, String>
    ): Response<EditProfileResponse>

    @Multipart
    @POST("upload/profile")
    fun uploadProfilePicture(
        @Part image: MultipartBody.Part,
        @Part("userId") userId: RequestBody
    ): Call<EditProfileResponse>

    @Multipart
    @POST("predict")  // Ganti dengan endpoint upload gambar Anda
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("gender") gender: RequestBody
    ): Call<ScanResponse>




    @GET("predictions")
    fun getScanHistory(@Header("Authorization") token: String
    ): Call<List<HistoryResponseItem>>


}
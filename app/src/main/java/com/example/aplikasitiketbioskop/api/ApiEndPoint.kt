package com.example.menuapp.Api

import com.example.aplikasitiketbioskop.model.MovieModel
import com.example.aplikasitiketbioskop.model.OrderModel
import com.example.aplikasitiketbioskop.model.UserModel
import com.example.aplikasitiketbioskop.model.ResponseModel
import com.example.aplikasitiketbioskop.model.SeatModel
import com.example.aplikasitiketbioskop.model.TicketModel
import com.example.aplikasitiketbioskop.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.Date

interface ApiEndPoint {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String, @Field("password") password: String): Call<UserModel>

    @FormUrlEncoded
    @GET("refresh")
    fun refresh(): Call<UserModel>

    @GET("movie")
    fun getMovie(): Call<MovieModel>

    @GET("movie/{id}")
    fun showMovie(@Path("id") id: Int): Call<MovieModel>

    @GET("seat")
    fun getSeat(
        @Query("movie_schedule_id") id: Int,
        @Query("screening_date") date: String
    ): Call<SeatModel>

    @POST("order")
    fun createOrder(): Call<OrderModel>

    @FormUrlEncoded
    @POST("order/detail")
    fun createOrderDetail(
        @Field("order_id") order: Int,
        @Field("movie_schedule_id") schedule: Int,
        @Field("seat_id") seat: Int,
        @Field("date_screening") date: String
    ): Call<ResponseModel>

    @GET("ticket")
    fun getTicket(): Call<TicketModel>
}
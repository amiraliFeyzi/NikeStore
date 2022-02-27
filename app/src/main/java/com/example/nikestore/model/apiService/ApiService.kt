package com.example.nikestore.model.apiService

import com.example.nikestore.model.dataclass.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("product/list")
    suspend fun getProduct(@Query("sort")sort:String):List<Product>

    @GET("product/search")
    suspend fun searchProduct(@Query("q")nameProduct:String):List<Product>

    @GET("banner/slider")
    suspend fun getBanners():List<Banner>

    @GET("comment/list")
    suspend fun getComments(@Query("product_id") productId: Int): List<Comment>

    @POST("comment/add")
    suspend fun addComment(@Body jsonObject: JsonObject)

    @POST("cart/add")
    suspend fun addToCart(@Body jsonObject: JsonObject): AddToCartResponse

    @POST("cart/remove")
    suspend fun removeItemFromCart(@Body jsonObject: JsonObject): MessageResponse

    @GET("cart/list")
    suspend fun getCart(): CartResponse

    @POST("cart/changeCount")
    suspend fun changeCount(@Body jsonObject: JsonObject): AddToCartResponse

    @GET("cart/count")
    suspend fun getCartItemsCount():CartItemCount

    @POST("auth/token")
    suspend fun login(@Body jsonObject: JsonObject): TokenResponse

    @POST("user/register")
    suspend fun signUp(@Body jsonObject: JsonObject): MessageResponse

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>

    @POST("order/submit")
    suspend fun submitOrder(@Body jsonObject: JsonObject):SubmitOrderResult

    @GET("order/checkout")
    suspend fun checkOut(@Query("order_id")orderId:Int):Checkout

    @GET("order/list")
    suspend fun orders():List<OrderHistoryItem>

}
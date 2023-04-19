package com.example.wellfed.functionality

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @GET("posts")
    fun getData() : Call<List<TestData>>

    @GET("v1/nutrition")
    fun getFoodData(@Query("food_item") food_item : String,
                    @Header("x-api-key") apiKey : String) : Call<List<NutritionDataItem>>

    @GET("integers/")
    suspend fun getRandomNumberOrg(@Query("num") num : Int,
                                   @Query("min") min : Int,
                                   @Query("max") max : Int,
                                   @Query("col") col : Int,
                                   @Query("base") base : Int,
                                   @Query("format") format : String,
                                   @Query("rnd") rnd : String) : TestData
    @GET("api/v1.0/random")
    suspend fun getRandomNumber(@Query("min") min : Int,
                                @Query("max") max : Int,
                                @Query("cnt") cnt : Int) : List<TestData>

}
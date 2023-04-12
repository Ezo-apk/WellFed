package com.example.wellfed.functionality

import retrofit2.Call

interface ApiInterface {

//    @GET(value)
    fun getFoodData(): Call<List<NutritionDataItem>>

}
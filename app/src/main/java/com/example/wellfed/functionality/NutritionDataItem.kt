package com.example.wellfed.functionality

data class NutritionDataItem(
    val name: String,
    val calories: Int,
    val serving_size_g: Int,
    val fat_total_g: Double,
    val fat_saturated_g: Double,
    val protein_g: Double,
    val sodium_mg: Int,
    val potassium_mg: Int,
    val cholesterol_mg: Int,
    val carbohydrates_total_g: Double,
    val fiber_g: Double,
    val sugar_g: Double
)
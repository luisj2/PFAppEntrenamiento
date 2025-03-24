package com.example.fithealth.ViewModel.FoodApi

import com.example.fithealth.Model.FoodApi.FatSecretRepository
import com.example.fithealth.Model.Retrofit.FatSecret.FatSecretBuilder

object FatSecretViewModelBuilder {

    fun getFatSecretViewModelFactory(): FatSecretViewModelFactory = FatSecretViewModelFactory(
        FatSecretRepository(FatSecretBuilder.createService())
    )
}
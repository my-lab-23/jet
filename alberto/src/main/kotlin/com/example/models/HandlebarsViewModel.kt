package com.example.models

import org.http4k.template.ViewModel

data class ImageViewModel(
    val sha256: String,
    val titolo: String,
    val descrizione: String
) : ViewModel

data class MultiImageViewModel(val images: List<ImageViewModel>) : ViewModel

package com.gds.brasilnoticias.data.local.model

data class RespostaDaNoticia(
    val articles: MutableList<Artigo>,
    val status: String,
    val totalResults: Int
)
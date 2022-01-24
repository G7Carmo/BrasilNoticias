package com.gds.brasilnoticias.util.state

import com.gds.brasilnoticias.data.local.model.Artigo

sealed class ArticleListState{
    data class Sucesso(val list : List<Artigo>) : ArticleListState()
    data class ErrorMessage(val errorMessage : String) : ArticleListState()
    object Loading : ArticleListState()
    object Empty : ArticleListState()
}

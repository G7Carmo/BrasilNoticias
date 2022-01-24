package com.gds.brasilnoticias.util.state

sealed class ArticleListEvent{
    object Fetch : ArticleListEvent()
}

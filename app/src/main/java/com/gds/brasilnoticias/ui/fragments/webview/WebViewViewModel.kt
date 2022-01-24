package com.gds.brasilnoticias.ui.fragments.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gds.brasilnoticias.data.local.model.Artigo
import com.gds.brasilnoticias.repository.NewsRepository
import kotlinx.coroutines.launch

class WebViewViewModel constructor(
    private val repository: NewsRepository
) : ViewModel() {
    fun salvarArtigo(artigo: Artigo) = viewModelScope.launch {
        repository.updateInsert(artigo)
    }
}
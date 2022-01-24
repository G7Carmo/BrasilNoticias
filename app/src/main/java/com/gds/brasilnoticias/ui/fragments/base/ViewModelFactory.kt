package com.gds.brasilnoticias.ui.fragments.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.ui.fragments.favorito.FavoritosViewModel
import com.gds.brasilnoticias.ui.fragments.home.HomeViewModel
import com.gds.brasilnoticias.ui.fragments.pesquisa.PesquisaViewModel
import com.gds.brasilnoticias.ui.fragments.webview.WebViewViewModel

class ViewModelFactory(
    private val repository: NewsRepository,
    private val application: Application
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository,application) as T
            modelClass.isAssignableFrom(PesquisaViewModel::class.java) -> PesquisaViewModel(repository,application) as T
            modelClass.isAssignableFrom(WebViewViewModel::class.java) -> WebViewViewModel(repository) as T
            modelClass.isAssignableFrom(FavoritosViewModel::class.java) -> FavoritosViewModel(repository) as T
            else -> throw IllegalArgumentException("View Model Nao Encontrado ")
        }
    }
}
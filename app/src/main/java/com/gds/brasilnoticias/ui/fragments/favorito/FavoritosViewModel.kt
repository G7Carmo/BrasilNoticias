package com.gds.brasilnoticias.ui.fragments.favorito

import androidx.lifecycle.*
import com.gds.brasilnoticias.data.local.model.Artigo
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.util.state.ArticleListEvent
import com.gds.brasilnoticias.util.state.ArticleListState
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoritosViewModel constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _favorite = MutableLiveData<ArticleListEvent>()
    val favorite: LiveData<ArticleListState> = _favorite.switchMap {
        when(it){
            ArticleListEvent.Fetch-> getAll()
        }
    }

    fun dispatch(event: ArticleListEvent){
        this._favorite.postValue(event)
    }

    private fun getAll(): LiveData<ArticleListState> {
        return liveData {
            try {
                emit(ArticleListState.Loading)
                val listliveData = repository.getAll()
                    .map {list->
                        if (list.isEmpty()){
                            ArticleListState.Empty
                        }else{
                            ArticleListState.Sucesso(list)
                        }
                    }
                emitSource(listliveData)
            }catch (e : Exception){
                emit(ArticleListState.ErrorMessage("Algo deu errado ${e.message}"))
            }
        }
    }


    fun salvarArtigo(artigo: Artigo) = viewModelScope.launch {
        repository.updateInsert(artigo)
    }

    fun deleteArtigo(artigo: Artigo) = viewModelScope.launch {
        repository.delete(artigo)
    }


}
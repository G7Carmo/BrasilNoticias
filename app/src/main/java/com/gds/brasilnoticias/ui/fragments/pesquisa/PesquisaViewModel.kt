package com.gds.brasilnoticias.ui.fragments.pesquisa

import android.app.Application
import androidx.lifecycle.*
import com.gds.brasilnoticias.data.local.model.RespostaDaNoticia
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.util.checkForInternetConnection
import com.gds.brasilnoticias.util.state.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class PesquisaViewModel constructor(
    private val repository: NewsRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _search = MutableLiveData<StateResource<RespostaDaNoticia>>()
    val search: LiveData<StateResource<RespostaDaNoticia>> = _search

    fun fetchSearch(query: String) = viewModelScope.launch {
        safeFetchSearch(query)
    }

    private suspend fun safeFetchSearch(query: String) {
        _search.value = StateResource.Loanding()
        try {
            if (checkForInternetConnection(getApplication())){
                val response = repository.search(query = query)
                _search.value = handleResponse(response)
            }else{
                _search.value = StateResource.Error("Falha na conexao")
            }
        } catch (e: Exception) {

        }
    }

    private fun handleResponse(response: Response<RespostaDaNoticia>): StateResource<RespostaDaNoticia> {
        if (response.isSuccessful) {
            response.body()?.let { value ->
                return StateResource.Sucesso(value)
            }
        }
        return StateResource.Error(response.message())
    }

}

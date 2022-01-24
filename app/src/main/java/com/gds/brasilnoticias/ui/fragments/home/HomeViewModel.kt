package com.gds.brasilnoticias.ui.fragments.home

import android.app.Application
import androidx.lifecycle.*
import com.gds.brasilnoticias.data.local.model.RespostaDaNoticia
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.util.checkForInternetConnection
import com.gds.brasilnoticias.util.state.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class HomeViewModel constructor(
    private val repository: NewsRepository,
    context: Application
) : AndroidViewModel(context) {
    private val _getAll = MutableLiveData<StateResource<RespostaDaNoticia>>()
    val getAll: LiveData<StateResource<RespostaDaNoticia>> = _getAll

    init {
        safeFatchAll()
    }

    private fun safeFatchAll() = viewModelScope.launch {
        _getAll.value = StateResource.Loanding()
        try {
            if (checkForInternetConnection(getApplication())) {
                val response = repository.getAllRemote()
                _getAll.value = handleResponse(response)
            } else {
                _getAll.value = StateResource.Error("Falha na conexao")

            }

        } catch (e: Exception) {
            _getAll.value = StateResource.Error("Artigos nao encontrados ${e.message}")
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
package com.gds.brasilnoticias.model.data

import android.content.Context
import com.gds.brasilnoticias.model.Artigo
import com.gds.brasilnoticias.model.db.ArtigoDataBase
import com.gds.brasilnoticias.network.RetrofitInstancia
import com.gds.brasilnoticias.presenter.favoritos.FavoritosInicial
import com.gds.brasilnoticias.presenter.noticia.NoticiaInicial
import com.gds.brasilnoticias.presenter.pesquisar.PesquisarInicial
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private val db: ArtigoDataBase = ArtigoDataBase(context)
    private var novoRepositorio : NewsRepository = NewsRepository(db)

    fun getPrincipaisNoticias(callback: NoticiaInicial.Presenter,country : String? = "br"){
        GlobalScope.launch(Dispatchers.Main){
            val resposta = country?.let { RetrofitInstancia.api.getPrincipaisNoticias(it) }
            if (resposta!!.isSuccessful){
                resposta.body()?.let {respotaComNoticia->
                    callback.sucesso(respotaComNoticia)
                }
                callback.completo()
            }else{
                callback.erro(resposta.message())
                callback.completo()
            }
        }
    }

    fun pesquisarNoticia(term :String,callback : PesquisarInicial.Presenter){
        GlobalScope.launch (Dispatchers.Main){
            val resposta = RetrofitInstancia.api.pesquisarNoticias(term)
            if (resposta.isSuccessful){
                resposta.body()?.let {newsResponse->
                    callback.sucesso(newsResponse)
                }
                callback.completo()
            }else{
                callback.erro(resposta.message())
                callback.completo()
            }
        }
    }

    fun savalArtigo(artigo: Artigo){
        GlobalScope.launch(Dispatchers.Main){
            novoRepositorio.updateInsert(artigo)
        }
    }
    fun getAllArtigos(callback: FavoritosInicial.Presenter){
        var todosOsArtigos : List<Artigo>
        CoroutineScope(Dispatchers.IO).launch {
            todosOsArtigos = novoRepositorio.getAll()

            withContext(Dispatchers.Main){
                callback.sucesso(todosOsArtigos)
            }
        }

    }

    fun deletarArtigo(artigo: Artigo?){
        GlobalScope.launch(Dispatchers.Main){
            artigo?.let {artigoSeguro->
                novoRepositorio.delete(artigoSeguro)
            }
        }
    }
}
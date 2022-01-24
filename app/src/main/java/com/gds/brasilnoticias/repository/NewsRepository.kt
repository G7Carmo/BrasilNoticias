package com.gds.brasilnoticias.repository

import androidx.lifecycle.LiveData
import com.gds.brasilnoticias.data.local.model.Artigo
import com.gds.brasilnoticias.data.local.db.ArtigoDataBase
import com.gds.brasilnoticias.data.remote.NoticiasApi

class NewsRepository(private val db : ArtigoDataBase,private val api : NoticiasApi ) {

    //remote
    suspend fun getAllRemote() = api.getPrincipaisNoticias()
    suspend fun  search(query : String) = api.pesquisarNoticias(query)

    //local
    suspend fun updateInsert(artigo: Artigo) = db.getArtigoDao().updateInsert(artigo)
    fun getAll() : LiveData<List<Artigo>> = db.getArtigoDao().getAll()
    suspend fun delete(artigo: Artigo) = db.getArtigoDao().delete(artigo)
}
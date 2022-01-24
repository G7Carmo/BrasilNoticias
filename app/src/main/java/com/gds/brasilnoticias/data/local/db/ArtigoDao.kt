package com.gds.brasilnoticias.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gds.brasilnoticias.data.local.model.Artigo

@Dao
interface ArtigoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(artigo: Artigo) : Long

    @Query("SELECT* FROM artigo")
    fun getAll(): LiveData<List<Artigo>>

    @Delete
    suspend fun delete(artigo: Artigo)


}
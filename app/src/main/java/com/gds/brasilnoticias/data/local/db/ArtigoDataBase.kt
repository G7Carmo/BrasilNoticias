package com.gds.brasilnoticias.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gds.brasilnoticias.data.local.model.Artigo
import com.gds.brasilnoticias.util.Constantes.BANCO_DE_DADOS

@Database(entities = [Artigo::class], version = 1, exportSchema = false)
@TypeConverters(Conversor::class)
abstract class ArtigoDataBase : RoomDatabase(){

    abstract fun getArtigoDao() : ArtigoDao

    companion object{

        @Volatile
        private var instance : ArtigoDataBase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock){
            instance ?: creacteDataBase(context).also { artigoDatabase->
                instance = artigoDatabase
            }
        }

        private fun creacteDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArtigoDataBase::class.java,
                BANCO_DE_DADOS
            ).build()

    }
}
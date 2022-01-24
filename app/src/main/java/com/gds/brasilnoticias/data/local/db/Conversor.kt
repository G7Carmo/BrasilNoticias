package com.gds.brasilnoticias.data.local.db

import androidx.room.TypeConverter
import com.gds.brasilnoticias.data.local.model.Fonte

class Conversor {
    @TypeConverter
    fun fromSource(fonte: Fonte) : String{
        return fonte.name
    }
    @TypeConverter
    fun toSource(name : String) : Fonte {
        return Fonte(name,name)
    }

}
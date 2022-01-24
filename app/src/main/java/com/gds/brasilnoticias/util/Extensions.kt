package com.gds.brasilnoticias.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gds.brasilnoticias.ui.fragments.base.BaseFragment
import java.util.*

fun View.show(){
    visibility = View.VISIBLE
}
fun View.hide(){
    visibility = View.INVISIBLE
}
fun Fragment.mensagem(context : Context,mensagem: String,tempo : Int = Toast.LENGTH_LONG){
    Toast.makeText(context,mensagem,tempo).show()
}
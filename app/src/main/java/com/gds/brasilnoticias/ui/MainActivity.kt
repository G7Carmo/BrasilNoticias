package com.gds.brasilnoticias.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.gds.brasilnoticias.R
import com.gds.brasilnoticias.adapter.MainAdapter
import com.gds.brasilnoticias.databinding.ActivityMainBinding
import com.gds.brasilnoticias.model.Artigo
import com.gds.brasilnoticias.model.data.NewsDataSource
import com.gds.brasilnoticias.presenter.ViewHome
import com.gds.brasilnoticias.presenter.noticia.NoticiaPresenter

class MainActivity : AbstractActivity(), ViewHome.View {
    private val mainAdapter by lazy { MainAdapter() }

    private lateinit var presenter: NoticiaPresenter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayout(): ViewBinding {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        configPresenter()
        configRecycler()
        clickAdapter()
    }

    private fun configPresenter() {
        val fonteDeDados = NewsDataSource(this)
        presenter = NoticiaPresenter(this, fonteDeDados)
        presenter.requisitarTudo()
    }

    private fun configRecycler() {
        with(binding.rvMoticias) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter() {
        mainAdapter.setOnClickListner { artigo ->
            val intent = Intent(this, ArtigoActivity::class.java)
            intent.putExtra("artigo", artigo)
            startActivity(intent)
        }
    }

    override fun mostrarBarraDeProgresso() {
        binding.rvProgressBar.visibility = View.VISIBLE
    }

    override fun showFalha(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    override fun esconderBarraDeProgresso() {
        binding.rvProgressBar.visibility = View.INVISIBLE
    }

    override fun mostrarArtigos(artigos: List<Artigo>) {
        mainAdapter.differ.submitList(artigos.toList())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.pesquisar_menu -> {
                Intent(this, PesquisarActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.favoritos_menu -> {
                Intent(this, FavoritosActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
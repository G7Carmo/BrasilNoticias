package com.gds.brasilnoticias.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import androidx.viewbinding.ViewBinding
import com.gds.brasilnoticias.R
import com.gds.brasilnoticias.adapter.MainAdapter
import com.gds.brasilnoticias.databinding.ActivityFavoritosBinding
import com.gds.brasilnoticias.model.Artigo
import com.gds.brasilnoticias.model.data.NewsDataSource
import com.gds.brasilnoticias.presenter.ViewHome
import com.gds.brasilnoticias.presenter.favoritos.FavoritosPresenter
import com.google.android.material.snackbar.Snackbar

class FavoritosActivity : AbstractActivity(), ViewHome.Favoritos {

    private val _adapter by lazy { MainAdapter() }
    private lateinit var presenter: FavoritosPresenter
    private lateinit var binding: ActivityFavoritosBinding

    override fun getLayout(): ViewBinding {
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        configPresenter()
        configRecycler()
        clickAdapter()
//        funcao de remover arrastando
        configRemoverItem()
    }

    private fun configRemoverItem() {
        val itemTouchPerCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder,target: RecyclerView.ViewHolder): Boolean = true
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val artigo = deletandoArtigo(viewHolder)
                snackbar(viewHolder, artigo)

            }

            private fun snackbar(
                viewHolder: RecyclerView.ViewHolder,
                artigo: Artigo?
            ) {
                Snackbar.make(
                    viewHolder.itemView,
                    R.string.article_delete_successful,
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setAction(getString(R.string.undo)) {
                        presenter.salvarArtigo(artigo!!)
                        _adapter.notifyDataSetChanged()
                    }
                    show()
                }
            }

        }
        //configurando a acao de remover arrastando ao recyclerView
        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavoritos)
        }

        presenter.getAll()
    }

    private fun deletandoArtigo(viewHolder: RecyclerView.ViewHolder): Artigo? {
        val position = viewHolder.adapterPosition
        val artigo = _adapter.differ.currentList[position]
        presenter.deleteArtigo(artigo)
        return artigo
    }

    private fun configPresenter() {
        val fonteDeDados = NewsDataSource(this)
        presenter = FavoritosPresenter(this, fonteDeDados)
        presenter.getAll()
    }

    override fun mostrarArtigos(artigos: List<Artigo>) {
        _adapter.differ.submitList(artigos.toList())
    }

    private fun configRecycler() {
        with(binding.rvFavoritos) {
            adapter = _adapter
            layoutManager = LinearLayoutManager(this@FavoritosActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoritosActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter() {
        _adapter.setOnClickListner { artigo ->
            val intent = Intent(this, ArtigoActivity::class.java)
            intent.putExtra("artigo", artigo)
            startActivity(intent)
        }
    }
}
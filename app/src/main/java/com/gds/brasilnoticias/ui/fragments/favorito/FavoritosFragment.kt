package com.gds.brasilnoticias.ui.fragments.favorito

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gds.brasilnoticias.R
import com.gds.brasilnoticias.data.local.db.ArtigoDataBase
import com.gds.brasilnoticias.data.local.model.Artigo
import com.gds.brasilnoticias.data.remote.RetrofitInstancia
import com.gds.brasilnoticias.databinding.FragmentFavoritosBinding
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.ui.adapter.MainAdapter
import com.gds.brasilnoticias.ui.fragments.base.BaseFragment
import com.gds.brasilnoticias.util.state.StateResource
import com.gds.brasilnoticias.util.hide
import com.gds.brasilnoticias.util.mensagem
import com.gds.brasilnoticias.util.show
import com.gds.brasilnoticias.util.state.ArticleListEvent
import com.gds.brasilnoticias.util.state.ArticleListState
import com.google.android.material.snackbar.Snackbar

class FavoritosFragment: BaseFragment<FavoritosViewModel,FragmentFavoritosBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun getViewModel(): Class<FavoritosViewModel> = FavoritosViewModel::class.java

    override fun getFragmenRepository(): NewsRepository =
        NewsRepository(ArtigoDataBase.invoke(requireContext()),RetrofitInstancia.api)

    override fun getFragmentBind(inflater: LayoutInflater,container: ViewGroup?): FragmentFavoritosBinding =
        FragmentFavoritosBinding.inflate(inflater,container,false)
    override fun onInject() {
        viewModel.dispatch(ArticleListEvent.Fetch)
        setupRecyclerView()
        observeResults()

    }
    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val artigo = deletandoArtigo(viewHolder)
            snackbar(viewHolder, artigo)
        }
    }
    //configurando a acao de remover arrastando ao recyclerView

    private fun setupRecyclerView() {
        with(binding.rvFavoritos) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
        }
        mainAdapter.setOnClickListner {artigo->
            val action = FavoritosFragmentDirections.actionFavoritosFragmentToWebViewFragment(artigo)
            findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun snackbar(viewHolder: RecyclerView.ViewHolder, artigo: Artigo?) {
        Snackbar.make(
            viewHolder.itemView,
            R.string.article_delete_successful,
            Snackbar.LENGTH_SHORT
        ).apply {
            setAction(getString(R.string.undo)) {
                viewModel.salvarArtigo(artigo!!)
                mainAdapter.notifyDataSetChanged()
            }
            show()
        }
        observeResults()
    }
    private fun deletandoArtigo(viewHolder: RecyclerView.ViewHolder): Artigo? {
        val position = viewHolder.adapterPosition
        val artigo = mainAdapter.differ.currentList[position]
        viewModel.deleteArtigo(artigo)
        return artigo
    }
    private fun observeResults() {
        viewModel.favorite.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is ArticleListState.Sucesso->{
                    binding.tvEmptyList.hide()
                        mainAdapter.differ.submitList(response.list)
                }
                is ArticleListState.ErrorMessage->{
                    binding.tvEmptyList.hide()
                    mensagem(requireContext(),"Ocorreu um Erro : ${response.errorMessage.toString()}")
                }
                is ArticleListState.Loading->{
                    binding.tvEmptyList.hide()
                }
                is ArticleListState.Empty->{
                    binding.tvEmptyList.show()
                    mainAdapter.differ.submitList(emptyList())                }
            }
        })
    }

}
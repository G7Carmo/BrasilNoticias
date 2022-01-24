package com.gds.brasilnoticias.ui.fragments.pesquisa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gds.brasilnoticias.data.local.db.ArtigoDataBase
import com.gds.brasilnoticias.data.remote.RetrofitInstancia
import com.gds.brasilnoticias.databinding.FragmentPesquisarBinding
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.ui.adapter.MainAdapter
import com.gds.brasilnoticias.ui.fragments.base.BaseFragment
import com.gds.brasilnoticias.util.*
import com.gds.brasilnoticias.util.UtilQueryTextListner
import com.gds.brasilnoticias.util.state.StateResource

class PesqusiaFragment : BaseFragment<PesquisaViewModel, FragmentPesquisarBinding>() {
    private val mainAdapter by lazy { MainAdapter() }


    override fun getViewModel(): Class<PesquisaViewModel> = PesquisaViewModel::class.java

    override fun getFragmenRepository(): NewsRepository =
        NewsRepository(ArtigoDataBase.invoke(requireContext()), RetrofitInstancia.api)

    override fun getFragmentBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPesquisarBinding =
        FragmentPesquisarBinding.inflate(inflater, container, false)

    override fun onInject() {
        setupRecyclerView()
        search()
        observeResults()
    }

    private fun search() = with(binding) {
        pesquisarNoticias.setOnQueryTextListener(UtilQueryTextListner(this@PesqusiaFragment.lifecycle) { newText ->
            newText?.let {query->
                if (query.isNotEmpty()){
                    viewModel.fetchSearch(query)
                }
            }
        }
        )
    }

    private fun setupRecyclerView() = with(binding) {
        rvPeesquisar.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
        mainAdapter.setOnClickListner { artigo ->
            val action =
                PesqusiaFragmentDirections.actionPesqusiaFragmentToWebViewFragment(artigo)
            findNavController().navigate(action)
        }
    }
    private fun observeResults() {
        viewModel.search.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is StateResource.Sucesso->{
                    binding.rvProgressBarTelaPesquisar.hide()
                    response.data?.let {data->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }
                }
                is StateResource.Error->{
                    binding.rvProgressBarTelaPesquisar.hide()
                    mensagem(requireContext(),"Ocorreu um Erro : ${response.message.toString()}")
                }
                is StateResource.Loanding->{
                    binding.rvProgressBarTelaPesquisar.show()
                }
            }
        })
    }

}
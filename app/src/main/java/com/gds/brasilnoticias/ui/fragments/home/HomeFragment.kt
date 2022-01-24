package com.gds.brasilnoticias.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gds.brasilnoticias.data.local.db.ArtigoDataBase
import com.gds.brasilnoticias.data.remote.RetrofitInstancia
import com.gds.brasilnoticias.databinding.FragmentHomeBinding
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.ui.adapter.MainAdapter
import com.gds.brasilnoticias.ui.fragments.base.BaseFragment
import com.gds.brasilnoticias.util.state.StateResource
import com.gds.brasilnoticias.util.hide
import com.gds.brasilnoticias.util.mensagem
import com.gds.brasilnoticias.util.show

class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>() {
    private val mainAdapter by lazy { MainAdapter() }



    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getFragmenRepository(): NewsRepository =
        NewsRepository(api = RetrofitInstancia.api, db = ArtigoDataBase.invoke(requireContext()))

    override fun getFragmentBind(inflater: LayoutInflater,container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater,container,false)

    override fun onInject() {
        setupRecyclerView()
        observeResults()
    }
    private fun observeResults() {
        viewModel.getAll.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is StateResource.Sucesso->{
                    binding.rvProgressBar.hide()
                    response.data?.let {data->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }
                }
                is StateResource.Error->{
                    binding.rvProgressBar.hide()
                    mensagem(requireContext(),"Ocorreu um Erro : ${response.message.toString()}")
                }
                is StateResource.Loanding->{
                    binding.rvProgressBar.show()
                }
            }
        })
    }

    private fun setupRecyclerView() = with(binding) {
        rvMoticias.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
            )
        }
        mainAdapter.setOnClickListner {artigo->
            val action =
                HomeFragmentDirections.actionHomeFragmentToWebViewFragment(artigo)
            findNavController().navigate(action)
        }
    }

}
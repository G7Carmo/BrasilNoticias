package com.gds.brasilnoticias.ui.fragments.webview

import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.gds.brasilnoticias.R
import com.gds.brasilnoticias.data.local.db.ArtigoDataBase
import com.gds.brasilnoticias.data.local.model.Artigo
import com.gds.brasilnoticias.data.remote.RetrofitInstancia
import com.gds.brasilnoticias.databinding.FragmentWebviewBinding
import com.gds.brasilnoticias.repository.NewsRepository
import com.gds.brasilnoticias.ui.fragments.base.BaseFragment
import com.gds.brasilnoticias.util.mensagem

class WebViewFragment : BaseFragment<WebViewViewModel,FragmentWebviewBinding>() {
    private val args : WebViewFragmentArgs by navArgs()
    private lateinit var artigo: Artigo

    override fun getViewModel(): Class<WebViewViewModel> = WebViewViewModel::class.java
    override fun getFragmenRepository(): NewsRepository =
        NewsRepository(ArtigoDataBase.invoke(requireContext()),RetrofitInstancia.api)

    override fun getFragmentBind(inflater: LayoutInflater,container: ViewGroup?): FragmentWebviewBinding =
        FragmentWebviewBinding.inflate(inflater,container,false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onInject() {
        artigo = args.artigo
        binding.webView.apply {
            webViewClient = WebViewClient()
            artigo.url?.let { url->
                loadUrl(url)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_artigo->{
                viewModel.salvarArtigo(artigo)
                mensagem(requireContext(),"artigo salvo com sucesso")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
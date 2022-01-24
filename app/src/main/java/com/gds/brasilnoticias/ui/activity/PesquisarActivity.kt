//package com.gds.brasilnoticias.ui.activity
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.viewbinding.ViewBinding
//import com.gds.brasilnoticias.ui.adapter.MainAdapter
//import com.gds.brasilnoticias.databinding.ActivityPesquisarBinding
//import com.gds.brasilnoticias.data.local.model.Artigo
//import com.gds.brasilnoticias.repository.NewsDataSource
//import com.gds.brasilnoticias.presenter.ViewHome
//import com.gds.brasilnoticias.presenter.pesquisar.PesquisarPresenter
//import com.gds.brasilnoticias.util.UtilQueryTextListner
//
//class PesquisarActivity : AbstractActivity(), ViewHome.View {
//
//    private val searchAdapter by lazy {
//        MainAdapter()
//    }
//    private lateinit var presenter: PesquisarPresenter
//    private lateinit var binding: ActivityPesquisarBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//    }
//
//    override fun getLayout(): ViewBinding {
//        binding = ActivityPesquisarBinding.inflate(layoutInflater)
//        return binding
//    }
//
//    override fun onInject() {
//        configPresenter()
//        configRecycler()
//        pesquisarNoticia()
//        clickAdapter()
//    }
//
//    private fun configPresenter() {
//        val fonteDeDados = NewsDataSource(this)
//        presenter = PesquisarPresenter(this, fonteDeDados)
//    }
//
//    fun pesquisarNoticia() {
//        binding.pesquisarNoticias
//            .setOnQueryTextListener(
//                UtilQueryTextListner(this.lifecycle) { newText ->
//                    newText?.let { query ->
//                        validandoQuery(query)
//                    }
//                }
//            )
//    }
//
//    private fun validandoQuery(query: String) {
//        if (query.isNotEmpty()) {
//            presenter.pesquisar(query)
//            binding.rvProgressBarTelaPesquisar.visibility = View.VISIBLE
//        }
//    }
//
//    private fun configRecycler() {
//        with(binding.rvPeesquisar) {
//            adapter = searchAdapter
//            layoutManager = LinearLayoutManager(this@PesquisarActivity)
//            addItemDecoration(
//                DividerItemDecoration(
//                    this@PesquisarActivity,
//                    DividerItemDecoration.VERTICAL
//                )
//            )
//
//        }
//
//    }
//
//    private fun clickAdapter() {
//        searchAdapter.setOnClickListner { artigo ->
//            val intent = Intent(this, ArtigoActivity::class.java)
//            intent.putExtra("artigo", artigo)
//            startActivity(intent)
//        }
//    }
//
//
//    override fun mostrarBarraDeProgresso() {
//        binding.rvProgressBarTelaPesquisar.visibility = View.VISIBLE
//    }
//
//    override fun showFalha(mensagem: String) {
//        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
//    }
//
//    override fun esconderBarraDeProgresso() {
//        binding.rvProgressBarTelaPesquisar.visibility = View.INVISIBLE
//    }
//
//    override fun mostrarArtigos(artigos: List<Artigo>) {
//        searchAdapter.differ.submitList(artigos.toList())
//    }
//
//}
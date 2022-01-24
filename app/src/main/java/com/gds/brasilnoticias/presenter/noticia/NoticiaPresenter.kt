package com.gds.brasilnoticias.presenter.noticia

import com.gds.brasilnoticias.model.RespostaDaNoticia
import com.gds.brasilnoticias.model.data.NewsDataSource
import com.gds.brasilnoticias.presenter.ViewHome

class NoticiaPresenter(
    val view: ViewHome.View,
    private val fonteDeDados: NewsDataSource
) : NoticiaInicial.Presenter {
    override fun requisitarTudo() {
        this.view.mostrarBarraDeProgresso()
        this.fonteDeDados.getPrincipaisNoticias(this,"br")
    }

    override fun sucesso(novaRespostaDaNoticia: RespostaDaNoticia) {
        this.view.mostrarArtigos(novaRespostaDaNoticia.articles)
    }

    override fun erro(mensagem: String) {
        this.view.showFalha(mensagem)
    }

    override fun completo() {
        this.view.esconderBarraDeProgresso()
    }
}
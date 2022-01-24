package com.gds.brasilnoticias.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gds.brasilnoticias.R
import com.gds.brasilnoticias.databinding.ItemNoticiaBinding
import com.gds.brasilnoticias.model.Artigo

class MainAdapter : RecyclerView.Adapter<MainAdapter.ArtigoViewHolder>() {
    inner class ArtigoViewHolder(val binding: ItemNoticiaBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Artigo>() {
        override fun areItemsTheSame(oldItem: Artigo, newItem: Artigo): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Artigo, newItem: Artigo): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtigoViewHolder =
        ArtigoViewHolder(
            ItemNoticiaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArtigoViewHolder, position: Int) {

        with(holder) {
            with(differ.currentList[position]) {
                Glide.with(holder.itemView.context).load(urlToImage).into(binding.ivArtigoImagem)
                binding.tvTitulo.text = author ?: source?.name
                binding.tvSource.text = source?.name ?: author
                binding.tvDescricao.text = description
                binding.tvDataDePublicacao.text = publishedAt

                holder.itemView.setOnClickListener {
                    onIntemClickListener?.let { click ->
                        click(this)
                    }
                }
            }
        }
    }

    private var onIntemClickListener: ((Artigo) -> Unit)? = null

    fun setOnClickListner(listner: (Artigo) -> Unit) {
        onIntemClickListener = listner
    }
}
package com.gds.brasilnoticias.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gds.brasilnoticias.repository.NewsRepository

abstract class BaseFragment<VM : ViewModel,VB : ViewBinding> : Fragment() {
    protected lateinit var binding : VB
    protected lateinit var viewModel : VM


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = getFragmentBind(inflater,container)

        val factory = ViewModelFactory(getFragmenRepository(), application = requireActivity().application)
        viewModel = ViewModelProvider(viewModelStore,factory).get(getViewModel())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInject()
    }

    abstract fun onInject()

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmenRepository(): NewsRepository

    abstract fun getFragmentBind(inflater: LayoutInflater, container: ViewGroup?): VB

}
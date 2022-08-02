package com.example.wowcart.presentation.components

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {

    private var _binding: VBinding? = null
    protected val binding get() = _binding!!

    abstract val viewModel: ViewModel

    abstract fun getViewBinding(): VBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFirebaseLog()
    }

    open fun showFirebaseLog() {
        val clazz = this::class.simpleName
        Log.d("Firebase", "Created: $clazz")
        Firebase.crashlytics.log("entered $clazz")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
package com.csibtn.fvmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.csibtn.fvmovies.databinding.ErrorDialogFragmentBinding

class ErrorDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ErrorDialogFragmentBinding.inflate(inflater, container, false)
        binding.btnOk.setOnClickListener{
            dismiss()
        }
        return binding.root
    }


}
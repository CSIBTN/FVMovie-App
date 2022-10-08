package com.csibtn.fvmovies.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.csibtn.fvmovies.databinding.TrailerWebViewFragmentBinding

class TrailerDialogFragment : DialogFragment() {
    private val args: TrailerDialogFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = TrailerWebViewFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            wvTrailer.apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(args.trailerUrl)
            }
        }
        return binding.root
    }
}
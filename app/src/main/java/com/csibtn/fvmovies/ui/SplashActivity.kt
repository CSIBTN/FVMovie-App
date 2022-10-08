package com.csibtn.fvmovies.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.csibtn.fvmovies.R
import com.csibtn.fvmovies.databinding.SplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var topAnim: Animation
    private lateinit var bottomAnim: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = SplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        binding.ivSplashIcon.animation = topAnim
        binding.tvAppTitle.animation = bottomAnim

        lifecycleScope.launch {
            delay(2000)
            Intent(this@SplashActivity, MainActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }
    }
}
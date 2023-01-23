package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isDownloadInProgress = false
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.progressBarTest.max = 100
        binding.progressBarTest.progress = 0

        binding.download.setOnClickListener {
            if (!isDownloadInProgress) {
                isDownloadInProgress = true
                simulateDownload()
            }
        }
    }

    private fun simulateDownload() {
        var currentProgress = 0
        job = CoroutineScope(Dispatchers.IO).launch {
            while (currentProgress < 100 && isActive) {
                currentProgress++
                withContext(Dispatchers.Main) {
                    binding.progressBarTest.progress = currentProgress
                }
                delay(50)
            }
            isDownloadInProgress = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
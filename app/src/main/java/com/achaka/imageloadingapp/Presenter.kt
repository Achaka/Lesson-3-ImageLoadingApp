package com.achaka.imageloadingapp


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Presenter(private val view: MainActivity) {

    private val scopeIO = CoroutineScope(Dispatchers.IO + Job())
    private val networkApi = Network.getNetworkApi()

    fun loadImageFromNetwork(url: String) {
        view.showPlaceholder()
        scopeIO.launch {
            val bitmap = networkApi.getImageFromNetwork(url)
            launch(Dispatchers.Main) {
                if (bitmap != null) {
                    view.showImage(bitmap)
                } else {
                    view.showError(R.drawable.ic_baseline_broken_image_24)
                    view.showErrorToast()
                }
            }
        }
    }
}

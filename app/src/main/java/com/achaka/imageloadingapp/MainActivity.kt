package com.achaka.imageloadingapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import com.achaka.imageloadingapp.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class MainActivity : AppCompatActivity() {

    private val requestListener = object : RequestListener<Drawable?> {
        override fun onLoadFailed(
            @Nullable e: GlideException?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable?>?,
            isFirstResource: Boolean
        ): Boolean {
            showErrorToast()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }
    }
    private val presenter = Presenter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val switch = binding.switchButton

        showPlaceholder()
        binding.image.setOnClickListener {
            loadImage(switch.isChecked)
        }
    }

    private fun loadImageGlide(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_image_24)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .error(R.drawable.ic_baseline_broken_image_24)
            .listener(requestListener)
            .into(binding.image)
    }

    fun showImage(bitmap: Bitmap) {
        binding.image.setImageBitmap(bitmap)
    }

    fun showError(drawableId: Int) {
        binding.image.setImageResource(drawableId)
    }

    fun showPlaceholder() {
        binding.image.setImageResource(R.drawable.ic_baseline_image_24)
    }

    fun showErrorToast() {
        Toast.makeText(this, getString(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    private fun loadImage(isChecked: Boolean) {
        if (isChecked) {
            loadImageGlide(binding.edittext.text.toString())
        } else {
            presenter.loadImageFromNetwork(binding.edittext.text.toString())
        }
    }

}

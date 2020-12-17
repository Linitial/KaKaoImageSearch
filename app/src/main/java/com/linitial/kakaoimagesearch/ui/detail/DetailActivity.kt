package com.linitial.kakaoimagesearch.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.linitial.kakaoimagesearch.R
import com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse.ImageInfo
import com.linitial.kakaoimagesearch.databinding.ActivityDetailBinding
import com.linitial.kakaoimagesearch.extension.gone
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL_ITEM = "EXTRA_DETAIL_ITEM"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding = ActivityDetailBinding.inflate(LayoutInflater.from(this)).apply {
            setContentView(root)
        }
    }

    private fun initData() {
        val item = intent?.getSerializableExtra(EXTRA_DETAIL_ITEM) as? ImageInfo
        item?.let {
            showImage(it.imageUrl)
            showDisplaySiteName(it.displaySiteName)
            showDateTime(it.dateTime)
        }
    }

    private fun showImage(imageUrl: String?) {
        imageUrl?.let {
            Glide.with(this)
                .load(it)
                .fitCenter()
                .into(binding.ivImage)
        } ?: binding.ivImage.gone()
    }

    private fun showDisplaySiteName(displaySiteName: String?) {
        displaySiteName?.let {
            val formatStr = String.format(getString(R.string.display_site_name), displaySiteName)
            binding.tvDisplaySiteName.text = formatStr
        } ?: binding.tvDisplaySiteName.gone()
    }

    private fun showDateTime(date: Date?) {
        date?.let {
            val formatDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(it)
            val formatStr = String.format(getString(R.string.writing_time), formatDate)
            binding.tvDateTime.text = formatStr
        } ?: binding.tvDateTime.gone()
    }

    override fun onBackPressed() {
        finish()
    }
}
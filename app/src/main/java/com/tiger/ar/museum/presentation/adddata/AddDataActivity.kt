package com.tiger.ar.museum.presentation.adddata

import androidx.activity.viewModels
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.databinding.AddDataActivityBinding

class AddDataActivity: MuseumActivity<AddDataActivityBinding>(R.layout.add_data_activity) {
    private val viewModel by viewModels<AddDataViewModel>()

    override fun onInitView() {
        super.onInitView()

        binding.btnAddData.setOnSafeClick {
            viewModel.addData()

            viewModel.addStreetViewList()

            viewModel.addStoryList()
        }
    }
}

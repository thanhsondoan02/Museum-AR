package com.tiger.ar.museum.presentation.download

import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.view.StatusBar
import com.tiger.ar.museum.databinding.DownloadActivityBinding
import com.tiger.ar.museum.presentation.camera.view3d.View3dActivity
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class DownloadActivity: MuseumActivity<DownloadActivityBinding>(R.layout.download_activity) {
    private val adapter by lazy { DownloadAdapter() }
    private val viewModel by viewModels<DownloadViewModel>()

    override fun setupStatusBar() = StatusBar(color = R.color.main_black, isDarkText = false)

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        initRecyclerView()
        viewModel.getDownloadedModel {
            binding.cvDownload.submitList(viewModel.dataList)
        }
    }

    private fun initOnClick() {
        binding.ivDownloadBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun initRecyclerView() {
        adapter.listener = object : DownloadAdapter.IListener {
            override fun onDelete(item: DownloadAdapter.DownloadItem) {
                val dialog = ConfirmDeleteDialog().apply {
                    name = item.name
                    size = item.size
                    onConfirmAction = {
                        viewModel.deleteModelFromInternalStorage(item.id) {
                            binding.cvDownload.submitList(viewModel.dataList)
                        }
                        dismiss()
                    }
                }
                dialog.show(supportFragmentManager, dialog::class.java.simpleName)
            }

            override fun onViewItem(id: String?) {
                navigateTo(View3dActivity::class.java, bundleOf(View3dActivity.ITEM_ID_KEY to id))
            }
        }

        binding.cvDownload.apply {
            setAdapter(this@DownloadActivity.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }
}

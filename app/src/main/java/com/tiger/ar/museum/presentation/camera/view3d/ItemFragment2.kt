package com.tiger.ar.museum.presentation.camera.view3d

import com.tiger.ar.museum.presentation.item.ItemFragment

class ItemFragment2: ItemFragment() {
    override fun onDestroy() {
        realMainActivity.finish()
        super.onDestroy()
    }
}

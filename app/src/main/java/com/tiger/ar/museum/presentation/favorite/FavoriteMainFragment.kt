package com.tiger.ar.museum.presentation.favorite

import androidx.fragment.app.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.FavoriteMainFragmentBinding
import com.tiger.ar.museum.domain.model.FavoriteData
import com.tiger.ar.museum.domain.model.Gallery
import com.tiger.ar.museum.presentation.widget.COLLECTION_MODE

class FavoriteMainFragment: MuseumFragment<FavoriteMainFragmentBinding>(R.layout.favorite_main_fragment) {
    private val viewModel by viewModels<FavoriteViewModel>()
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        initRecyclerView()
        getFavoriteData()
    }

    private fun initOnClick() {
    }

    private fun initRecyclerView() {
        adapter.listener = object : FavoriteAdapter.IListener {
            override fun onFavoriteTab() {
                binding.cvFavoriteMain.submitList(viewModel.listFavorite)
                getFavoriteData()
            }

            override fun onGalleriesTab() {
                binding.cvFavoriteMain.submitList(viewModel.listGallery)
            }

            override fun onViewAllItem() {

            }

            override fun onViewAllStory() {

            }

            override fun onViewAllCollection() {

            }

            override fun onMoreGallery(gallery: Gallery) {

            }
        }
        binding.cvFavoriteMain.apply {
            setAdapter(this@FavoriteMainFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
            submitList(viewModel.listFavorite)
        }
    }

    private fun getFavoriteData() {
        val ref = FirebaseDatabase.getInstance().getReference("Users/${AppPreferences.getUserInfo().key}/favorites")
//        val query = ref.orderByChild("email").equalTo(email).limitToFirst(1)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteData = snapshot.getValue(FavoriteData::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                toast(getAppString(R.string.fail) + ": ${error.message}")
            }
        })
    }
}

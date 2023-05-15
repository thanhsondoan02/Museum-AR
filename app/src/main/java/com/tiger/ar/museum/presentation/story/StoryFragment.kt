package com.tiger.ar.museum.presentation.story

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.toast
import com.tiger.ar.museum.databinding.StoryFragmentBinding
import com.tiger.ar.museum.domain.model.Story

class StoryFragment: MuseumFragment<StoryFragmentBinding>(R.layout.story_fragment) {
    companion object {
        const val STORY_ID_KEY = "story_id_key"
    }

    private val adapter by lazy { StoryAdapter() }
    private var storyId: String? = null

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        storyId = arguments?.getString(STORY_ID_KEY)
    }

    override fun onInitView() {
        super.onInitView()

        if (storyId == null) {
            toast("Story id is null")
            return
        }

        binding.vpStory.apply {
            adapter = this@StoryFragment.adapter
        }

        val storyRef = Firebase.firestore.collection("stories").document(storyId!!)
        storyRef.get().addOnSuccessListener {
            val story = it.toObject(Story::class.java)
            story?.pages?.let { pages ->
                adapter.submitList(pages)
            }
        }
    }
}

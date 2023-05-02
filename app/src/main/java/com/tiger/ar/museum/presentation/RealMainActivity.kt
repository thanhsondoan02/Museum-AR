package com.tiger.ar.museum.presentation

import androidx.viewpager.widget.ViewPager
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.FontSpan
import com.tiger.ar.museum.common.SpannableBuilder
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.*
import com.tiger.ar.museum.common.view.StatusBar
import com.tiger.ar.museum.databinding.RealMainActivityBinding
import com.tiger.ar.museum.presentation.camera.view3d.View3dActivity
import com.tiger.ar.museum.presentation.explore.ExploreFragment
import com.tiger.ar.museum.presentation.favorite.FavoriteMainFragment
import com.tiger.ar.museum.presentation.game.GameFragment
import com.tiger.ar.museum.presentation.home.HomeFragment
import com.tiger.ar.museum.presentation.profile.ProfileDialog
import com.tiger.ar.museum.presentation.setting.SettingFragment

class RealMainActivity : MuseumActivity<RealMainActivityBinding>(R.layout.real_main_activity) {

    private val pagerAdapter by lazy { MainViewPagerAdapter(supportFragmentManager) }
    private val fragmentList = mutableListOf<MuseumFragment<*>>()
    private val homeFragment by lazy { HomeFragment() }
    private val exploreFragment by lazy { ExploreFragment() }
    private val favoriteMainFragment by lazy { FavoriteMainFragment() }
    private val gameFragment by lazy { GameFragment() }
    private var settingFragment: SettingFragment? = null

    override fun setupStatusBar() = StatusBar(color = R.color.main_black, isDarkText = false)

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        setSpanTitle()
        initBottomNav()
        initViewPager()
        loadAvatar()
    }

    fun setBackIcon() {
        binding.ivRealMainSearch.setImageResource(R.drawable.ic_back)
    }

    fun setSearchIcon() {
        binding.ivRealMainSearch.setImageResource(R.drawable.ic_search)
    }

    fun reloadFavorite() {
        favoriteMainFragment.getFavoriteData()
    }

    private fun initOnClick() {
        binding.ivRealMainCast.setOnSafeClick {

        }

        binding.ivRealMainSearch.setOnSafeClick {
        }

        binding.ivRealMainProfile.setOnSafeClick {
            val profileDialog = ProfileDialog()
            profileDialog.listener = object : ProfileDialog.IListener {
                override fun onSetting() {
                    profileDialog.dismiss()
                    supportFragmentManager.fragments.let {
                        if (it[it.size - 2] is SettingFragment) {
                            settingFragment?.scrollToTop()
                        } else {
                            settingFragment = SettingFragment()
                            addFragmentNew(settingFragment!!, containerId = R.id.flRealMainContainer)
                        }
                    }

                }
            }
            profileDialog.show(supportFragmentManager, profileDialog::class.java.simpleName)
        }

        binding.fabRealMainCamera.setOnSafeClick {
            navigateTo(View3dActivity::class.java)
        }
    }

    private fun setSpanTitle() {
        binding.tvRealMainTitle.text = SpannableBuilder(getAppString(R.string.tiger) + " ")
            .withSpan(FontSpan(getAppFont(R.font.roboto_bold)))
            .appendText(getAppString(R.string.app_name))
            .withSpan(FontSpan(getAppFont(R.font.roboto_regular)))
            .spannedText
    }

    private fun initBottomNav() {
        binding.bnvRealMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.iBottomNavHome -> {
                    binding.vpRealMain.setCurrentItem(0, false)
                    true
                }
                R.id.iBottomNavExplore -> {
                    binding.vpRealMain.setCurrentItem(1, false)
                    true
                }
                R.id.iBottomNavFavorite -> {
                    binding.vpRealMain.setCurrentItem(2, false)
                    true
                }
                R.id.iBottomNavGame -> {
                    binding.vpRealMain.setCurrentItem(3, false)
                    true
                }
                else -> throw IllegalArgumentException("Invalid item id")
            }
        }
    }

    private fun initViewPager() {
        fragmentList.add(homeFragment)
        fragmentList.add(exploreFragment)
        fragmentList.add(favoriteMainFragment)
        fragmentList.add(gameFragment)
        pagerAdapter.addListFragment(fragmentList)

        binding.vpRealMain.apply {
            setPagingEnabled(false)
            adapter = pagerAdapter
            offscreenPageLimit = pagerAdapter.count
            currentItem = 0
        }

        binding.vpRealMain.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            }
        )
    }

    private fun loadAvatar() {
        binding.ivRealMainProfile.loadImage(AppPreferences.getUserInfo().avatar, placeHolder = getAppDrawable(R.drawable.ic_no_picture))
    }
}

package com.tiger.ar.museum.presentation

import android.view.ViewOutlineProvider
import androidx.core.view.updateLayoutParams
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.material.appbar.AppBarLayout
import com.tiger.ar.museum.AppPreferences
import com.tiger.ar.museum.R
import com.tiger.ar.museum.common.FontSpan
import com.tiger.ar.museum.common.SpannableBuilder
import com.tiger.ar.museum.common.binding.MuseumActivity
import com.tiger.ar.museum.common.binding.MuseumFragment
import com.tiger.ar.museum.common.extension.getAppColor
import com.tiger.ar.museum.common.extension.getAppDrawable
import com.tiger.ar.museum.common.extension.getAppFont
import com.tiger.ar.museum.common.extension.getAppString
import com.tiger.ar.museum.common.extension.loadImage
import com.tiger.ar.museum.common.extension.setOnSafeClick
import com.tiger.ar.museum.common.extension.toastUndeveloped
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
    private val listAppBarCallBack = mutableListOf<(appbarLayOut: AppBarLayout, verticalOffset: Int) -> Unit>()
    private var isBackIcon = false

    override fun setupStatusBar() = StatusBar(color = R.color.main_black, isDarkText = false)

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        setSpanTitle()
        initBottomNav()
        initViewPager()
        loadAvatar()
        initTopAppBarCallback()
    }

    fun setBackIcon() {
        isBackIcon = true
        binding.ivRealMainSearch.apply {
            setImageResource(R.drawable.ic_back_main_black)
            setOnSafeClick {
                navigateBack()
                if (supportFragmentManager.fragments.size == 4 ||
                    supportFragmentManager.fragments.lastOrNull() is SupportStreetViewPanoramaFragment
                ) {
                    setSearchIcon()
                }
            }
        }
    }

    fun setSearchIcon() {
        isBackIcon = false
        binding.ivRealMainSearch.apply {
            setImageResource(R.drawable.ic_search_main_black)
            setOnSafeClick {
//                navigateTo(SearchActivity::class.java)
                toastUndeveloped()
            }
        }
    }

    fun setTransparentActionBar() {
        binding.apply {
            constRealMainActionBar.setBackgroundColor(getAppColor(R.color.transparent))
            ablRealMain.outlineProvider = null
            tvRealMainTitle.setTextColor(getAppColor(R.color.white))
            if (isBackIcon) {
                ivRealMainSearch.setImageResource(R.drawable.ic_back_white)
            } else {
                ivRealMainSearch.setImageResource(R.drawable.ic_search_white)
            }
            ivRealMainCast.setImageResource(R.drawable.ic_cast_white)
        }
    }

    fun setWhiteActionBar() {
        binding.apply {
            constRealMainActionBar.setBackgroundColor(getAppColor(R.color.white))
            ablRealMain.outlineProvider = ViewOutlineProvider.BOUNDS
            tvRealMainTitle.setTextColor(getAppColor(R.color.main_black))
            if (isBackIcon) {
                ivRealMainSearch.setImageResource(R.drawable.ic_back_main_black)
            } else {
                ivRealMainSearch.setImageResource(R.drawable.ic_search_main_black)
            }
            ivRealMainCast.setImageResource(R.drawable.ic_cast_main_black)
        }

    }

    fun enableScrollHideActionBar(isEnabled: Boolean) {
        binding.constRealMainActionBar.updateLayoutParams {
            (this as AppBarLayout.LayoutParams).scrollFlags = if (isEnabled) {
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or
                        AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
            } else {
                binding.ablRealMain.setExpanded(true, true)
                0
            }
        }

    }

    fun reloadFavorite() {
        favoriteMainFragment.getFavoriteData()
    }

    fun addCallBackOnOffSetChange(callBack: (appbarLayOut: AppBarLayout, verticalOffset: Int) -> Unit) {
        listAppBarCallBack.add(callBack)
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

    private fun initTopAppBarCallback() {
        binding.ablRealMain.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
//            // Xử lý sự kiện thay đổi vị trí của AppBarLayout
//            if (verticalOffset == 0) {
//                // AppBarLayout hiển thị hoàn toàn
//                // ...
//            } else if (Math.abs(verticalOffset) >= appBarLayout?.totalScrollRange ?: 0) {
//                // AppBarLayout bị ẩn hoàn toàn
//                // ...
//            } else {
//                // AppBarLayout đang ở trạng thái trung gian
//                // ...
//            }
            listAppBarCallBack.forEach {
                it.invoke(appBarLayout, verticalOffset)
            }
        }
    }
}

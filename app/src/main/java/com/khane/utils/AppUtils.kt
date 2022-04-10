package com.khane.utils

object AppUtils {

    /*fun setSelectedTab(context: Context, tabLayout: TabLayout, selectedTab: Int) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        var vgTab: ViewGroup
        var tabChildCount: Int
        var tabViewChild: View

        for (j in 0 until tabsCount) {
            if (j != selectedTab) {
                vgTab = vg.getChildAt(selectedTab) as ViewGroup // Particular TabView
                tabChildCount = vgTab.childCount //Each tab has 2 children, ImageView and TextView
                for (i in 0 until tabChildCount) {
                    tabViewChild = vgTab.getChildAt(i)
                    if (tabViewChild is TextView) {
                        tabViewChild.gravity = Gravity.CENTER
                        tabViewChild.isAllCaps = false
                        tabViewChild.letterSpacing = 0f
                        tabViewChild.isSingleLine = true
                        tabViewChild.typeface =
                            ResourcesCompat.getFont(context, R.font.sf_pro_display_semibold)
                        tabViewChild.setTextColor(context.getColor(R.color.colorYellowF7))
                    }
                }
            }
        }
    }

    fun setUnSelectedTab(context: Context, tabLayout: TabLayout, unSelectedTab: Int) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        var vgTab: ViewGroup
        var tabChildCount: Int
        var tabViewChild: View

        for (j in 0 until tabsCount) {
            if (j == unSelectedTab) {
                vgTab = vg.getChildAt(unSelectedTab) as ViewGroup // Particular TabView
                tabChildCount = vgTab.childCount //Each tab has 2 children, ImageView and TextView
                for (i in 0 until tabChildCount) {
                    tabViewChild = vgTab.getChildAt(i)
                    if (tabViewChild is TextView) {
                        tabViewChild.gravity = Gravity.CENTER
                        tabViewChild.isAllCaps = false
                        tabViewChild.letterSpacing = 0f
                        tabViewChild.isSingleLine = true
                        tabViewChild.setTypeface(
                            ResourcesCompat.getFont(context, R.font.sf_pro_display_semibold)
                        )
                        tabViewChild.setTextColor(context.getColor(R.color.colorGrey3C))
                    }
                }
            }
        }
    }*/

}
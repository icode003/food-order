package com.khane.ui.manager

import android.content.Intent
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.core.util.Pair
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.khane.ui.base.BaseFragment


@UiThread
interface ActivityBuilder {

    fun start()

    fun addBundle(bundle: Bundle): ActivityBuilder

    fun addSharedElements(pairs: List<Pair<View, String>>): ActivityBuilder

    fun byFinishingCurrent(): ActivityBuilder

    fun byFinishingAll(): ActivityBuilder

    fun <T : BaseFragment<*>> setPage(page: Class<T>): ActivityBuilder

    // Bhargav Savasani : 01-09-2021 for resultLauncher
    /*var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        // There are no request codes
        val data: Intent? = result.data
        doSomeOperations()
    }
}*/
    fun forResult(resultLauncher: ActivityResultLauncher<Intent>): ActivityBuilder

    fun shouldAnimate(isAnimate: Boolean): ActivityBuilder


}

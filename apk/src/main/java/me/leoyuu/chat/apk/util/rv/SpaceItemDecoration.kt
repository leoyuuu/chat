package me.leoyuu.chat.apk.util.rv


import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import me.leoyuu.chat.apk.util.dp2px

/**
 * date 2018/5/9
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class SpaceItemDecoration(topDp: Int, rightDp: Int, bottomDp: Int, leftDp: Int) : RecyclerView.ItemDecoration() {

    private val rect = Rect()

    constructor(space: Int) : this(space, space, space, space)

    init {
        rect.top = dp2px(topDp)
        rect.right = dp2px(rightDp)
        rect.bottom = dp2px(bottomDp)
        rect.left = dp2px(leftDp)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.top = rect.top
        outRect.left = rect.left
        outRect.right = rect.right
        outRect.bottom = rect.bottom
    }
}
package com.bytedance.tiktok.view.viewpagerlayoutmanager

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View

import ltd.android.coriander_video.view.viewpagerlayoutmanager.OnViewPagerListener

/**
 * create on 2018/11/23
 * description  ViewPager页面切换类型LayoutManager，监听了item的进入和退出并回调
 */
class ViewPagerLayoutManager : LinearLayoutManager {
    private var mPagerSnapHelper: PagerSnapHelper? = null
    private var mOnViewPagerListener: OnViewPagerListener? = null
    private var mRecyclerView: RecyclerView? = null

    /**
     * 位移，用来判断移动方向
     */
    private var mDrift = 0

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
        init()
    }

    private fun init() {
        mPagerSnapHelper = PagerSnapHelper()
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        mPagerSnapHelper!!.attachToRecyclerView(view)
        this.mRecyclerView = view
        mRecyclerView!!.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
    }

    /**
     * 滑动状态的改变
     *
     * @param state
     */
    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                val viewIdle = mPagerSnapHelper!!.findSnapView(this) ?: return
                val positionIdle = getPosition(viewIdle)
                if (mOnViewPagerListener != null && childCount == 1) {
                    mOnViewPagerListener!!.onPageSelected(
                        positionIdle,
                        positionIdle == itemCount - 1
                    )
                }
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                val viewDrag = mPagerSnapHelper!!.findSnapView(this)
                if (viewDrag != null) {
                    val positionDrag = getPosition(viewDrag)
                }
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
                val viewSettling = mPagerSnapHelper!!.findSnapView(this)
                if (viewSettling != null) {
                    val positionSettling = getPosition(viewSettling)
                }
            }
        }
    }

    /**
     * 监听竖直方向的相对偏移量
     *
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        mDrift = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    /**
     * 监听水平方向的相对偏移量
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        mDrift = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    fun setOnViewPagerListener(listener: OnViewPagerListener?) {
        mOnViewPagerListener = listener
    }

    private val mChildAttachStateChangeListener: RecyclerView.OnChildAttachStateChangeListener =
        object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (mOnViewPagerListener != null && childCount == 1) {
                    mOnViewPagerListener!!.onInitComplete()
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                if (mDrift >= 0) {
                    if (mOnViewPagerListener != null) mOnViewPagerListener!!.onPageRelease(
                        true,
                        getPosition(view)
                    )
                } else {
                    if (mOnViewPagerListener != null) mOnViewPagerListener!!.onPageRelease(
                        false,
                        getPosition(view)
                    )
                }
                mOnViewPagerListener!!.onPageRelease(true, getPosition(view))
            }
        }
}
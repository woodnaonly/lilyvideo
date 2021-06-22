package ltd.android.coriander_video.activity

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.util.SparseIntArray
import android.view.MenuItem
import android.view.View
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_sys_note.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.view_pager_adapter.FragmentAdapter
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.fragment.Fragment1
import ltd.android.coriander_video.fragment.Fragment2
import ltd.android.coriander_video.fragment.Fragment3
import ltd.android.coriander_video.fragment.Fragment4
import ltd.android.coriander_video.net.http.api.NoticeAPi
import ltd.android.coriander_video.utils.GsonUtils
import ltd.android.coriander_video.utils.ObjectBox
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.util.*

//class MainActivity : AppCompatActivity(),CoroutineScope {
class MainActivity : BaseActivity<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    lateinit var mFragmentAdapter: FragmentAdapter

//    val mFragment1 by lazy { Fragment1.newInstance() }
//    val mFragment2 by lazy { Fragment2.newInstance() }
//    val mFragment3 by lazy { Fragment3.newInstance() }
//    val mFragment4 by lazy { Fragment4.newInstance() }

    val mFragments = ArrayList<Fragment>()

    val itemsId = SparseIntArray(3)


    override fun initView() {
        super.initView()

        test()


        itemsId.put(R.id.i_tab1, 0)
        itemsId.put(R.id.i_tab2, 1)
        itemsId.put(R.id.i_tab3, 2)
        itemsId.put(R.id.i_tab4, 3)


//        mFragments.add(mFragment1)
//        mFragments.add(mFragment2)
//        mFragments.add(mFragment3)
//        mFragments.add(mFragment4)

        mFragmentAdapter = FragmentAdapter(supportFragmentManager, mFragments)

        mViewPager.adapter = mFragmentAdapter
        mViewPager.offscreenPageLimit = mFragments.size

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBottomNavigationViewEx.currentItem = position
            }

        })


        initBottomNavigationViewEx(mBottomNavigationViewEx)
        initSystemNotice()

    }

    private fun initSystemNotice() {
        launch(Dispatchers.Main) {
            try {
                val noticeResponse = withContext(Dispatchers.IO)
                {
                    NoticeAPi.instance.getAdAsync().await()
                }
                if (noticeResponse.success) {
                    val data = noticeResponse.data
                    sys_note_title_t.text = data.title
                    sys_note_content_t.loadDataWithBaseURL(null, data.content, "text/html", "UTF-8", null)
                    sys_note_info_t.setOnClickListener {
                        mRootSystemView.visibility = View.GONE
                        mContentView.isEnabled = true
                    }
                    mContentView.isEnabled = false
                    mRootSystemView.visibility = View.VISIBLE
                    //设置点击事件，防止事件穿透
                    mRootSystemView.setOnClickListener { }
                }
            } catch (e: Exception) {
            }

        }
    }

    private fun test() {
        val mBox: Box<MovieDTO> = ObjectBox.boxStore.boxFor(MovieDTO::class.java)
        val query = mBox.query().build()
        val list = query.find()
        list.forEach {
            Log.d("=====test", GsonUtils.GsonString(it))
        }

    }


    private fun initBottomNavigationViewEx(bottomNavigationViewEx: BottomNavigationViewEx) {

        bottomNavigationViewEx.onNavigationItemSelectedListener =
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                private var previousPosition = -1
                override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                    val position = itemsId.get(p0.itemId)
                    if (previousPosition != position) {
                        // only set item when item changed
                        previousPosition = position
                        mViewPager.currentItem = position
                    }
                    return true
                }

            }
        bottomNavigationViewExDisableAllAnimation(bottomNavigationViewEx)
    }

    private fun bottomNavigationViewExDisableAllAnimation(bottomNavigationViewEx: BottomNavigationViewEx) {
        bottomNavigationViewEx.enableAnimation(false)
        bottomNavigationViewEx.enableShiftingMode(false)
        bottomNavigationViewEx.enableItemShiftingMode(false)
    }

    override fun onBackPressed() {
//        if (mFragment3.onBackPressed()) {
//            return
//        }
        finish()
    }
}



package ltd.android.coriander_video.activity

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.util.SparseIntArray
import android.view.MenuItem
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_main.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.view_pager_adapter.FragmentAdapter
import ltd.android.coriander_video.fragment.Fragment1
import ltd.android.coriander_video.fragment.Fragment2
import ltd.android.coriander_video.fragment.Fragment3
import ltd.android.coriander_video.fragment.Fragment4
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.util.*

//class MainActivity : AppCompatActivity(),CoroutineScope {
class SystemNoteActivity : BaseActivity<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.actvity_sys_note
    }

    lateinit var mFragmentAdapter: FragmentAdapter

    val mFragment1 by lazy { Fragment1.newInstance() }
    val mFragment2 by lazy { Fragment2.newInstance() }
    val mFragment3 by lazy { Fragment3.newInstance() }
    val mFragment4 by lazy { Fragment4.newInstance() }

    val mFragments = ArrayList<androidx.fragment.app.Fragment>()

    val itemsId = SparseIntArray(3)


    override fun initView() {
        super.initView()

        test()


        itemsId.put(R.id.i_tab1, 0)
        itemsId.put(R.id.i_tab2, 1)
        itemsId.put(R.id.i_tab3, 2)
        itemsId.put(R.id.i_tab4, 3)


        mFragments.add(mFragment1)
        mFragments.add(mFragment2)
        mFragments.add(mFragment3)
        mFragments.add(mFragment4)

        mFragmentAdapter = FragmentAdapter(supportFragmentManager, mFragments)

        mViewPager.adapter = mFragmentAdapter
        mViewPager.offscreenPageLimit = mFragments.size

        mViewPager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBottomNavigationViewEx.currentItem = position
            }

        })


        initBottomNavigationViewEx(mBottomNavigationViewEx)

    }

    private fun test() {


    }

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val map = mapOf("location" to "1")
//
//
//        CoroutineScope(Dispatchers.Main).launch {
//            val await = AdAPi.instance.GetAd(map).await()
//            Log.d("=====", await.code.toString())
//            Log.d("=====", await.data.size.toString())
//            Log.d("=====", Thread.currentThread().name)
//
//        }
//        disableAllAnimation(mBottomNavigationViewEx)
//
//    }

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
        if (mFragment3.onBackPressed()) {
            return
        }
        finish()
    }
}



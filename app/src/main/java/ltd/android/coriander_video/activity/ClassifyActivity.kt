package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import com.google.android.material.tabs.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_classify.*
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.view_pager_adapter.FragmentAdapter
import ltd.android.coriander_video.entity.MovieClass
import ltd.android.coriander_video.fragment.classify_grid.RecyclerClassifyGridFragment
import ltd.android.coriander_video.net.http.api.HomeAPi
import ltd.android.coriander_video.view_model.base.BaseViewModel

class ClassifyActivity : BaseActivity<BaseViewModel>() {

    companion object {
        private const val topPositionName = "topPosition"
        private const val BottomPositionName = "bottomPosition"

        fun start(context: Context?, topPosition: Int, bottomPosition: Int) {
            val intent = Intent(context, ClassifyActivity::class.java)
            intent.putExtra(topPositionName, topPosition)
            intent.putExtra(BottomPositionName, bottomPosition)
            context?.startActivity(intent)
        }
    }

    val mListFragment = ArrayList<RecyclerClassifyGridFragment>()
    val mListBottom = ArrayList<String>()

    override fun layoutId(): Int {
        return R.layout.activity_classify
    }


    override fun initView() {
        super.initView()
        val intent = intent
        val topPosition = intent.getIntExtra(topPositionName, 0)
        val bottomPosition = intent.getIntExtra(BottomPositionName, 0)


        classify_search.setOnClickListener {
            SearchActivity.start(it.context)
        }
        tabLayout_top.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout_bottom.tabMode = TabLayout.MODE_SCROLLABLE
        val arrayListTop = arrayListOf("综合", "最多播放", "最近更新", "最多喜欢")
        arrayListTop.forEach {
            val tab = tabLayout_top.newTab()
            tab.customView = getTabView(it)
            tabLayout_top.addTab(tab)
        }



        launch {
            val baseResponse =
                withContext(Dispatchers.IO) { HomeAPi.instance.getMovieClassAsync() }.await()
            val fragmentAdapter = FragmentAdapter(supportFragmentManager, mListFragment)
            view_pager.adapter = fragmentAdapter
            tabLayout_bottom.setupWithViewPager(view_pager)
            if (baseResponse.success) {
                baseResponse.data.add(0, MovieClass.getMovieClassAll())
                baseResponse.data.forEach {
                    val tab = tabLayout_bottom.newTab()
                    tab.customView = getTabView(it.clsName)
                    tabLayout_bottom.addTab(tab)
                    fragmentAdapter.title.add(it.clsName)
                    mListFragment.add(RecyclerClassifyGridFragment.newInstance(it.id))
                }
                fragmentAdapter.notifyDataSetChanged()
                tabLayout_bottom.removeAllTabs()
                baseResponse.data.forEach {
                    val tab = tabLayout_bottom.newTab()
                    tab.customView = getTabView(it.clsName)
                    tabLayout_bottom.addTab(tab)
                    mListBottom.add(it.clsName)
                }

                tabLayout_top.post {
                    val tab1 = tabLayout_top.getTabAt(topPosition)
                    tab1?.select()

                    val tab2 = tabLayout_bottom.getTabAt(bottomPosition)
                    tab2?.select()
                }

            }


        }


        tv_tablayout_top.text = "综合"
        tabLayout_top.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                val currentfragment = mListFragment[view_pager.currentItem]
                when (position) {
                    0 -> {
                        mListFragment.forEach {
                            it.sort = ""
                        }
                        tv_tablayout_top.text = "综合"

                        currentfragment.Refresh()
                    }
                    1 -> {
                        mListFragment.forEach {
                            it.sort = "play_count"
                        }
                        currentfragment.Refresh()
                        tv_tablayout_top.text = "最多播放"
                    }
                    2 -> {
                        mListFragment.forEach {
                            it.sort = "love_cnt"
                        }
                        currentfragment.Refresh()
                        tv_tablayout_top.text = "最近更新"
                    }
                    3 -> {
                        mListFragment.forEach {
                            it.sort = "id"
                        }
                        tv_tablayout_top.text = "最多喜欢"
                        currentfragment.Refresh()
                    }

                }
            }

        })
        tabLayout_bottom.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                if (mListBottom.size == tabLayout_bottom.tabCount && mListBottom.size != 0) {
                    tv_tablayout_bottom.text = mListBottom[position!!]
                }
            }

        })


    }

    fun getTabView(str: String): View {
        val inflate = LayoutInflater.from(this).inflate(R.layout.classify_textview, null)
        (inflate.findViewById(R.id.tab_item) as TextView).text = str
        return inflate
    }

}



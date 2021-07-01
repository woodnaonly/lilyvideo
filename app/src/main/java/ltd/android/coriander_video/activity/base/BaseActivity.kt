package ltd.android.coriander_video.activity.base

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.jaeger.library.StatusBarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import ltd.android.coriander_video.R
import ltd.android.coriander_video.utils.ToastUtil
import ltd.android.coriander_video.view_model.base.BaseViewModel
import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), CoroutineScope {

    protected var mViewModel: VM? = null
    protected val toastUtil by lazy { ToastUtil.getInstance() }

    override val coroutineContext: CoroutineContext =
        Dispatchers.Main + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        prepareBeforeInitView()
        setToolbar()
        initView()
        initVM()
        startObserve()
        initStatusBar()
        if (isApplyEventBus()) {
            registerEventBus()
        }
    }


    private fun initStatusBar() {
        if (isStatusBar()) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0)
        }
    }

    protected fun isStatusBar(): Boolean {
        return true
    }




    private fun setToolbar() {
        findViewById<View>(R.id.classify_back)?.setOnClickListener {
            finish()
        }

        providerToolBar().let { setSupportActionBar(it) }
    }

    /**
     * 布局文件id
     */
    abstract fun layoutId(): Int

    open fun prepareBeforeInitView() {}
    open fun initView() {}
    open fun startObserve() {}

    private fun initVM() {
        providerVMClass()?.let { it ->
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel!!)
        }
    }

    /**
     *设置[Toolbar]
     */
    open fun providerToolBar(): Toolbar? = null

    /**
     * [BaseViewModel]的实现类
     */
    open fun providerVMClass(): Class<VM>? = null


    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
        }
        coroutineContext[Job.Key]?.cancel()
        if (isApplyEventBus()) {
            unregisterEventBus()
        }
        super.onDestroy()
    }

    protected open fun isApplyEventBus(): Boolean {
        return false
    }

    private fun registerEventBus() {
        EventBus.getDefault().register(this)
    }

    private fun unregisterEventBus() {
        EventBus.getDefault().unregister(this)
    }
}
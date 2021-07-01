package ltd.android.coriander_video.fragment.base

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import ltd.android.coriander_video.utils.ToastUtil
import ltd.android.coriander_video.view_model.base.BaseViewModel
import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext


abstract class BaseFragment<VM : BaseViewModel> : androidx.fragment.app.Fragment(), CoroutineScope {

    protected var mViewModel: VM? = null
    protected lateinit var mRootView: View
    protected val toastUtil by lazy { ToastUtil.getInstance() }

    override val coroutineContext: CoroutineContext =
        Dispatchers.Main + SupervisorJob()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareBeforeInitView()
        setToolbar()
        initVM()
        initView()
        startObserve()
        if (isApplyEventBus()) {
            registerEventBus()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = View.inflate(context, layoutId(), null)
        return mRootView
    }

    private fun setToolbar() {
//        providerToolBar().let { setSupportActionBar(it) }
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
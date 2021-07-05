package ltd.android.coriander_video.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import ltd.android.coriander_video.R;
import ltd.android.coriander_video.utils.DisplayMetricsUtils;

/**
 * @author by 黄梦 on 2021/7/3.
 */

public class CommentDialog extends Dialog implements
        android.view.View.OnClickListener {

    public interface OnSendListener {
        void sendComment(String content);
    }

    private Context mContext;
    private EditText mEdittext;
    private TextView mTvCancel;
    private TextView mTvSend;
    private OnSendListener onSendListener;

    public void setOnSendListener(OnSendListener onSendListener) {
        this.onSendListener = onSendListener;
    }

    public CommentDialog(Context context) {
        super(context, R.style.stlyle_dialog_transparent_bg);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = (ViewGroup) View.inflate(mContext,
                R.layout.comment_view, null);

        initView(rootView);
        setContentView(rootView);
        setLayout();
        setOnShowListener(new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
//                mEdittext.setFocusableInTouchMode(true);
//                mEdittext.requestFocus();
//                InputMethodManager inputManager = (InputMethodManager) mEdittext
//                        .getContext().getSystemService(
//                                Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(mEdittext,
//                        InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void initView(View v) {
//        mEdittext = (EditText) v.findViewById(R.id.et_comment);
//        mTvCancel = (TextView) v.findViewById(R.id.tv_cancel);
        mTvSend = (TextView) v.findViewById(R.id.tv_send);
//        mTvCancel.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
    }

    private void setLayout() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        p.height = (int) DisplayMetricsUtils.dp2px(400);
        getWindow().setAttributes(p);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_cancel:
//                dismiss();
//                break;
            case R.id.tv_send:
//                String content = mEdittext.getText().toString().trim();
//                if (TextUtils.isEmpty(content)) {
//                    UIHelper.ToastMessage(AppContext.getInstance(), "请输入评论");
//                    return;
//                }

//                if (onSendListener != null) {
//                    onSendListener.sendComment(content);
//                }
                break;
            default:
                break;
        }
    }
}

package ltd.android.coriander_video.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author by 梁馨 on 2018/3/1.
 */

public class TextViewTimeCountUtils1 extends CountDownTimer {

  TextView mTextView;

  public TextViewTimeCountUtils1(TextView mTextView) {
    super(60_000, 1000);
    this.mTextView = mTextView;
  }

  public TextViewTimeCountUtils1(TextView textView, long millisInFuture, long countDownInterval) {
    super(millisInFuture, countDownInterval);
    mTextView = textView;
  }

  //mBtnGetCode.setText("重新获取(" + millisUntilFinished / 1000 + "s)");
  @Override
  public void onTick(long millisUntilFinished) {
    mTextView.setText(millisUntilFinished / 1000 + "s");
    mTextView.setClickable(false);
    mTextView.setEnabled(false);
  }

  @Override
  public void onFinish() {
    mTextView.setText("重新验证");
    mTextView.setClickable(true);
    mTextView.setEnabled(true);

  }

}

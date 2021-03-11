package ltd.android.coriander_video.adapter.fragment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import ltd.android.coriander_video.R;
import ltd.android.coriander_video.entity.Ad;
import ltd.android.coriander_video.net.http.apiImp.MemberImp;
import ltd.android.coriander_video.utils.IntentUtils;
import ltd.android.coriander_video.utils.glide.GlideUtils;

/**
 * @author by 梁馨 on 2019/3/9.
 */
public class BannerViewHolder implements MZViewHolder<Ad> {
    private ImageView banner_image;

    @Override
    public View createView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.fragment1_recyclerview_item_banner_item, null);
        this.banner_image = inflate.findViewById(R.id.banner_image);
        return inflate;
    }

    @Override
    public void onBind(Context context, int i, Ad ad) {
        GlideUtils.loadImg(banner_image, ad.getThumbnail());
        banner_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.gotoUrl(context, ad.getLinkAddr());
                MemberImp.INSTANCE.getUserInfo(new Function2<CoroutineScope, Continuation<? super Unit>, Object>() {
                    @Override
                    public Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return new Object();
                    }
                });
            }
        });
    }
}

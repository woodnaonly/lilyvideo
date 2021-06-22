package ltd.android.coriander_video.dto;

import android.text.TextUtils;
import ltd.android.coriander_video.R;
import ltd.android.coriander_video.dto.promotion.PromotionBase;

/**
 * @author by 黄梦 on 2019/3/24.
 */
public class UserDTO {
    public String registerTime;

    public long id;
    public int dailyDownloadNum;
    public int dailyViewNum;
    public int gender;
    public String gmtCreate;
    public String header;
    public int inviteCnt;
    public int leftDownloadNum;
    public int leftViewNum;
    public int level;
    public String myInviteCode;
    public String name;
    public int nextLevelNeed;
    public String phone;
    public int supUserId;
    public int usedDownloadNum;
    public int usedViewNum;
    //分享链接
    public PromotionBase link;
    //分享群
    public PromotionBase group;


    public String getName() {
        if (isVisit())
            return "游客";
        else {
            if (TextUtils.isEmpty(name)) {
                return phone.substring(0, 3) + "****" + phone.substring(7);
            }
        }
        return name;
    }


    public String getLevelExplain(int level) {
        String explain = "入门";
        switch (level) {
            case 0: {
                explain = "L0小白";
                break;
            }
            case 1: {
                explain = "L1入门";
                break;
            }
            case 2: {
                explain = "L2进阶";
                break;
            }
            case 3: {
                explain = "L3达人";
                break;
            }

            case 4: {
                explain = "L4专家";
                break;
            }

            case 5: {
                explain = "L5教授";
                break;
            }
            case 6: {
                explain = "老司机";
                break;
            }
        }
        return explain;
    }

    public String getDailyViewNum() {
        if (dailyViewNum < 99) {
            return String.valueOf(dailyViewNum);
        }
        return "无限制";
    }

    public String getLeftViewNum() {
        if (leftViewNum < 99) {
            return String.valueOf(leftViewNum);
        }
        return "无限制";
    }


    public int getLevelResource(int level) {
        int resource = R.drawable.ic_level0;
        switch (level) {
            case 0: {
                resource = R.drawable.ic_level0;
                break;
            }
            case 1: {
                resource = R.drawable.ic_level1_s;
                break;
            }
            case 2: {
                resource = R.drawable.ic_level2_s;
                break;
            }
            case 3: {
                resource = R.drawable.ic_level3_s;
                break;
            }

            case 4: {
                resource = R.drawable.ic_level4_s;
                break;
            }

            case 5: {
                resource = R.drawable.ic_level5_s;
                break;
            }
            case 6: {
                resource = R.drawable.ic_level6_s;
                break;
            }
        }
        return resource;
    }


    /**
     * 判断时候是游客，
     *
     * @return 没有手机号就是游客
     */
    public Boolean isVisit() {
//        phone = null;
        return TextUtils.isEmpty(phone);
    }

    public static class genderEnum {
        public final static int bot = 0;
        public final static int girl = 1;
        public final static int unknown = 2;
    }

}

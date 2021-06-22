package ltd.android.coriander_video.dto;

/**
 * @author by 黄梦 on 2019/3/21.
 */
public class Categorie {
    public int deleteFlag;
    public long id;
    public String name;
    public long rank;

    public static Categorie getCategorieAll() {
        Categorie movieClass = new Categorie();
        movieClass.name = "全部";
        movieClass.id = -1024;
        return movieClass;
    }
}

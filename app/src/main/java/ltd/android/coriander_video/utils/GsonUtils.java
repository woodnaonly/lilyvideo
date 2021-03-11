package ltd.android.coriander_video.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings("unused")
public class GsonUtils {

  private GsonUtils() {
  }

  public static Gson getInstance() {
    return Holder.gson;
  }

  /**
   * 转成json
   */
  public static String GsonString(Object object) {
    String gsonString = null;
    if (getInstance() != null) {
      gsonString = getInstance().toJson(object);
    }
    return gsonString;
  }

  /**
   * 转成bean
   */
  public static <T> T GsonToBean(String gsonString, Type clazz) {
    T t = null;
    if (getInstance() != null) {
      t = getInstance().fromJson(gsonString, clazz);
    }
    return t;
  }

  /**
   * 转成bean
   */
  public static <T> T GsonToBean(String gsonString, Class<T> cls) {
    T t = null;
    if (getInstance() != null) {
      t = getInstance().fromJson(gsonString, cls);
    }
    return t;
  }

  /**
   * 转成list 泛型在编译期类型被擦除导致报错
   */
  public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
    List<T> list = null;
    if (getInstance() != null) {
      list = getInstance().fromJson(gsonString, new TypeToken<List<T>>() {
      }.getType());
    }
    return list;
  }

  /**
   * 转成list 解决泛型问题
   */
  public static <T> List<T> jsonToList(String json, Class<T> cls) {
      if (json == null) {
          return new ArrayList<>();
      }
    Gson gson = getInstance();
    List<T> list = new ArrayList<T>();
    JsonArray array = new JsonParser().parse(json).getAsJsonArray();
    for (final JsonElement elem : array) {
      list.add(gson.fromJson(elem, cls));
    }
    return list;
  }

  /**
   * 转成list中有map的
   */
  public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
    List<Map<String, T>> list = null;
    if (getInstance() != null) {
      list = getInstance().fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
      }.getType());
    }
    return list;
  }

  /**
   * 转成map的
   */
  public static <T> Map<String, T> GsonToMaps(String gsonString) {
    Map<String, T> map = null;
    if (getInstance() != null) {
      map = getInstance().fromJson(gsonString, new TypeToken<Map<String, T>>() {
      }.getType());
    }
    return map;
  }

  /**
   * 将Map转化为Json
   */
  public static <T> String mapToJson(Map<String, T> map) {
    return getInstance().toJson(map);
  }

  private static class Holder {

    public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        //解决Map 将String转成double的问题， 如果Double等于long就转成long
        .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
          @Override
          public JsonElement serialize(Double src, Type typeOfSrc,
              JsonSerializationContext context) {
              if (src == src.longValue()) {
                  return new JsonPrimitive(src.longValue());
              }
            return new JsonPrimitive(src);
          }
        }).registerTypeHierarchyAdapter(BigDecimal.class, new JsonSerializer<BigDecimal>() {
          @Override
          public JsonElement serialize(BigDecimal src, Type typeOfSrc,
              JsonSerializationContext context) {
            String s = src.toPlainString();
            return new JsonPrimitive(s);
          }
        }).create();
  }
}


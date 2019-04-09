package cn.newexist.admin.redis;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description
 **/
public interface CacheService {
    /**
     * 保存缓存
     *sd
     * @param value    *
     * @param key      *
     * @param expire   过期时间
     * @param timeUnit 过期时间单位
     *                 *
     * @since 1.0.0
     */
    void set(String key, String value, long expire, TimeUnit timeUnit);

    /**
     * 保存缓存
     *
     * @param value *
     * @param key   *
     * @since 1.0.0
     */
    void set(String key, String value);

    /**
     * 读取缓存
     *
     * @param key *
     * @return
     */
    String get(String key);

    /**
     * 获取符合当前缀的缓存数据
     *
     * @param keyPrefix
     * @return
     */
    Set<String> scanKeyByPrefix(String keyPrefix);


    /**
     * 删除一个缓存
     *
     * @param key
     * @return 存在key且成功删除 才返回true
     */
    boolean delete(String key);

    /**
     * 删除多个缓存
     *
     * @param keys
     * @return 删除key数量 *
     */
    long delete(String... keys);

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param obj
     * @param expire
     * @param timeUnit 过期时间单位
     * @return
     */
    void set(String key, Object obj, long expire, TimeUnit timeUnit);

    /**
     * 向缓存中设置对象
     *
     * @param key *
     * @param obj *
     * @return
     */
    void set(String key, Object obj);

    /**
     * 从缓存中获取对象
     *
     * @param key   *
     * @param clazz *
     * @param <T>   *
     * @return </T>
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * @param key   *
     * @param clazz *
     * @param <T>   *
     * @return </T>
     */
    <T> Set<T> getSet(String key, Class<T> clazz);

    /**
     * 从缓存中获取List
     *
     * @param key *
     * @return </T>
     */
    List<String> getList(String key);

    /**
     * 从缓存中获取List
     *
     * @param key   *
     * @param clazz *
     * @param <T>   *
     * @return </T>
     */
    <T> List<T> getList(String key, Class<T> clazz);

    /**
     * 向缓存中添加list
     *
     * @param key   *
     * @param value *
     * @return
     */
    void addToList(String key, List value);

    /**
     * 向缓存中添加List
     *
     * @param key   *
     * @param value *
     * @return
     */
    void addToList(String key, List value, long expire, TimeUnit timeUnit);

    /**
     * 只有在不存在这个缓存的时候才保存这个对象
     *
     * @param key *
     * @param obj *
     *            <p>
     *            *
     * @return
     */
    boolean setIfNotExists(String key, Object obj);

    /**
     * 只有在不存在这个缓存的时候才保存这个对象
     *
     * @param key      *
     * @param obj      *
     * @param expire   过期时间
     * @param timeUnit 过期时间单位
     *                 <p>
     *                 *
     * @return
     */
    boolean setIfNotExists(String key, Object obj, long expire, TimeUnit timeUnit);

    /**
     * 判断某个key是否存在
     *
     * @param key *
     * @return
     */

    boolean existsKey(String key);


    /**
     * 设置过期时间
     * 单位秒
     *
     * @param key      *
     * @param expire   过期时间
     * @param timeUnit 过期时间单位
     * @return
     */
    void expire(String key, long expire, TimeUnit timeUnit);

    /**
     * 设置缓存在多少时间失效
     *
     * @param key  *
     * @param date 过期时间
     * @return
     */
    void expireAt(String key, Date date);

    /**
     * Returns the remaining time to live of a key that has a timeout.
     *
     * @param key *
     * @return
     */
    long getTTL(String key);

    /**
     * 往mapName的map中添加一个key为mapKey值为value的字符串
     *
     * @param mapName *
     * @param mapKey  *
     * @param value
     */
    void addToMap(String mapName, String mapKey, String value);

    /**
     * 往mapName的map中添加一个key为mapKey值为value的字符串
     *
     * @param mapName *
     * @param mapKey  *
     * @param value
     */
    void addToMap(String mapName, String mapKey, Object value);

    /**
     * 获取map
     *
     * @return
     */
    Map<String, String> getMap(String mapName);

    /**
     * 获取map
     *
     * @return
     */
    <T> Map<String, T> getMap(String mapName, Class<T> tClass);

    /**
     * 获取map中的值
     *
     * @param key   *
     * @param field *
     * @return
     */
    String getMapValue(String key, String field);

    /**
     * 获取map中的值
     *
     * @param key   *
     * @param field *
     * @return
     */
    <T> T getMapValue(String key, String field, Class<T> tClass);


    /**
     * 添加数据到set中
     */
    void addToSet(String key, Object value);

    /**
     * 添加数据到set中
     */
    void addToSet(String key, Object value, long expire, TimeUnit timeUnit);

    /**
     * 向set中设置一批值
     */
    void addToSet(String key, Collection values);

    /**
     * 向set中设置一批值
     */
    void addToSet(String key, Collection values, long expire, TimeUnit timeUnit);

    /**
     * 从set中删除数据
     *
     * @param key   *
     * @param value *
     * @return
     */
    void removeFromSet(String key, String value);

    /**
     * 判断值是否在set中存在
     */
    boolean isInSet(String key, String value);

    /**
     * 判断对象是否在set中存在
     *
     * @param key
     * @param value
     * @return
     */
    boolean isInSet(String key, Object value);

    /**
     * 获取集合的数量
     */
    long getSetSize(String key);

    /**
     * 获取集合
     */
    Set<String> getSet(String key);

    /**
     * 增加
     */
    long increase(String key, Long value);

    /**
     * 减少
     */
    long decrease(String key, Long value);

    /**
     * 删除List中的元素
     * @param key
     * @param count 删除的个数  0 全部删除
     * @param value 删除的值
     */
    void listRemoveElement(String key,long count ,Object value);

    void addToList(String key,String value);

}

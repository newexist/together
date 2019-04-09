package cn.newexist.admin.redis;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description
 **/
@Service
@SuppressWarnings({"unchecked", "Unboxing"})
public class SpringRedisCacheService implements CacheService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (null == value) {
            return null;
        }
        return value.toString();
    }

    @Override
    public boolean delete(String key) {
        Boolean b = redisTemplate.delete(key);
        if (b == null) {
            return false;
        }
        return b;
    }

    @Override
    public long delete(String... keys) {
        Set<String> kSet = Stream.of(keys).collect(Collectors.toSet());
        Long aLong = redisTemplate.delete(kSet);
        if (aLong == null) {
            return 0L;
        }
        return aLong;
    }

    @Override
    public void set(String key, Object obj, long expire, TimeUnit timeUnit) {
        String value = toString(obj);
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    @Override
    public void set(String key, Object obj) {
        String value = toString(obj);
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String obj = redisTemplate.opsForValue().get(key);
        if (null == obj) {
            return null;
        }
        return JSONObject.parseObject(obj, clazz);
    }

    @Override
    public <T> Set<T> getSet(String key, Class<T> clazz) {
        //
        Set<String> members = redisTemplate.opsForSet().members(key);
        if (members == null) {
            return Collections.EMPTY_SET;
        }
        Set<T> set = new HashSet<>(members.size());
        for (String it : members) {
            if (String.class == clazz) {
                set.add((T) it);
            } else {
                set.add(JSON.parseObject(it, clazz));
            }
        }
        return set;
    }

    @Override
    public List<String> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1L);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        List<String> values = redisTemplate.opsForList().range(key, 0, -1L);
        if (values == null) {
            return Collections.EMPTY_LIST;
        }
        List<T> list = new ArrayList<>(values.size());
        for (String value : values) {
            if (String.class == clazz) {
                list.add((T) value);
            } else {
                list.add(JSON.parseObject(value, clazz));
            }
        }
        return list;
    }

    @Override
    public void addToList(String key, List value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        List<String> pushValues = new ArrayList<>(value.size());
        for (Object o : value) {
            pushValues.add(toString(o));
        }
        redisTemplate.opsForList().rightPushAll(key, pushValues);
    }

    @Override
    public void addToList(String key, List value, long expire, TimeUnit timeUnit) {
        addToList(key, value);
        expire(key, expire, timeUnit);
    }

    @Override
    public boolean setIfNotExists(String key, Object obj) {
        String value = toString(obj);
        Boolean b = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (b == null) {
            return false;
        }
        return b;
    }

    @Override
    public boolean setIfNotExists(String key, Object obj, long expire, TimeUnit timeUnit) {
        boolean b = setIfNotExists(key, obj);
        expire(key, expire, timeUnit);
        return b;
    }

    @Override
    public boolean existsKey(String key) {
        Boolean b = redisTemplate.hasKey(key);
        if (b == null) {
            return false;
        }
        return b;
    }

    @Override
    public void expire(String key, long expire, TimeUnit timeUnit) {
        redisTemplate.expire(key, expire, timeUnit);
    }

    @Override
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    @Override
    public long getTTL(String key) {
        Long aLong = redisTemplate.getExpire(key);
        if (aLong == null) {
            return 0L;
        }
        return aLong;
    }

    @Override
    public void addToMap(String mapName, String mapKey, String value) {
        redisTemplate.opsForHash().put(mapName, mapKey, value);
    }

    @Override
    public void addToMap(String mapName, String mapKey, Object value) {
        String jsonValue = JSON.toJSONString(value);
        addToMap(mapName, mapKey, jsonValue);
    }

    @Override
    public Map<String, String> getMap(String mapName) {
        return (Map) redisTemplate.opsForHash().entries(mapName);
    }

    @Override
    public <T> Map<String, T> getMap(String mapName, Class<T> tClass) {
        Map map = getMap(mapName);
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object value = entry.getValue();
            if (tClass != String.class && value != null) {
                value = JSON.parseObject(value.toString(), tClass);
                entry.setValue(value);
            }
        }
        return map;
    }

    @Override
    public String getMapValue(String key, String field) {
        Object result = redisTemplate.opsForHash().get(key, field);
        return result == null ? null : result.toString();
    }

    @Override
    public <T> T getMapValue(String key, String field, Class<T> tClass) {
        String value = getMapValue(key, field);
        if (value == null || String.class == tClass) {
            return (T) value;
        }

        return JSON.parseObject(value, tClass);
    }

    @Override
    public void addToSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, toString(value));

    }

    @Override
    public void addToSet(String key, Object value, long expire, TimeUnit timeUnit) {
        addToSet(key, value);
        expire(key, expire, timeUnit);
    }

    @Override
    public void addToSet(String key, Collection values) {
        if (values == null || values.isEmpty()) {
            return;
        }

        String[] strValues = new String[values.size()];
        int i = 0;
        for (Object value : values) {
            strValues[i++] = toString(value);
        }

        redisTemplate.opsForSet().add(key, strValues);
    }

    @Override
    public void addToSet(String key, Collection values, long expire, TimeUnit timeUnit) {
        addToSet(key, values);
        expire(key, expire, timeUnit);
    }


    @Override
    public void removeFromSet(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    @Override
    public boolean isInSet(String key, String value) {
        Boolean b = redisTemplate.opsForSet().isMember(key, value);
        if (b == null) {
            return false;
        }
        return b;
    }

    @Override
    public boolean isInSet(String key, Object value) {
        String jsonValue = JSON.toJSONString(value);
        return isInSet(key, jsonValue);
    }

    @Override
    public long getSetSize(String key) {
        Long aLong = redisTemplate.opsForSet().size(key);
        if (aLong == null) {
            return 0L;
        }
        return aLong;
    }

    @Override
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public long increase(String key, Long value) {
        Long aLong = redisTemplate.opsForValue().increment(key, value);
        if (aLong == null) {
            return 0L;
        }
        return aLong;
    }

    @Override
    public long decrease(String key, Long value) {
        Long aLong = redisTemplate.opsForValue().increment(key, -value); //增长相反的值
        if (aLong == null) {
            return 0L;
        }
        return aLong;
    }

    @Override
    public void listRemoveElement(String key,long count, Object value) {
        redisTemplate.opsForList().remove(key,0,value);
    }

    @Override
    public void addToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key,value);
    }


    /**
     * 提供键空间的遍历操作，支持游标，复杂度O(1), 整体遍历一遍只需要O(N)；
     * 提供结果模式匹配；
     * 支持一次返回的数据条数设置，但仅仅是个hints，有时候返回的会多；
     * 弱状态，所有状态只需要客户端需要维护一个游标；
     * 无法提供完整的快照遍历，也就是中间如果有数据修改，可能有些涉及改动的数据遍历不到；
     * 每次返回的数据条数不一定，极度依赖内部实现；
     * 返回的数据可能有重复，应用层必须能够处理重入逻辑；上面的示例代码中，redisTemplate.execute方法是个Set，
     * 相当于已经对于返回的key去重
     * count是每次扫描的key个数，并不是结果集个数。
     * count要根据扫描数据量大小而定，Scan虽然无锁，但是也不能保证在超过百万数据量级别搜索效率；
     * count不能太小，网络交互会变多，count要尽可能的大。
     * 在搜索结果集1万以内，建议直接设置为与所搜集大小相同
     * --------------------
     *
     * @param keyPrefix
     * @return
     */
    @Override
    public Set<String> scanKeyByPrefix(String keyPrefix) {
        Set<String> result = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> binaryKeys = new HashSet<>();
            // count是每次扫描的key个数，并不是结果集个数。
            // count要根据扫描数据量大小而定，Scan虽然无锁，但是也不能保证在超过百万数据量级别搜索效率；
            // count不能太小，网络交互会变多，count要尽可能的大。在搜索结果集1万以内，建议直接设置为与所搜集大小相同
            ScanOptions options = ScanOptions.scanOptions().match(keyPrefix + "*").count(1000).build();
            Cursor<byte[]> cursor = connection.scan(options);
            while (cursor.hasNext()) {
                try {
                    binaryKeys.add(new String(cursor.next(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return binaryKeys;
        });

        return result;
    }

    private static String toString(Object obj) {
        return obj instanceof String ? obj.toString() : JSON.toJSONString(obj);
    }

}

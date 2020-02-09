package com.xjf.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Guava Cache 本地缓存的使用
 *
 * @author xjf
 * @date 2020/2/9 20:49
 */
public class Main {

    private PersonDao personDao = new PersonDao();

    private LoadingCache<String, Person> cacheBuilder = CacheBuilder.newBuilder()
            // 缓存过期时间 10 秒
            .expireAfterWrite(10, TimeUnit.SECONDS)
            // 10 内如果没有被读或写就会过期。如果 10 内有读或写，会一直使用下去不过期
//            .expireAfterAccess(10, TimeUnit.SECONDS)
            // 缓存没有命中时则重新从数据库中获取
            .build(new CacheLoader<String, Person>() {
                @Override
                public Person load(String id) throws Exception {
                    System.out.println("从数据库中获取数据");
                    return personDao.getPerson(id);
                }
            });

    /**
     * 获取 Person
     * @param id
     * @return
     */
    public Person getPerson(String id) {
        Person person = null;
        try {
            person = cacheBuilder.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return person;
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();

        for (int i = 0; i < 10; i++) {
            System.out.println(main.getPerson("3"));
            TimeUnit.SECONDS.sleep(3);
            if (i == 5){
                TimeUnit.SECONDS.sleep(12);
            }
        }

    }
}

package com.xjf.redis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * 布隆过滤器测试
 *
 * @author xjf
 * @date 2020/2/11 11:50
 */
public class BloomFilterTest {

    public static void main(String[] args) {

        int total = 1000000;
        // 初始化布隆过滤器，定义有一百万个数据。第三个参数为容错率，比如设置为0.03，则容错率为 0.03%。
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total, 0.0003);

        // 初始化 1000000 条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put("" + i);
        }

        // 判断值是否在过滤器中，请求的总数为 1010000
        int count = 0;
        for (int i = 0; i < total + 10000; i++) {
            if (bf.mightContain("" + i)){
                count++;
            }
        }

        System.out.println("匹配数量：" + count);
        System.out.println("===========================分隔符============================");
        cal();
    }


    /**
     * 计算误报率
     * @return
     */
    private static void cal(){
        int capacity = 1000000;
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
        int sum = 0;
        for (int i = capacity + 20000; i < capacity + 30000; i++) {
            if (bloomFilter.mightContain(i)) {
                sum ++;
            }
        }
        //0.03
        System.out.println("错误数目：" + sum);
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        System.out.println("错判率为:" + df.format((float)sum/10000));
    }
}

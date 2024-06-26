package com.ksyun.multithread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataAnalyzer {

    //数据集合对象
    private static List<DataItem> list;

    //保证多线程安全的原子变量
    private static final AtomicInteger num = new AtomicInteger(0);

    //id出现次数的集合
    private static final Map<Integer,Integer> mapResult = new HashMap<>();

    //描述出现个数的集合
    private static final Map<String,Integer> map = new HashMap<>();

    public static void putMapResult(Integer key,Integer value){
        mapResult.put(key,value);
    }

    public static boolean keyExist(Integer key){
        return mapResult.containsKey(key);
    }

    public static Integer getValue(Integer key){
        return mapResult.get(key);
    }

    public static void put(String key,Integer value){
        map.put(key,value);
    }

    public static boolean kExist(String key){
        return map.containsKey(key);
    }

    public static Integer get(String key){
        return map.get(key);
    }

    //在DataAnalyzer类中实现一个analyze方法，该方法启动多个线程来并行处理数据集合。每个线程负责处理集合的一部分数据。
    //每个线程应该实现Runnable接口，并在run方法中完成对分配给它的数据部分的统计分析。
    public static void analyze(int nThreads){
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        for(int i = 0;i < list.size();i++){
            executor.submit(new MyThread(list.get(num.getAndIncrement())));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                // 超时后仍未完成，可以选择取消剩余任务
                executor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            // 如果在等待时线程被中断，重新设置中断状态并取消任务
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    //在DataAnalyzer类中实现一个mergeResults方法，用于汇总所有线程的结果。
    public static StringBuilder mergeResults(){
        StringBuilder result;
        result = new StringBuilder("ID ");
        for (Map.Entry<Integer,Integer> entry:mapResult.entrySet()) {
            result.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        result.setLength(result.length() - 1);
        result.append(" high frequency description ");
        result.append(getKeyWithMaxValue(map));
        return result;
    }

    //获取描述次数最大的值
    public static String getKeyWithMaxValue(Map<String,Integer> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        String maxKey = null;
        Integer maxValue = map.values().iterator().next(); // 假设第一个值就是最大值，稍后会比较
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            Integer value = entry.getValue();
            if (value.compareTo(maxValue) > 0) {
                maxKey = entry.getKey();
                maxValue = value;
            }
        }
        return maxKey;
    }

    //获取文件文本
    public static void readFile(String filePath){
        try (FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            String jsonString = sb.toString();
            list = convertStringToDataItems(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将字符串转化为DataItem对象
    public static List<DataItem> convertStringToDataItems(String input) {
        List<DataItem> dataItems = new ArrayList<>();
        // 去除字符串的首尾大括号
        input = input.substring(1, input.length() - 1);
        // 正则表达式匹配键值对
        Pattern pattern = Pattern.compile("(\\d+):\"(.*?)\"");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String description = matcher.group(2);
            dataItems.add(new DataItem(id, description));
        }
        return dataItems;
    }

}

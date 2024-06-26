package com.ksyun.multithread;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("请指定文件路径");
            return;
        }
        DataAnalyzer.readFile(args[0]);
        DataAnalyzer.analyze(3);
        System.out.println(DataAnalyzer.mergeResults());
    }

}

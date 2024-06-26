# Ksyun Camp Multithread

## 题目

实现一个Java多线程程序，该程序能够对一个大型数据集合进行统计分析。数据集合包含多个元素，每个元素包含一个整数ID和一个字符串描述。程序需要对集合中的数据进行以下统计分析：

1. 计算集合中每个ID出现的次数。

2. 统计集合中描述字段中出现频率最高的字符串。


## 要求：

1. 仓库分支：main，项目名称为 ksyun-camp-multithread

2. 创建一个DataItem类，包含id和description属性。

3. 创建一个DataAnalyzer类，该类包含一个List<DataItem>作为数据集合。

4. 在DataAnalyzer类中实现一个analyze方法，该方法启动多个线程来并行处理数据集合。每个线程负责处理集合的一部分数据。

5. 每个线程应该实现Runnable接口，并在run方法中完成对分配给它的数据部分的统计分析。

6. 在DataAnalyzer类中实现一个mergeResults方法，用于汇总所有线程的结果。

7. 在main方法中，加载验收数据后，创建DataAnalyzer实例并启动分析线程。等待所有线程完成，并打印最终的统计结果。

8. 工程采用maven 搭建，环境信息为 Linux运行环境、maven 3.9.0以上，jdk 1.8

9. 作业验收数据以文本文件提供，内容为json格式，程序需要将指定路径下的文件内容读入后放入内存集合中做后续分析，文件内容示例

  ```json
  {1:"Licensed",1:"Apache",2:"See",1:"hello",3:"Version",9:"Licensed"}
  ```

10. 作业结果直接输出到控制台，格式为`ID [{$id}:{$sum},{$id}:{$sum},...]  high frequency description {$word}`，对应示例中的结果则为 ID 1:3,2:1,3:1,9:1 high frequency description Licensed，(注意示例内容仅是说明格式，非最终考核文本内容）



## 批改方式
1. 直接将作业代码提交到git 仓库main分支上，我们会拉取源码后进行maven编译，生成可执行jar包，mvn clean package
2. 运行编译后的jar，命令如java -jar ksyun-camp-multithread.jar /data/homework/multithread/input_data >result
3. 检查result 结果是否正确 

 评分标准

- 程序输出结果满足题目要求得分70

- 源码书写与题目要求一致得分30



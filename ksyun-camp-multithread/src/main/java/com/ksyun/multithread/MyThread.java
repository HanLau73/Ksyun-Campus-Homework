package com.ksyun.multithread;

public class MyThread implements Runnable{

    private DataItem dataItem;

    public MyThread(DataItem dataItem) {
        this.dataItem = dataItem;
    }

    @Override
    public void run() {
        if(DataAnalyzer.keyExist(dataItem.getId())){
            DataAnalyzer.putMapResult(dataItem.getId(),DataAnalyzer.getValue(dataItem.getId()) + 1);
        }else {
            DataAnalyzer.putMapResult(dataItem.getId(),1);
        }
        if(DataAnalyzer.kExist(dataItem.getDescription())){
            DataAnalyzer.put(dataItem.getDescription(),DataAnalyzer.get(dataItem.getDescription()) + 1);
        }else {
            DataAnalyzer.put(dataItem.getDescription(),1);
        }
    }

}

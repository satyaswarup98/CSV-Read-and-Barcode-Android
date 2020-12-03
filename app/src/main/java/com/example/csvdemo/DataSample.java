package com.example.csvdemo;

public class DataSample {
    private String unv;
    private int hi;
    private int ci;

    public String getUnv() {
        return unv;
    }

    public void setUnv(String unv) {
        this.unv = unv;
    }

    public int getHi() {
        return hi;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    @Override
    public String toString() {
        return unv +",  " + hi + ",  " + ci +"\n" ;
    }
}

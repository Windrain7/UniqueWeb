package com.example.demo.model;

public class Employee {
    private int no;
    private String name;
    private String job;
    private double salary;

    public void setNo(int no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "No: " + this.no + "\n" + this.name + "    job: " + this.job + "    salary:" + this.salary;
    }
}

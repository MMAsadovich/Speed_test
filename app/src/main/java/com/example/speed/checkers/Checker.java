package com.example.speed.checkers;

public abstract class Checker extends Thread{

    private Boolean isSuccess;

    public Checker() {
        this.isSuccess = false;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }


}

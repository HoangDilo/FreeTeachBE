package com.example.freeteachbe.DTO.ReturnPayload;

public class DataMessage<T> extends Message{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataMessage(String message, T data) {
        super(message);
        this.data = data;
    }
}

package com.mustafafidan.rahatlaticisesler.base;

public class BaseResponse<T> {
    T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


// error mesajı gibi her servis isteğinde sabit olan alanlar eklenebilir
    //boolean isSuccess;
    //int code;


}

package com.haier.uhome.os.javaandroid.generics;

/**
 * 泛型类
 *
 * @param <T>
 */
public class GenericsInterfaceImp<T> implements GenericsInterface<T> {

    private T data;

    public void setdata(T data) {
        this.data = data;
    }

    @Override
    public T getdata() {
        return null;
    }
}

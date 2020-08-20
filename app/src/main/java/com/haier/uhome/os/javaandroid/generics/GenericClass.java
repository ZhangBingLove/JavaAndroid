package com.haier.uhome.os.javaandroid.generics;

/**
 * 泛型类
 *
 * @param <T>
 */
public class GenericClass<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        GenericClass<String> genericClass = new GenericClass<>();
        genericClass.setData("Generic Class");
        System.out.println(genericClass.getData());
    }
}

package com.kbh.java8.lecture.ex03;

import java.util.function.Function;

/**
 * Created by ohjic on 2016-02-22.
 */
public class FunctionalInterface_Function {
    public static void main(String[] args){
        Function<String,Integer> toInt = new Function<String, Integer>() {
            @Override
            public Integer apply(final String value) {
                return Integer.parseInt(value);
            }
        };

        //to LamdaExpresstion
        Function<String,Integer> toInt2 = value -> Integer.parseInt(value);


        final Integer number = toInt.apply("100");
        final Integer number2 = toInt2.apply("100");

        System.out.println(number+"\t\t"+ number2);

        final Function<Integer,Integer> identity = Function.identity();
        final Function<Integer,Integer> identity2 = t -> t;             //Fuction Class를 참고하여보자

        System.out.println(identity.apply(999));
        System.out.println(identity2.apply(888));
    }
}

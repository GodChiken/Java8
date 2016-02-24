package com.kbh.java8.lecture.ex03;

import java.util.function.Consumer;

/**
 * Created by ohjic on 2016-02-22.
 */
public class FunctionalInterface_Consumer {
    public static void main(String[] args){
        //Consumer
        final Consumer<String> print = new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println(value);
            }
        };
        //to LamdaExpression
        final Consumer<String> print2 = value -> System.out.println(value);
        /* 우리는 펑션을 통하여도 할수있지않을까 하는 의문을가질수 있지만, 작성하게되면 다음의 에러가 발생한다.
         Void가 void로 converting 이 안되는 에러가 발생한다. 이러한 이유는 Function값은 리턴값을 가지고 있어야하기 때문이다.
         하여 아무리 Void로 설정한다해도 에러발생
         만약에 이 무제를 해결하고 싶다할시에 Consumer를 활용한 print2를 참고하면 된다.*/
        /*final Function<String,Void> print3 = value -> System.out.println(value);*/
        print.accept("hello");

        final Consumer<String> greeting = value -> System.out.println("Hello"+value);

        greeting.accept("Kevin");
    }
}

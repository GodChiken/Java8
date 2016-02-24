package com.kbh.java8.lecture.ex03;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by ohjic on 2016-02-22.
 */
public class FunctionalInterface_Supplier_LazyEvaluation {
    public static void main(String[] args){
        //Supplier
        /**
        * 입력 값이 없는데 어떻게 결과값이 있고 사용용도를 주목하자
         *
         * */
        final Supplier<String> helloSupplier = () -> "hello";
        System.out.println(helloSupplier.get()+"world");

        long start = System.currentTimeMillis();

        /**
         * getVeryExpensiveValue()만 쓰게되는 경우 바로 호출하여 String 값을 리턴받고 그 값을
         * printIfValidIndex에 파라미터 값으로 넣으려하는데, String과 Supplier<String>의 타입 불일치로 인한
         * 에러가 발생한다.
         *
         * printIfValidIndex(0,getVeryExpensiveValue());        에러
         *
         * 하여 Supplier는 파라미터가 존재하지 않기 떄문에 ()->method 형식으로 호출한다.
         *
         * */

        printIfValidIndex(0,getVeryExpensiveValue());
        printIfValidIndex(-1,getVeryExpensiveValue());
        printIfValidIndex(-2,getVeryExpensiveValue());

        System.out.println("불필요한 작업까지 모두 완수한시간 " + (System.currentTimeMillis() - start) / 1000);

        long correct_start = System.currentTimeMillis();

        printIfValidIndex(0,()->getVeryExpensiveValue());
        printIfValidIndex(-1,()->getVeryExpensiveValue());
        printIfValidIndex(-2,()->getVeryExpensiveValue());

        System.out.println("Supplier을 통한 Lazy Evaluation을 활용한 필요한작업만 완수한시간 " + (System.currentTimeMillis() - correct_start) / 1000);


    }

    private static String getVeryExpensiveValue(){
        // let's jest say it has very expensive calculation here!
        // 과도한 연산과정에 의해서  값이 출력된다고 가정한다.
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  "kevin";
    }

    /*private static void printIfValidIndex(int number, String value){*/
    private static void printIfValidIndex(int number, Supplier<String> valueSupplier){
        if(number >= 0){
            System.out.println("값은" + valueSupplier.get() + ".");
        }else {
            System.out.println("Invalid");
        }
    }

    private static void printIfValidIndex(int number, String value){
        if(number >= 0){
            System.out.println("값은" + value + ".");
        }else {
            System.out.println("Invalid");
        }
    }
}

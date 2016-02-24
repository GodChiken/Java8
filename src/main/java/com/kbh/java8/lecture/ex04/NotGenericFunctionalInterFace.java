package com.kbh.java8.lecture.ex04;

import java.math.BigDecimal;

/**
 * Created by ohjic on 2016-02-22.
 */

public class NotGenericFunctionalInterFace {
    public static void main(String[] args){
       BigDecimalToCurrency bigDecimalToCurrency = bd -> "$" + bd.toString();
        System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal("120.00")));

        //사용이 불가하다.
        /*final InvalidFunctionalInterface invalidFunctionalInterface = value -> value.toString();
        System.out.println(invalidFunctionalInterface.mkString(123));*/
    }
    /*private static <T1,T2,T3> void println(T1 t1, T2 t2, T3 t3,Function3<T1,T2,T3,String> function){
        System.out.println(function.apply(t1,t2,t3));
    }*/
}
@FunctionalInterface
interface BigDecimalToCurrency {
    String toCurrency(BigDecimal value);
}
/*@FunctionalInterface를 적용하여 컴파일 타임에서 검사를 통과하여도 LamdaExpression을 못하는 경우가 있는데
* Generic method 인 경우 그러하다*/
@FunctionalInterface
interface InvalidFunctionalInterface {
    <T> String mkString(T value);
}

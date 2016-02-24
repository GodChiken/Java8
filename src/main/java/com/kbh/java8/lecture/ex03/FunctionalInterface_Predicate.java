package com.kbh.java8.lecture.ex03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by ohjic on 2016-02-22.
 */
public class FunctionalInterface_Predicate {
    public static void main(String[] args){

        //Predicate
        /*입력값은 지정할수있고 결과값은 언제나 Boolean 형태의 값을 리턴하는 인터페이스이다.
        * 형태는 Predicate<T>, 즉 Function<T,Boolean>이라고 생각해도 무방하다.
        * 보통 주어진 결과값을 만족하는지 확인 시 많이 사용된다.*/

        Predicate<Integer> isPositive = i -> i > 0;
        Predicate<Integer> lessThan3 = i -> i < 3;

        /*이런식으로 사용될수 없다 자바 내부의 FCC의 조건을 만족하지 않기 때문에
        * single abstract method를 허용하지 않는다.*/
        /*System.out.println(isPositive(1));*/

        System.out.println(isPositive.test(1));
        System.out.println(isPositive.test(0));
        System.out.println(isPositive.test(-1));

        List<Integer> numbers = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);

        List<Integer> positiveNum = new ArrayList<>();
        System.out.println("Predicate Start");
        for(Integer num : numbers){
            System.out.println(isPositive.test(num));
            if(isPositive.test(num)){
                positiveNum.add(num);
            }
        }

        List<Integer> numberLessthan3 = new ArrayList<>();
        System.out.println("Predicate Start");
        for(Integer num : numbers){
            System.out.println(isPositive.test(num));
            if(lessThan3.test(num)){
                numberLessthan3.add(num);
            }
        }
        System.out.println("positive integer:" + positiveNum);
        System.out.println("number less than 3:" + numberLessthan3);

        System.out.println("Filter positive integer:" + filter(numbers,isPositive));
        System.out.println("Filter number less than 3:" + filter(numbers,lessThan3));
    }
    private static <T> List<T> filter(List<T> list, Predicate<? super T> filter){
        List<T> result = new ArrayList<>();
        for (T input : list){
            if(filter.test(input)) {
                result.add(input);
            }
        }
        return result;
    }
}

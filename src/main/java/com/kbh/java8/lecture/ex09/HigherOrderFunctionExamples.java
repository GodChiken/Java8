package com.kbh.java8.lecture.ex09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Higher-Order Function (고계함수 or 고차함수)
 *
 * 다음 2가지 사항을 만족하면 Higher-Order Fuction이라고 보면 된다.
 *
 * 1. 펑션이 인자값으로 또다른 메서드를 받는다.
 * 2. 펑션을 실행시 리턴값으로 또 다른 펑션을 리턴. *
 *  ex) Function<Function<Integer,String>,String>
 *      f = x -> x.apply(10)
 *      f.apply(i->"#"+i)
 *
 *      (i->"#"+i) = x
 *
 * Function<Integer, Function<Integer,Integer>>
 *
 * f = i -> i2 -> i+i2
 *
 * i2 -> i+i2 == Function<Integer,Integer> = R
 * i -> i2 -> i+i2 == Function<Integer, Function<Integer,Integer>>
 *
 * f.apply(1).apply(9)
 * i = 1
 * i2 = 9
 *
 */
public class HigherOrderFunctionExamples {
    public static void main(String[] args) {
        final Function<Function<Integer,String>,String> f = g -> g.apply(10);
        //f = Higher-order Function
        //g = i -> "#"+i
        System.out.println( f.apply(i -> "#" + i) );

        final Function<Integer,Function<Integer,Integer>> f2 = i -> (i2 -> i+i2);
        System.out.println(
                f2.apply(1).apply(9)
        );

        final List<Integer> list = Arrays.asList(1,2,3,4,5);
        final List<String> mappedList = map(list, i -> "#" + i);
        System.out.println(
                mappedList
        );
        System.out.println(
                list.stream()
                        .map(i -> "#" + i)
                        .collect(toList())
        );
        Function<Integer,Function<Integer,Function<Integer,Integer>>> f3=
                i1 -> i2 -> i3-> i1+i2+i3;

        //old
        /*final Function<Integer,Function<Integer,Integer>> applied1 = f3.apply(1);
        final Function<Integer, Integer> applied2 = applied1.apply(2);*/
        //new
        System.out.println(
                "f3.apply(1).apply(2).apply(3) = "+f3.apply(1).apply(2).apply(3)
        );

    }
    private static <T,R> List<R> map(List<T> list, Function<T, R> mapper){
        final List<R> result = new ArrayList<>();
        for(final T t : list){
            result.add(mapper.apply(t));
        }
        return result;
    }

}

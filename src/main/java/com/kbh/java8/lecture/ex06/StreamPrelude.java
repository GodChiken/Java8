package com.kbh.java8.lecture.ex06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by ohjic on 2016-02-23.
 */
public class StreamPrelude {
    public static void main(String[] args) {
        int abs1 = Math.abs(-1);
        int abs2 = Math.abs(1);

        System.out.println(abs1);
        System.out.println(abs2);
        System.out.println(abs1==abs2);

        int minInt = Math.abs(Integer.MIN_VALUE);
        //절대값인데 어찌하여 마이너스 가 나오는가?
        /*자바는 Integer는 32bit 자바에는 unsigned가 없다. -2~31승 ~~ 2~31승-1
        *
        * 즉 인티저의 최소값의 절대값은, 최대값의 절대값보다 1이 더크기 떄문에 overflow현상이 일어나기 때문이다.
        *
         *  */
        System.out.println(minInt);
        //IdentityFunction
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("\nmapOld(numbers, i -> i * 2)");
        System.out.println(
            mapOld(numbers, i -> i * 2)
        );

        System.out.println("mapOld(numbers, null)");
        System.out.println(
            mapOld(numbers, null)
        );


        System.out.println("");
        System.out.println("map(numbers, i -> i * 2)\n" +
            map(numbers, i -> i * 2)
        );
        System.out.println("map(numbers, i -> i)\n" +
            map(numbers, i -> i)
        );
        System.out.println("map(numbers, Function.identity())\n" +
            map(numbers, Function.identity())
        );


    }
    private static <T,R> List<R> map(final List<T> list, final Function<T,R> mapper){
        final List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(mapper.apply(t));
        }
        return  result;
    }
    private static <T,R> List<R> mapOld(final List<T> list, final Function<T,R> mapper){
        final List<R> result;
        if (mapper != null) {
            result = new ArrayList<>();
        }else{
            result = new ArrayList<>((List<R>)list);
        }
        if (result.isEmpty()) {
            for (T t : list) {
                result.add(mapper.apply(t));
            }
        }
        return  result;
    }
}

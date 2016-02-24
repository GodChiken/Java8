package com.kbh.java8.lecture.ex07;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ohjic on 2016-02-23.
 * 웹버전의 자바 콘솔 테스팅 : http://www.javarepl.com/console.html
 */
public class StreamExample {
    public static void main(String[] args) {

        range();
        /**
         * 아래 두 메소드를 실행하고 그냥 두시면 숫자가 계속 증가합니다.
         * 꼭 강제종료 시켜주세요.
         */
//    infiniteCollection1();
//    infiniteCollection2();
    }

    private static void range() {
        /**
         * 0 1 2 3 4 5 6 7 8 9
         */
        IntStream.range(0, 10)
                .forEach(i -> System.out.print(i + " "));

        /**
         * 1 2 3 4 5 6 7 8 9 10
         */
        IntStream.rangeClosed(1, 10)
                .forEach(i -> System.out.print(i + " "));
    }

    /**
     * int를 이용한 무한 Collection
     */
    private static void infiniteCollection1() {
        IntStream.iterate(1, i -> i + 1)
                .forEach(i -> System.out.print(i + " "));
    }

    /**
     * BigInteger를 이용한 무한 Collection
     */
    private static void infiniteCollection2() {
        Stream.iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE))
                .forEach(i -> System.out.print(i + " "));
    }
}

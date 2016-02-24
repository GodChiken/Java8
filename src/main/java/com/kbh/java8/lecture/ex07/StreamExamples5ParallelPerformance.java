package com.kbh.java8.lecture.ex07;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by ohjic on 2016-02-23.
 */
public class StreamExamples5ParallelPerformance {
    private static void slowDown() {
        try {
            TimeUnit.MILLISECONDS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
            slowDown();
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).peek(i -> slowDown()).reduce(Long::sum).get();
    }

    /*
    * 병렬 스트림을 쓰게되면
    * Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    * 의 구조로 진행하기되면 아무리 코어를 여러개 써서 작업을 하라 지시하여도
    * 항상 결과값이 있어야 그다음 작업을 할수밖에 없게 되버린다.
    * 싱글코어랑 작업하는것과 별반 다를 바가 없게 된다.
    * */
    /*
    * peek란 매서드는? 작없을 하지만 리턴값을 가지고있지않다.
    * foreach와 비슷하지만 다른점이 있다.
    * foreach는 Terminal Operation Method라 그냥 끝나버리지만
    * peek같은 경우에는 Stream을 리턴을 한다
    * 그대신에 peek가 받는 function은 리턴값이 없다.
    * 하여 void 매서드를 적용하기 안성맞춤이다.
    * */
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().peek(i -> slowDown()).reduce(Long::sum).get();
    }

    /*
    * cpu의 코어수 에 따라서 1~1000까지의 연산을 한다 할경우
    * 4등분하여 연산 작업을 하는 것이 아닌
    * 병렬스트림 내부 자체에서 적절히 여러개로 나누어 계산하고 합치고 또 나머지 부분을 나누거나 합치는 방식이 진행된다.
    * 이런 내부적인 연산과정때문에 순서가 보장이 안된다.
    * */
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).peek(i -> slowDown()).reduce(Long::sum).getAsLong();
    }

    /*
    *
    * */
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().peek(i -> slowDown()).reduce(Long::sum).getAsLong();
    }

    public static void main(String[] args) {
        final long n = 1000;
        final long start = System.currentTimeMillis();
//    1 + 2 + 3 + ... + 98 + 99 + 100
        System.out.println((1 + n) * (n / 2));
        System.out.println("           Gauss Way: " + (System.currentTimeMillis() - start) + " ms\n");

        final long start1 = System.currentTimeMillis();
        System.out.println("     iterativeSum(n): " + iterativeSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start1) + " ms\n");
        final long start2 = System.currentTimeMillis();
        System.out.println("    sequentialSum(n): " + sequentialSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start2) + " ms\n");
        final long start3 = System.currentTimeMillis();
        System.out.println("      parallelSum(n): " + parallelSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start3) + " ms\n");
        final long start4 = System.currentTimeMillis();
        System.out.println("        rangedSum(n): " + rangedSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start4) + " ms\n");
        final long start5 = System.currentTimeMillis();
        System.out.println("parallelRangedSum(n): " + parallelRangedSum(n));
        System.out.println("                      " + (System.currentTimeMillis() - start5) + " ms\n");
    }
}

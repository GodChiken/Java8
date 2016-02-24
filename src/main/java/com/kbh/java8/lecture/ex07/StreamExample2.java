package com.kbh.java8.lecture.ex07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by ohjic on 2016-02-23.
 * Java 8 부터는 인터페이스에 추상메서드의 명뿐만아니라 바디, 스태틱메서드도 가질수있다.
 */
public class StreamExample2 {
    private static final List<Integer> NUMBERS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5)
                .forEach(i -> System.out.print(i + " "));
        System.out.println("\n==================================");

        Integer result = null;
        /*직접적으로 제어하고 메모리도 효율적으로 사용하는것 같아 편리해보이지만
        * 코더의 실수가 생길수도있고 코드의 이해도 어렵다.
        * 가령 올바른 결과물을 뽑아내기 위해서는, 15의 연산과정의 필요하다
        * 1,2,3 에서 1번씩
        * 4,5,6에서 4번씩의 연산이 필요하기떄문*/
        for (final Integer number : NUMBERS) {
            if (number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                if (newNumber > 10) {
                    result = newNumber;
                    break;
                }
            }
        }
        System.out.println("\n==================================");
        System.out.println("Imperative Result: " + result);

        /*
        * 실상 보면 27번의 연산과정이 일어나는데 어찌하여쓸까?
        * Stream에서는 filter, map 따위에서 정의해노은 람다식의 연산을
        * 그때그떄 한번씩 돌아가면서 하는 것이아닌 맨 마지막 매서드를 실행할때
        * 그제서야 작업을 시작한다. 미리미리 작업하는 것이아닌 필요한 순간에만 작업한다.
        * 마치 방학숙제를 전날 몰아서 하듯이..
        * */
        System.out.println("\n==================================");
        System.out.println("Functional Result: " +
                        NUMBERS.stream()
                                .filter(number -> number > 3)
                                .filter(number -> number < 9)
                                .map(number -> number * 2)
                                .filter(number -> number > 10)
                                .findFirst()
        );

        System.out.println("\n==================================");
        System.out.println("Functional Result (with logging): " +
                        NUMBERS.stream()
                                .filter(number -> {
                                    System.out.println("number > 3");
                                    return number > 3;
                                })
                                .filter(number -> {
                                    System.out.println("number < 9");
                                    return number < 9;
                                })
                                .map(number -> {
                                    System.out.println("number * 2");
                                    return number * 2;
                                })
                                .filter(number -> {
                                    System.out.println("number > 10");
                                    return number > 10;
                                })
                                .findFirst()
        );
        System.out.println("\n==================================");

        final List<Integer> greaterThan3 = filter(NUMBERS, i -> i > 3);
        final List<Integer> lessThan9 = filter(greaterThan3, i -> i < 9);
        final List<Integer> doubled = map(lessThan9, i -> i * 2);
        final List<Integer> greaterThan10 = filter(doubled, i -> i > 10);
        System.out.println("My own method result: " + greaterThan10);
        System.out.println("My own method result.get(0): " + greaterThan10.get(0));

        System.out.println("\n==================================");
        final List<Integer> myOwnMethodResult =
                filter(
                        map(
                                filter(
                                        filter(NUMBERS,
                                                i -> i > 3),
                                        i -> i < 9),
                                i -> i * 2),
                        i -> i > 10);
        System.out.println("My own method result: " + myOwnMethodResult);
        System.out.println("My own method result.get(0): " + myOwnMethodResult.get(0));

        System.out.println("\n==================================");
        customMethodsWithLogging();
    }

    private static void customMethodsWithLogging() {
        final AtomicInteger count = new AtomicInteger(1);

        final List<Integer> greaterThan3 = filter(NUMBERS, i -> {
            System.out.println(count.getAndAdd(1) + ": i > 3");
            return i > 3;
        });
        final List<Integer> lessThan9 = filter(greaterThan3, i -> {
            System.out.println(count.getAndAdd(1) + ": i < 9");
            return i < 9;
        });
        final List<Integer> doubled = map(lessThan9, i -> {
            System.out.println(count.getAndAdd(1) + ": i * 2");
            return i * 2;
        });
        final List<Integer> greaterThan10 = filter(doubled, i -> {
            System.out.println(count.getAndAdd(1) + ": i > 10");
            return i > 10;
        });
        System.out.println("My own method result: " + greaterThan10);
        System.out.println("My own method result.get(0): " + greaterThan10.get(0));
    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        final List<T> result = new ArrayList<>();
        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}

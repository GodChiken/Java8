package com.kbh.java8.lecture.ex07;

/**
 * Created by ohjic on 2016-02-23.
 */

import java.util.Arrays;
        import java.util.List;
        import java.util.stream.Stream;

        import static java.util.stream.Collectors.*;

/**
 * @author Kevin Lee
 * @since 2015-08-22
 */
public class StreamExample3 {
    /*스트림에는 두가지의 메서드가 존재한다.
    * Intermediate Operation Method
    * - 계속하여 Stream을 리턴하기 때문에 Method Chaining을 통해 무엇을할지 Stream에 지시가 가능
    * - filter(), map() 등이 이에 해당된다.
    * - 스트림을 리턴하는 Method이면 이에 해당된다.
    *
    * Terminal Operation Method
    * -최종적으로 실행하는 메서드를 의미?
    * -Stream외의 메서드를 리턴하거나 void 일경우 이에 해당된다.
    * -Terminal Operation Method를 호출하기 전까진 스트림에 무슨값이 있는지 알수없으니 유의하자
    * */
    public static void main(String[] args) {
        System.out.println("collect(toList()): " +
                        Stream.of(1, 3, 3, 5, 5)
                                .filter(i -> i > 2)
                                .map(i -> i * 2)
                                .map(i -> "#" + i)
                                .collect(toList())
        );

        System.out.println("collect(toSet()): " +
                        Stream.of(1, 3, 3, 5, 5)
                                .filter(i -> i > 2)
                                .map(i -> i * 2)
                                .map(i -> "#" + i)
                                .collect(toSet())
        );

        System.out.println("collect(joining()): " +
                        Stream.of(1, 3, 3, 5, 5)
                                .filter(i -> i > 2)
                                .map(i -> i * 2)
                                .map(i -> "#" + i)
                                .collect(joining())
        );

        System.out.println("collect(joining(\", \")): " +
                        Stream.of(1, 3, 3, 5, 5)
                                .filter(i -> i > 2)
                                .map(i -> i * 2)
                                .map(i -> "#" + i)
                                .collect(joining(", "))
        );

        System.out.println("collect(joining(\", \", \"[\", \"]\")): " +
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(joining(", ", "[", "]")) // [#6, #6, #10, #10]
        );

        System.out.println("distinct().collect(joining(\", \", \"[\", \"]\")): " +
                        Stream.of(1, 3, 3, 5, 5)
                                .filter(i -> i > 2)
                                .map(i -> i * 2)
                                .map(i -> "#" + i)
                                .distinct()
                                .collect(joining(", ", "[", "]"))
        );

        System.out.println("distinct().collect(toList()): " +
                        Stream.of(1, 3, 3, 5, 5)
                                .filter(i -> i > 2)
                                .map(i -> i * 2)
                                .map(i -> "#" + i)
                                .distinct()
                                .collect(toList())
        );
        /*== : 메모리 레퍼런스 비교
        * equals() : Equality를 비교*/
        /*자바의 unboxing boxing
        * 자바의 autoboxing 은 각 자료형의 값을 리턴하고
        * Integer.valueof() dhk new Integer의 차이는 바로
        * valuof 매서드는 캐싱이라는 작업을 하기 때문에, -128~127 까지밖에 안한다.
        * 하여 만약 ==를 활용하여 128을 비교하는 것을 연산할 경우 같지 않으므로 결과값이 empty로 나타난다.
        *
        * */
        final Integer integer3 = 3;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5)
                        .filter(i -> i == integer3)
                        .findFirst()
        );

        final Integer integer127 = 127;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 127)
                        .filter(i -> i == integer127)
                        .findFirst()
        );

        final Integer integer128 = 128;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i == integer128)
                        .findFirst()
        );


        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i.equals(integer128))
                        .findFirst()
        );

        System.out.println(".filter(i -> i > integer3).count(): " +
                        Stream.of(1, 2, 3, 4, 5)
                                .filter(i -> i > integer3)
                                .count()
        );

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("for (Integer i : numbers): ");
        for (Integer i : numbers) {
            System.out.print("i = " + i + " ");
        }

        System.out.println("\n\nforEach(i -> System.out.println(i)): ");
        Stream.of(1, 2, 3, 4, 5)
                .forEach(i -> System.out.print(i + " "));

    }
}
package com.kbh.java8.lecture.ex07;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by ohjic on 2016-02-23.
 */
public class StreamExamples5ParallelPerformancePractical {
    /*
    * 일정항 price set를 놓고 랜덤으로 돌려서 product을 생성시 값을 세팅하고 이것을 list에 저장하는 예제
    * 특정 가격을 찾은다음 합계를 구하는 코드
    * 예전 1.8 버전 이하의 방식을 구현하여보고, stream, parallelStream 방식으로도 구현해본다.
    * */
    private static final String[] priceStrings = {"1.0", "100.99", "35.75", "21.30", "88.00"};
    private static final BigDecimal[] targetPrices = {new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("31")};
    private static final Random random = new Random(123);
    private static final Random targetPriceRandom = new Random(111);

    private static final List<Product2> products;

    static {
        final int length = 8_000_000;
        final Product2[] list = new Product2[length];
        /*초기에 length가 10인 배열을 내부적으로 생성해서 add메소드 사용시 용량 확인을 하여 꽉찰시 1.5배를 한다
        * 즉 array.length=100,000 인데, 이게 꽉차면 그후에 element 1개만 더 추가하여도 내부의 크기는 150,000으로 변환.*/
        for (int i = 1; i <= length; i++) {
            list[i - 1] = new Product2((long) i, "Product" + i, new BigDecimal(priceStrings[random.nextInt(5)]));
        }
        /*unmodifiableList : 수정 불가하게 만듬
        * 이렇게하면 add, delete, update 작업시
        * UnsupportedOperationException 이 발생한다.
        * */
        products = Collections.unmodifiableList(Arrays.asList(list));
    }

    private static BigDecimal imperativeSum(final List<Product2> products, final Predicate<Product2> predicate) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final Product2 product : products) {
            if (predicate.test(product)) {
                sum = sum.add(product.getPrice());
            }
        }
        return sum;
    }

    private static BigDecimal streamSum(final Stream<Product2> stream, final Predicate<Product2> predicate) {
        return stream.filter(predicate).map(Product2::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void imperativeTest(final BigDecimal targetPrice, final boolean printResult) {
        /**
         * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
         * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
         */
        if (printResult) {
            System.out.println("============================================");
            System.out.println("\nImperative Sum\n--------------------------------------------");
        }
        final long start = System.currentTimeMillis();
        final BigDecimal result = imperativeSum(products, product -> product.getPrice().compareTo(targetPrice) >= 0);
        final long howLong = System.currentTimeMillis() - start;
        if (printResult) {
            System.out.println("Sum: " + result);
            System.out.println("It took " + howLong + " ms.");
            System.out.println("============================================");
        }
    }

    private static void streamTest(final BigDecimal targetPrice, final boolean printResult) {
        /**
         * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
         * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
         */
        if (printResult) {
            System.out.println("============================================");
            System.out.println("\nStream Sum\n--------------------------------------------");
        }
        final long start = System.currentTimeMillis();
        final BigDecimal result = streamSum(products.stream(), product -> product.getPrice().compareTo(targetPrice) >= 0);
        final long howLong = System.currentTimeMillis() - start;
        if (printResult) {
            System.out.println("Sum: " + result);
            System.out.println("It took " + howLong + " ms.");
            System.out.println("============================================");
        }
    }

    private static void parallelStreamTest(final BigDecimal targetPrice, final boolean printResult) {
        /**
         * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
         * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
         */
        if (printResult) {
            System.out.println("============================================");
            System.out.println("\nParallel Stream Sum\n--------------------------------------------");
        }
        final long start = System.currentTimeMillis();
        final BigDecimal result = streamSum(products.parallelStream(), product -> product.getPrice().compareTo(targetPrice) >= 0);
        final long howLong = System.currentTimeMillis() - start;
        if (printResult) {
            System.out.println("Sum: " + result);
            System.out.println("It took " + howLong + " ms.");
            System.out.println("============================================");
        }
    }

    public static void main(String[] args) {
        /**
         * Benchmark 코드라서 결코 일반 앱 개발등에 쓰기 좋은 코딩 스타일로 작성된것이 아닙니다.
         * (Stream사용 이외의) 이런 코드 작성을 절대 권장하지 않습니다.
         */
        test1();
        test2();
        test3();
    }

    private static void test1() {

        final BigDecimal targetPrice = new BigDecimal("40");

        imperativeTest(targetPrice, false);
        streamTest(targetPrice, false);
        parallelStreamTest(targetPrice, false);

        System.out.println("\n\n================================================================\nTest1 Starts!");
        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            imperativeTest(price, true);
            streamTest(price, true);
            parallelStreamTest(price, true);
        }
    }

    private static void test2() {

        final BigDecimal targetPrice = new BigDecimal("40");

        parallelStreamTest(targetPrice, false);
        imperativeTest(targetPrice, false);
        streamTest(targetPrice, false);

        System.out.println("\n\n================================================================\nTest2 Starts!");
        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            parallelStreamTest(price, true);
            imperativeTest(price, true);
            streamTest(price, true);
        }
    }

    private static void test3() {

        final BigDecimal targetPrice = new BigDecimal("40");

        streamTest(targetPrice, false);
        parallelStreamTest(targetPrice, false);
        imperativeTest(targetPrice, false);

        System.out.println("\n\n================================================================\nTest3 Starts!");
        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            streamTest(price, true);
            parallelStreamTest(price, true);
            imperativeTest(price, true);
        }
    }

}

@AllArgsConstructor
@Data
class Product2 {
    private Long id;
    private String name;
    private BigDecimal price;
}

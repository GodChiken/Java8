package com.kbh.java8.lecture.ex10;

/**
 * function & method 차이점
 * function - 호출 가능한 객체 (e.g.sum(productPriceList))
 * method - 특정 객체(object)에 종속된 호출가능한 코드 (e.g.order.discountTotal(10))
 *
 * 하나의 abstract 메서드를 가지는 인터페이스 즉 Functional Interface를 가지고
 * 이것에 대한 구현체를 작성하여 사용하였는데
 *
 * Lambda Expression을 통해서 즉시 사용하였다.
 */
/*
    @FunctionalInterface
    interface Function<T,R>{
        R apply(T t);
    }
*/
/*
    Arrays.asList(1,2,3)
          .stream()
          .map(i -> i*2)
          .collect(toList());
*/

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * 기존의 가지고있는 메서드를 다른메서드의 파라미터로 넘기던지 반환한다든지... 의 해결법이ㅣ
 * Method Refferance 이다.
 * */
public class MethodReferenceExamples {
    public static void main(String[] args) {
        Arrays.asList(1,2,3,4,5)
                /*.forEach(i -> System.out.println(i));*/
                .forEach(System.out::println);


        /*(bd1,bd2) -> bd1.compareTo(bd2)
        *
        * BigDecimal.compareTo(BigDecimal)
        *     bd1                  bd2
        * */
        System.out.println(
                Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
                        .stream()
                        /*.sorted((bd1, bd2) -> bd1.compareTo(bd2))*/
                        /*.sorted(BigDecimalUtil::compare)*/
                        .sorted(BigDecimal::compareTo)//어떤 오브젝트가 오든 그 오브젝트의 메서드를 사용하는 method referrence
                        .collect(toList())
        );

        System.out.println(
                Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
                        .stream()
                        .sorted(BigDecimalUtil::compare)//static method의 method referrence사용
                        .collect(toList())
        );

        /*
        * x -> x.equals("c")
        * String.equals(String)
        *    x             ?
        * */
        final String targetString = "c";
        System.out.println(
                Arrays.asList("a", "b", "c", "d")
                .stream()
                /*.anyMatch(x->x.equals("c"))*/
                /*.anyMatch(String::equals)*/
                /*.anyMatch(x -> targetString.equals(x))*/
                .anyMatch(targetString::equals) //object member의 method referrence의 사용인데 특정한 오브젝트의 method referrence사용
        );
        printMethodRefference03();
        methodReference03();
    }

    private static void printMethodRefference03() {
        System.out.println("\n=================================================================");
        System.out.println("methodReference03");
        System.out.println("\n=================================================================");
    }

    private static void  methodReference03(){
        /*First Class Function*/

        /**
         * Function can be passed as a parameter to another function
         * 함수를 또다른 함수에 파라미터로 넘긴다.
         */
        printPassParameterFunction();
        /*
        * using Lambda Expression
        * */
        System.out.println(testFirstClassFunction1(3, i -> "".valueOf(i * 2)));
        /*
        * 메서드 참조
        * */
        System.out.println(testFirstClassFunction1(3, MethodReferenceExamples::doubleThenToString));

        /**
         * a function can be returned as the result of another function
         * 함수가 다른 함수의 결과값으로 반환 될 수있다.
         * */
        printReturnResultFunction();
        /*
        * using Lambda Expression
        * */
        final Function<Integer,String> fl = getDoubleThenToStringUsingLambdaExpression();
        final String resultFormFl = fl.apply(3);
        System.out.println(resultFormFl);
        /*
        * 메서드 참조
        * */
        final Function<Integer,String> fmr = getDoubleThenToStringUsingMethodRefference();
        final String resultFromFmr = fmr.apply(3);
        System.out.println(resultFromFmr);

        /**
         * A function can be stored in the data structure.
         * 함수는 특정 데이터구조에 저장될 수 있다.
         * */
        printDataStructureInMethod();
        /*
        * using Lambda Expression
        * */
        final List<Function<Integer,String>> fsL = Arrays.asList(i -> String.valueOf(i*2));
        for(final Function<Integer,String> f : fsL){
            final String result = f.apply(3);
            System.out.println(result);
        }
        /*
        * 메서드 참조
        * */
        final List<Function<Integer,String>> fsMr = Arrays.asList(MethodReferenceExamples::doubleThenToString);
        for(final Function<Integer,String> f : fsMr){
            final String result = f.apply(3);
            System.out.println(result);
        }

        final Function<Integer,String> fl2 = i -> "".valueOf(i*2);
        final String resultFl2 = fl2.apply(5);
        System.out.println(resultFl2);

        final Function<Integer,String> fsMr2 = MethodReferenceExamples::doubleThenToString;
        final String resultFsMr2 = fl2.apply(5);
        System.out.println(resultFl2);

        /**
         * 람다와 메서드 레퍼런스 둘다 사용하기
         * */
        printBothLambdaMethodRefference();
        List<Function<Integer,String>> fsBoth = Arrays.asList(i->"".valueOf(i * 2),MethodReferenceExamples::doubleThenToString,MethodReferenceExamples::addHashPrefix);

        for(final Function<Integer,String> f : fsBoth){
            final String result = f.apply(7);
            System.out.println(result);
        }


    }

    private static void printBothLambdaMethodRefference() {
        System.out.println("\n=============================================");
        System.out.println("람다와 메서드 레퍼런스 둘다 사용하기");
        System.out.println("=============================================");
    }

    private static void printDataStructureInMethod() {
        System.out.println("\n=================================================================");
        System.out.println(" A function can be stored in the data structure.");
        System.out.println(" 함수는 특정 데이터구조에 저장될 수 있다.");
        System.out.println("\n=================================================================");
    }

    private static void printReturnResultFunction() {
        System.out.println("\n=================================================================");
        System.out.println(" a function can be returned as the result of another function");
        System.out.println(" 함수가 다른 함수의 결과값으로 반환 될 수있다.");
        System.out.println("\n=================================================================");
    }

    private static void printPassParameterFunction() {
        System.out.println("\n=================================================================");
        System.out.println("Function can be passed as a parameter to another function");
        System.out.println(" 함수를 또다른 함수에 파라미터로 넘긴다.");
        System.out.println("\n=================================================================");
    }

    private static String addHashPrefix(int number){
        return "#"+number*8;
    }
    private static String doubleThenToString(int i ){
        return "".valueOf(i*2);
    }
    private static String testFirstClassFunction1(int n,Function<Integer,String> f){
        return "The result is " + f.apply(n)+ ".";
    }
    private  static Function<Integer,String> getDoubleThenToStringUsingLambdaExpression(){
        return i -> "".valueOf(i * 2);
    }

    public static Function<Integer,String> getDoubleThenToStringUsingMethodRefference() {
        return MethodReferenceExamples::doubleThenToString;
    }
}
class BigDecimalUtil{
    public static int compare(BigDecimal bd1, BigDecimal bd2){
        return  bd1.compareTo(bd2);
    }
}




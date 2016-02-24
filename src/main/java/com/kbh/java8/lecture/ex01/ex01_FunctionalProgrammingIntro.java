package com.kbh.java8.lecture.ex01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ex01_FunctionalProgrammingIntro {
    /*우리는 1~10까지의 리시트의 결과물을 출력하는 예제를 실행한다.*/
    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final StringBuilder stringBuilder = new StringBuilder();

        /*
            우리는 이런식으로 기본 for문에서 벗어난 향상된for문을 사용하여 반복문을 수행한다.
        *   다음의 문제가 발생한다.
        *   다음 명령문의 수행은 1 : 2 : 3 : 4 : 5 : 6 : 7 : 8 : 9 : 10 : 으로 출력
        *   맨 마지막 부분의 ':'을 제거하기 위하여 우리는 foreach문을 쓰지않고 과거의 for문과 if문을 활용하였었다.
        *   이런 비효율 적인 측면이 발생하지만, Java8에서의 해결법이 있다.
        *
        * */
        final String str = " : ";
        for(Integer number : numbers){
            stringBuilder.append(number).append(str);
        }
        /*기존 자바 방식*/
        if(stringBuilder.length() > 0){
            stringBuilder.delete(stringBuilder.length()-3,stringBuilder.length());                   // 프로그래머가 항상 귀찮게 문자열의 시작,끝 지점 인덱스를 신경써야한다.
        }
        System.out.println(stringBuilder.toString());

        /*java8 functional programming*/
        final String result = numbers.stream()
                                     .map(String::valueOf)                                           //Integer형을 String클래스의 valueOf() 메서드를 호출하여 형을 변환한다.
                                     .collect(Collectors.joining(" : "));
        System.out.println(result);
    }
}

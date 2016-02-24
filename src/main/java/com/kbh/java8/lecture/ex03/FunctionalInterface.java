package com.kbh.java8.lecture.ex03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by ohjic on 2016-02-22.
 * 어떤 타입을 받아 다른 결과물을 리턴 받으면 우리는 그것을 일반적인 매퍼에 해당하는 펑션
 * 우리는 같은 타입을 리턴받으면 identity function이라고 한다.
 *
 * 가령 이렇게 매서드가 있다 할 경우
 *
 * public int toInto(String value){
 *     return Integer.parseInt(value);
 * }
 *
 * Identity Function이지만 착각할수도 있는 경우가 있다.
 *
 * public String f(String value){
 *     return "value is" + value;
 * }
 * 이런식으로 변형이 일어나는 경우, 이것은 Indentity Function이라고 할수 없다.
 *
 * public String Identity(String value){
 *     return vaule;
 * }
 *
 * 받은것을 그대로 리턴한다면 왜 쓰는지에 대해선 추후 정의하도록 하신다 하였다.
 */
public class FunctionalInterface {


}

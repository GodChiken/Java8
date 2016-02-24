package com.kbh.java8.lecture.ex04;

/**
 * Created by ohjic on 2016-02-22.
 *
 * Functional Interce를  활용시
 * @FunctionalInterce 라는 어노테이션을 볼 경우가 있다.
 * 이럴땐 Functionalinterface는 Abstract Method를 하나만 가지고 있어야하기때문에
 * 이 어노테이션 활용시 컴파일 에러가 발생한다. 실수를 방지시킬수있다
 * 컴파일 타임 부분에서 발견할수 있다.
 *
 * @FunctionalInterface
 * interface Function<T,R>{
 *     R apply (T t);
 *     R print (T t);
 * }
 * 이렇게 되어있는경우 Lamda Expression에서는 구분을 할수 없기에 에러가 난다.
 * 애초의 람다의 타입에 해당하는 Functional Interface 자체도 결국
 * 오브젝트를 생성해하는데 Method Body가 없는 메서드가 있을시 오브젝트를 생성못하기 때문에
 * 람다를 쓰기위해서는 Singe Abstract Method를 지켜야합니다.
 *
 * tip) instance method 에서는 static method 에 접근 가능하지만 그 반대의 경우에는 접근이 불가하다다 */

@FunctionalInterface
interface Function3<T1,T2,T3, R>{
    R apply(T1 t1,T2 t2,T3 t3);
}

public class CustomFunctionalInterface {
    public static void main(String[] args){
        println(1,2,3,(i1,i2,i3)->"".valueOf(i1 + i2 + i3));
        println("넓이는\t", 12, 20, (message, length, width) -> message + (length * width));
        println("1L","kevin","funnic@naver.com",
                (id,name,email)
                        ->"user info : ID=\t" + id+"name:\t"+name+"email\t"+email);
    }
    private static <T1,T2,T3> void println(T1 t1, T2 t2, T3 t3,Function3<T1,T2,T3,String> function){
        System.out.println(function.apply(t1,t2,t3));
    }
}

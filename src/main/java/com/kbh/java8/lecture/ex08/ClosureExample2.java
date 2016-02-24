package com.kbh.java8.lecture.ex08;

/**
 * 클로저의 구현이 내부적으로 기존의 Anonymous Class와 어떤 차이가 있는지.
 * Lamda Expression 이 Byte Code 레벨에서 어떤식으로 구현됬는가.
 *
 * 추후에 더 듣도록하자.. 지금은 이해하기 어렵다.
 * https://www.youtube.com/watch?v=bKzMl7LKIO0&index=21&list=PLRIMoAKN8c6O8_VHOyBOhzBCeN7ShyJ27
 */
public class ClosureExample2 {
    private int number = 999;

    public static void main(String[] args) {

    }

    public static void test(){
        int number = 100;
        /**
         * 익명클래스를 구현하고 컴파일 시 클래스 파일이 하나 더생긴다.
         * 익명클래스에 대한 클래스 파일이고 파일형식은 '[클래스명]$[번호].class'
         * */
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        };
        runnable.run();

        /**
         * 람다익스프레션이 익명클래스와 동일한 구조로 표현되지 않았을까? 하는 생각이 들수 있다.
         * 람다는 익명클래스처럼 컴파일 타임에 별도의 클래스 파일이 만들어지지 않는다.
         * 하지만 cmd 창에서 javap -c -p 같은 private 메소드까지 결과를 확인할수있는 명령어로 살펴보면
         *
         * ' private static void [lambda]$[인접한메서드]$[인덱스번호](); '
         *
         * 형식으로 private 메서드가 컴파일 타임에 생성이 된다.
         *
         * 람다 레시피라는 것이 생성되는데 람다표현식도 런타임단계에서는 실행할 오브젝트가 있어야한다.
         * 이것의 역활을 하는것이 람다 레시피이다.
         *
         * 지금은 잘 모르겟지만 LambdaMetaFacotry 가 레시피인데
         *
         * java는 static type language이고 컴파일이 된 상태에서 타입이 결정된다.
         * dynamic language가 jvm 에서 돌아가는 것이 있다. jruby json groovy 따위
         * 이런 언어들은 컴파일 타임에 타입을 결정할 수 없는것이 있다.
         * 이것을 지원하기 위해서 타입이결정이 안되도 런타임에서 오브젝트를 생성할수 있게 해주는 것이
         * InvokeDynamic이다.
         *
         * MethodHandle이라는 기능이 추가됬는데 단순히 메소드를 저장하는것이아닌
         * 직접 접근하여 실행할 수있는 메소드 레퍼런스이므로 재사용이 가능한 메소드는 저장 및 즉시사용이 가능하게 한다.
         * */
        Runnable runnable1 = () -> System.out.println(number);
        runnable1.run();
    }
}

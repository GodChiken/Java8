package com.kbh.java8.lecture.ex08;

/**
 * 프로그래밍시 익명과 람다의 차이점을 살피자
 *
 * 클로저란?
 * int i = 100;
 *
 * someMethod(x -> x*2+i)
 * Lamda Expression : 익명 함수라고 불룰수도있고. 지금 경우에는 x-> x*2 가 해당된다.
 * 함수 밖에 존재하는 변수 i는 non-local variable 혹은 Free variable이라고도 부른다.
 * 함수가 밖에 존재하는 non-local variable에 접근한다면 우리는 이것을 Closure 라고 부른다.
 */
/**
 * 기존의 익명클래스를 썻을 경우에는 free variable 인 'number'가 반드시 final 키워드가 붙었어야됫으나
 * 8버전에서부터는 없어도 무방하다. 하지만 익명클래스 내부에서 변경을 할 시에는 에러가 발생한다.
 * 이것을 Effectively Final이라 부른다.
 * */
/** 기존 Old Style의 코딩과 Lamda의 경우 Closure over 과정 시 어떤식으로 ClosureExamples의 자원에 접근하는가에 유의할 필요가 있다.
 * 람다의 경우에는 가장 인접한 클래스의 자원에 접근하지만 익명의 같은 경우에는 오브젝트처럼 사용되므로
 * Runnable의 toString을 사용하게된다.
 * */
public class ClosureExamples {
    private int number = 999;

    public static void main(String[] args) {
        new ClosureExamples().test3();
    }


    private void test() {
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        });

        testClosure("Lambda Expression", () -> System.out.println(number));

    }
    private void test1() {
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(ClosureExamples.this.number);
            }
        });

        testClosure("Lambda Expression", () -> System.out.println(this.number));

    }

    private void test2() {

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println("this.toString(): " + this.toString());
            }
        });

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println("ClosureExamples.this.toString(): " + ClosureExamples.this.toString());
            }
        });

        testClosure("Lambda Expression", () -> System.out.println("this.toString(): " + this.toString()));
    }

    private void test3() {

        /**
         * 가령,
         * System.out.println("\"ClosureExamples calling toString()\": " + toString("text"));
         * 으로 작성할 경우 Object의 toString으로 인식하여 매개변수가 없는데 삽입하려하는 것에 대한 에러가 뜬다. 이것은
         * 좀더 설명하자면 Anonymous Class 가 가지고 있는 메소드 와 이름이 동일한 외부 메서드에 접근할 경우
         * shadowing이 발생하는데 이것에 대한 설명은 다른 강의에 다룬다.
         * 람다는 자체 스콥이 없기때문에 해당 메서드를 가지고있는 가장 가까운 클래스에 접근하여 찾기때문에 이러한
         * shasowing 문제가 발생하지 않는다.
         * */
        System.out.println("\"ClosureExamples calling toString()\": " + toString());
        System.out.println("\"ClosureExamples calling toString(int, String)\": " + toString(1, "Hello"));

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
//        System.out.println("toString(int, String): " + toString(1, "Test"));
                System.out.println("toString(int, String) causes compile-time error");
                System.out.println("ClosureExamples.this.toString(int, String): " + ClosureExamples.toString(1, "Test"));
            }
        });
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
//        System.out.println("toString(int): " + toString(1));
                System.out.println("toString(int) causes compile-time error");
                System.out.println("ClosureExamples.this.toString(int, String): " + ClosureExamples.this.toString(1));
            }
        });

        testClosure("Lambda Expression", () -> System.out.println("this.toString(int, String): " + this.toString(1, "Test")));
        testClosure("Lambda Expression", () -> System.out.println("toString(int, String): " + toString(1, "Test")));
        testClosure("Lambda Expression", () -> System.out.println("this.toString(int): " + this.toString(1)));
        testClosure("Lambda Expression", () -> System.out.println("toString(int): " + toString(1)));
    }

    private void test4() {

        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                int number = 50; // no compile-time error
                System.out.println(number);
            }
        });
        /**
         * 람다의 경우 이미 114번 라인에 지정이 되있으므로 컴파일 에러가 뜬다
         * 현재 람다가 인식하는 scope에 지정이 되있기 때문이다.
         * 익명클래스의 경우에는 컴파일에러가 뜨질 않지만, 개발자가 내부적으로 이런식의 변수의 변경을 일으킬시
         * 실수할 우려가 있다. 위함할수 있다는 이야기이다.
         *
         * */
        testClosure("Lambda Expression", () -> {
//      int number = 50; // compile-time error
            System.out.println(number);
        });

    }

    private static void testClosure(final String name, final Runnable runnable) {
        System.out.println("===================================");
        System.out.println(name + ": ");
        runnable.run();
        System.out.println("===================================");
    }


    @Override
    public String toString() {
        return new StringBuilder("ClosureExamples{")
                .append("number=")
                .append(number)
                .append('}')
                .toString();
    }

    public String toString(int number) {
        return "#" + number;
    }

    public static <T> String toString(int number, T value) {
        return "[" + number + "] The value is " + String.valueOf(value) + ".";
    }
}
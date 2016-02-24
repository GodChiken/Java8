package com.kbh.java8.lecture.ex02;

import java.util.*;

/**
 * Created by ohjic on 2016-02-23.
 */
public class OopExample {
    public static void main(String[] args){
        MyList<Integer> list = new MyList();
        /*list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.getCount());*/

        //5개를 넣었는데 결과값은 10개가 나온다 그 이유는
        /*
            addAll은 결과를 더할때 add를 호출하는 구조를 가지고있다.
            우리가 만든 커스텀리스트이 add메서드는 호출할때마다 카운트를 증가시키는 구조를 가지고있기때문에,
            10으로 결과가 출력된다.
            AbstractCollection.java 파일에 보면

            public boolean addAll(Collection<? extends E> c) {
                boolean modified = false;
                for (E e : c)
                    if (add(e))
                        modified = true;
                return modified;
            }
            의 구조로 되어있다 참고하자.
        */
        list.addAll(Arrays.asList(1,2,3,4,5));
        System.out.println(list.getCount());
    }
}

class MySet<E> extends HashSet<E>{
    @Override
    public boolean add(E e) {
        return add0(e);
    }
    private boolean add0(final E e){
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add0(e))                                //이런식으로 private custom method를 만들어 사용하면 상속에 영향을 받지 않는다.
                modified = true;
        return modified;
    }
}

class MyList<E> extends MySet<E> {
    private int count;

    public int getCount() {
        return count;
    }

    @Override
    public boolean addAll(Collection c) {
        count+= c.size();
        return super.addAll(c);
    }
    @Override
    public boolean add(final E o) {
        count++;
        return super.add(o);
    }
}

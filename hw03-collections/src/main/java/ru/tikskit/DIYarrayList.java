package ru.tikskit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class DIYarrayList<E> implements List<E> {

    private final static int DEFAULT_CAPACITY = 10;
    /*Массив, содержащий данные списка*/
    private Object[] data;
    /*Размер списка*/
    private int size;


    public DIYarrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[])data, 0, size, c);
    }

    @Override
    public Spliterator<E> spliterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(0) != -1;
    }


    private class DIYIterator implements Iterator<E> {
        private int curIndex = 0;

        @Override
        public E next() {
            return (E) DIYarrayList.this.data[curIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            if (DIYarrayList.this.size == 0) {
                return false;
            } else {
                return curIndex < DIYarrayList.this.size;
            }
        }
    }

    private class DIYListIterator implements ListIterator {
        private int next = 0;
        private static final int EMPTY_PREV = -10;
        private int prevRet = EMPTY_PREV;

        public DIYListIterator() {

        }


        @Override
        public void forEachRemaining(Consumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            if (DIYarrayList.this.size == 0) {
                return false;
            } else {
                return next < DIYarrayList.this.size;
            }
        }

        @Override
        public Object next() {
            if (hasNext()) {
                prevRet = next;
                return (E) DIYarrayList.this.data[next++];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasPrevious() {
            if (DIYarrayList.this.size == 0) {
                return false;
            } else {
                return next - 1 >= 0;
            }
        }

        @Override
        public Object previous() {
            if (hasPrevious()) {
                prevRet = --next;
                return (E) DIYarrayList.this.data[prevRet];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public int nextIndex() {
            return next;
        }

        @Override
        public int previousIndex() {
            return next - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Object o) {
            if (prevRet == EMPTY_PREV) {
                throw new IllegalStateException();
            } else {
                DIYarrayList.this.set(prevRet, (E)o);
            }
        }

        @Override
        public void add(Object o) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DIYIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        } else {
            remove(index);
            return true;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int newSize = size + c.size();
        Object[] a = c.toArray();

        if (newSize > data.length) {
            data = Arrays.copyOf(data, data.length + newSize * 2);
        }
        System.arraycopy(a, 0, data, size, a.length);
        size = newSize;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        size = 0;
        /*Обнуляем указатели, чтобы сборщик мусора мог удалить объекты*/
        Arrays.fill(data, 0, data.length - 1, null);
    }

    @Override
    public E get(int index) {
        if (index >= 0 && index < size) {
            return (E) data[index];
        }
        else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public E set(int index, E element) {
        if (index >= 0 && index < size) {
            Object prev = data[index];
            data[index] = element;
            return (E) prev;
        }
        else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public void add(int index, E element) {
        if (index >= 0) {
            if (index < size) {
                data[index] = element;
            } else if (index < data.length) {
                size = index + 1;
                data[index] = element;
            } else {
                data = Arrays.copyOf(data, index * 2);
                size = index + 1;
                data[index] = element;
            }
        }
        else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public E remove(int index) {
        if (index >= 0 && index < size) {
            Object prev = data[index];
/*
            for (int i = index; i < size - 1; i++) {
                data[i] = data[i + 1];
            }
*/
            System.arraycopy(data, index + 1, data, index, size - index - 1);
            data[size - 1] = null;
            size--;
            return (E) prev;
        }
        else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (data[i].equals(o)) {
                    return i;
                }
            }

        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (data[i].equals(o)) {
                    return i;
                }
            }

        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DIYListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<E> stream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<E> parallelStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

        DIYarrayList<Integer> diy = new DIYarrayList<>();
        Integer[] ia = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        Collections.addAll(diy, ia);

        ia = new Integer[]{-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20, -21, -22, -23, -24};
        DIYarrayList<Integer> diy1 = new DIYarrayList<>();
        Collections.addAll(diy1, ia);

        Collections.copy(diy1, diy);

//        diy1.addAll(diy);

/*        ListIterator<Integer> li = diy.listIterator();

        while (li.hasNext()) {
            Integer i = li.next();
            System.out.println(i);
        }

        while (li.hasPrevious()) {
            Integer i = li.previous();
            System.out.println(i);
        }*/
/*

        DIYarrayList<Integer> diy = new DIYarrayList<>();
        Integer[] ia = new Integer[]{7, 9, 1};
        Collections.addAll(diy, ia);
*/

        Collections.sort(diy, new Comparator<>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        if (o1 == o2) {
                            return 0;
                        } else {
                            return o1 - o2;
                        }
                    }
                }
            );

        for (int i = 0; i < diy.size(); i++) {
            System.out.println(i);
        }

    }
}

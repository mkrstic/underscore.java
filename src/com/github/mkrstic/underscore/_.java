package com.github.mkrstic.underscore;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class _ {

    public static <T> void each(final Iterable<T> list, final Block<? super T> func) {
        int index = 0;
        for (T element : list) {
            func.apply(element);
            index += 1;
        }
    }

    public static <T> void each(final Iterable<T> list, final Function2<? super T, Integer, Void> func) {
        int index = 0;
        for (T element : list) {
            func.apply(element, index);
            index += 1;
        }
    }

    public static <T> void forEach(final Iterable<T> list, final Block<? super T> func) {
        each(list, func);
    }

    public static <T> void forEach(final Iterable<T> list, final Function2<? super T, Integer, Void> func) {
        each(list, func);
    }

    public static <T, E> List<T> map(final Iterable<E> list, final Function1<? super E, T> func) {
        final List<T> transformed = new ArrayList<T>();
        for (E element : list) {
            transformed.add(func.apply(element));
        }
        return transformed;
    }

    public static <T, E> List<T> map(final Iterable<E> list, final Function2<? super E, Integer, T> func) {
        final List<T> transformed = new ArrayList<T>();
        int index = 0;
        for (E element : list) {
            transformed.add(func.apply(element, index));
            index += 1;
        }
        return transformed;
    }

    public static <T, E> List<T> collect(final Iterable<E> list, final Function1<? super E, T> func) {
        return map(list, func);
    }

    public static <T, E> List<T> collect(final Iterable<E> list, final Function2<? super E, Integer, T> func) {
        return map(list, func);
    }

    public static <E> E reduce(final Iterable<E> list, final E zeroElem, final Function2<E, E, E> func) {
        E accum = zeroElem;
        for (E element : list) {
            accum = func.apply(accum, element);
        }
        return accum;
    }

    public static <E> E reduce(final Iterable<E> list, final E zeroElem, final Function3<E, E, Integer, E> func) {
        E accum = zeroElem;
        int index = 0;
        for (E element : list) {
            accum = func.apply(accum, element, index);
            index += 1;
        }
        return accum;
    }

    public static <E> E inject(final Iterable<E> list, final E zeroElem, final Function2<E, E, E> func) {
        return reduce(list, zeroElem, func);
    }

    public static <E> E inject(final Iterable<E> list, final E zeroElem, final Function3<E, E, Integer, E> func) {
        return reduce(list, zeroElem, func);
    }

    public static <E> E reduceRight(final Iterable<E> list, final E zeroElem, final Function1<E, E> func) {
        final Stack<E> stack = new Stack<E>();
        for (E elem : list) {
            stack.push(elem);
        }
        E accum = zeroElem;
        int index = 0;
        for (E elem : stack) {
            accum = func.apply(accum);
            index += 1;
        }
        return accum;
    }

    public static <E> E reduceRight(final Iterable<E> list, final E zeroElem, final Function2<E, Integer, E> func) {
        final Stack<E> stack = new Stack<E>();
        for (E elem : list) {
            stack.push(elem);
        }
        E accum = zeroElem;
        int index = 0;
        for (E elem : stack) {
            accum = func.apply(accum, index);
            index += 1;
        }
        return accum;
    }

    public static <E> E find(final Iterable<E> list, final Predicate<E> pred) {
        for (E element : list) {
            if (pred.apply(element)) {
                return element;
            }
        }
        return null;
    }

    public static <E> E detect(final Iterable<E> list, final Predicate<E> pred) {
        return find(list, pred);
    }

    public static <E> List<E> filter(final Iterable<E> list,
                                     final Predicate<E> pred) {
        final List<E> filtered = new ArrayList<E>();
        for (E element : list) {
            if (pred.apply(element)) {
                filtered.add(element);
            }
        }
        return filtered;
    }

    public static <E> List<E> select(final Iterable<E> list,
                                     final Predicate<E> pred) {
        return filter(list, pred);
    }

    public static <E> List<E> where(final Iterable<E> list,
                                    final Iterable<Pair<String, E>> properties) {
        return filter(list, new Predicate<E>() {
            @Override
            public Boolean apply(final E elem) {
                for (Pair<String, E> prop : properties) {
                    try {
                        if (!elem.getClass().getField(prop._1()).get(elem)
                                .equals(prop._2())) {
                            return false;
                        }
                    } catch (Exception ex) {
                        //throw new IllegalArgumentException(ex);
                        return false;
                    }
                }
                return true;
            }
        });

    }

    public static <E> E findWhere(final Iterable<E> list,
                                  final Iterable<Pair<String, E>> properties) {
        return find(list, new Predicate<E>() {
            @Override
            public Boolean apply(final E elem) {
                for (Pair<String, E> prop : properties) {
                    try {
                        if (!elem.getClass().getField(prop._1()).get(elem)
                                .equals(prop._2())) {
                            return false;
                        }
                    } catch (Exception ex) {
                        //throw new IllegalArgumentException(ex);
                        return false;

                    }
                }
                return true;
            }
        });

    }

    public static <E> List<E> reject(final Iterable<E> list, final Predicate<E> pred) {
        return filter(list, new Predicate<E>() {
            @Override
            public Boolean apply(E input) {
                return !pred.apply(input);
            }
        });
    }

    public static <E> boolean every(final Iterable<E> list, final Predicate<E> pred) {
        return filter(list, pred).equals(list);
    }

    public static <E> boolean all(final Iterable<E> list, final Predicate<E> pred) {
        return every(list, pred);
    }

    public static <E> boolean some(final Iterable<E> list, final Predicate<E> pred) {
        return find(list, pred) != null;
    }

    public static <E> boolean any(final Iterable<E> list, final Predicate<E> pred) {
        return some(list, pred);
    }

    public static <E> boolean contains(final Iterable<E> list, final E elem) {
        return some(list, new Predicate<E>() {
            @Override
            public Boolean apply(E e) {
                return elem.equals(e);
            }
        });
    }

    public static <E> boolean include(final Iterable<E> list, final E elem) {
        return contains(list, elem);
    }

    public static <E> void invoke(final Iterable<E> list, final String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        invoke(list, methodName, Collections.emptyList());
    }

    public static <E> void invoke(final Iterable<E> list, final String methodName,
                                  final List<Object> args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final List<Class<?>> argTypes = map(args, new Function1<Object, Class<?>>() {
            public Class<?> apply(Object input) {
                return input.getClass();
            }
        });
        final Method method = list.getClass().getMethod(methodName, argTypes.toArray(new Class[argTypes.size()]));
        _.each(list, new Block<E>() {
            public void apply(E input) {
                try {
                    method.invoke(list, args.toArray(new Object[args.size()]));
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    public static <E> List<Object> pluck(final Iterable<E> list,
                                         final String propertyName) throws NoSuchFieldException, SecurityException {
        final Iterator<E> iterator = list.iterator();
        if (iterator.hasNext()) {
            final Field field = iterator.next().getClass().getField(propertyName);
            return _.map(list, new Function1<E, Object>() {
                @Override
                public Object apply(E input) {
                    try {
                        return field.get(input);
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        }
        return Collections.emptyList();
    }

    public static <E extends Comparable<? super E>> E max(final Collection<E> list) {
        return Collections.max(list);
    }

    public static <E, F extends Comparable> E max(final Collection<E> list, final Function1<E, F> func) {
        return Collections.max(list, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return func.apply(o1).compareTo(func.apply(o2));
            }
        });
    }

    public static <E extends Comparable<? super E>> E min(final Collection<E> list) {
        return Collections.min(list);
    }

    public static <E, F extends Comparable> E min(final Collection<E> list, final Function1<E, F> func) {
        return Collections.min(list, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return func.apply(o1).compareTo(func.apply(o2));
            }
        });
    }

    public static <E, T extends Comparable<? super T>> List<E> sortBy(final Iterable<E> list, final Function1<E, T> func) {
        final List<E> sortedList = new ArrayList<E>();
        _.each(list, new Block<E>() {
            @Override
            public void apply(E element) {
                sortedList.add(element);
            }
        });
        Collections.sort(sortedList, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return func.apply(o1).compareTo(func.apply(o2));
            }
        });
        return sortedList;
    }

    public static <K, E> Map<K, List<E>> groupBy(final Iterable<E> list, final Function1<E, K> func) {
        final Map<K, List<E>> retVal = new HashMap<K, List<E>>();
        for (E e : list) {
            final K key = func.apply(e);
            List<E> val;
            if (retVal.containsKey(key)) {
                val = retVal.get(key);
            } else {
                val = new ArrayList<E>();
            }
            val.add(e);
            retVal.put(key, val);
        }
        return retVal;
    }

    public static <K, E> Map<K, List<E>> indexBy(final Iterable<E> list, final String property) {
        return groupBy(list, new Function1<E, K>() {
            @Override
            public K apply(E elem) {
                try {
                    return elem.getClass().getField(property).getClass(elem);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    public static <K, E> Map<K, Integer> countBy(final Iterable<E> list, Function1<E, K> func) {
        final Map<K, Integer> retVal = new HashMap<K, Integer>();
        for (E e : list) {
            final K key = func.apply(e);
            if (retVal.containsKey(key)) {
                retVal.put(key, 1 + retVal.get(key));
            } else {
                retVal.put(key, 1);
            }
        }
        return retVal;
    }

    public static <E> List<E> shuffle(final Iterable<E> list) {
        final List<E> copy = new ArrayList<E>();
        for (E elem: list) {
            copy.add(elem);
        }
        Collections.shuffle(copy);
        return copy;
    }

    public static <E> E sample(final List<E> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <E> List<E> sample(final List<E> list, final int howMany) {
        final List<E> samples = new ArrayList<E>();
        final List<E> listCopy = new ArrayList<E>(list);
        while (samples.size() <= howMany || !listCopy.isEmpty()) {
            E sample = sample(listCopy);
            listCopy.remove(sample);
            samples.add(sample);
        }
        return samples;
    }

    public static <E, T> Function<T> bind(final Function1<T>)
}

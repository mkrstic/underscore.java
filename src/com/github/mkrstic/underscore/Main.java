package com.github.mkrstic.underscore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final Block<Integer> printlnFunc = new Block<Integer>() {
            @Override
            public void apply(Integer input) {
                System.out.println(input);
            }
        };
        {


            List<String> list = Arrays.asList("prvi", "drugi", "treci");

            _.each(list, new Block<String>() {
                @Override
                public void apply(String input) {
                    System.out.println("elem: " + input);
                }
            });

            Collection<Integer> listSizes = _.map(list, new Function1<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return input.length();
                }
            });

            _.each(listSizes, printlnFunc);
        }

        // Sum example
        {
            Function2<Integer, Integer, Integer> sumFunc = new Function2<Integer, Integer, Integer>() {
                @Override
                public Integer apply(Integer accumulator, Integer value) {
                    return accumulator + value;
                }
            };
            Collection<Integer> list = Arrays.asList(1, 2, 3);
            Integer sum = _.reduce(list, 0, sumFunc);
            System.out.println("sum: " + sum);
        }

        {
            Collection<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
            System.out.println("Even elems:");
            _.each(_.filter(list, new Predicate<Integer>() {
                @Override
                public Boolean apply(Integer input) {
                    return input % 2 == 0;
                }
            }), printlnFunc);
        }

        {
            Collection<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>() {{
                add(new Pair<String, Integer>("abc", 3));
                add(new Pair<String, Integer>("afu2", 1));
                add(new Pair<String, Integer>("cdef", 4));
                add(new Pair<String, Integer>("nkaden", 6));
            }};
            _.reject(list, new Predicate<Pair<String, Integer>>() {
                @Override
                public Boolean apply(Pair<String, Integer> input) {
                    return input._1().length() != input._2();
                }
            });
        }

        {
            Collection<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6);
            System.out.println("Sorted list: ");
            _.each(_.sortBy(list2, new Function1<Integer, Double>() {
                @Override
                public Double apply(Integer input) {
                    return Math.sin((double) input);
                }
            }), printlnFunc);


        }


    }


}


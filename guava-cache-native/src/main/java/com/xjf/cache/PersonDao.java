package com.xjf.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xjf
 * @date 2020/2/9 20:51
 */
public class PersonDao {

    private static List<Person> personList = Arrays.asList(
            new Person("1", "xjf", "123456"),
            new Person("2", "dale", "123456"),
            new Person("3", "盖伦", "123456"),
            new Person("4", "布隆", "123456"),
            new Person("5", "提莫", "123456")
    );

    public Person getPerson(String id){
        List<Person> resultList = personList.stream().filter(person -> id.equals(person.getId())).collect(Collectors.toList());

        return Optional.ofNullable(resultList).orElse(new ArrayList<>()).get(0);
    }
}

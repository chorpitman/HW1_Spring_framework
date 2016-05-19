package com.epam.utils;

import com.epam.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Pagination {
    public static List<User> usersPagination(HashMap<Integer, Object> map, String name, int pageSize, int pageNum) {
        //получаю всех юзеров с искомым именем
        List<User> matchingUserList = new ArrayList<>();

        for (Object user : map.values()) {
            if (((User) user).getName().equals(name)) {
                matchingUserList.add(((User) user));
            }
        }

        if (pageSize == 0 || name.isEmpty() || pageNum == 0) {
            System.out.println("Wrong param");
            return Collections.emptyList();
        }

        //Находим максимальное количество страниц
        int maxPageSize = 0;
        if ((matchingUserList.size()) % pageSize == 0)
            maxPageSize = matchingUserList.size() / pageSize;
        else {
            maxPageSize = matchingUserList.size() / pageSize + 1;
        }

        //Условие
        if (pageNum > maxPageSize) {
            System.out.println("Wrong param - page size");
            return Collections.emptyList();
        }

        //Находим начальную страницу
        int startPage = 0;
        if (maxPageSize >= 1) {
            startPage = 1;
        }

        //Находим начальный индекс элемента для добавления в возвращаемую коллекцию
        int indexFrom = (pageNum - 1) * pageSize + 1;

        //Находим последний индекс элемента для добавления в возвращаемую коллекцию
        int indexTo = pageNum * pageSize;
        if (indexTo > matchingUserList.size()) {
            indexTo = matchingUserList.size();
        }
        return matchingUserList.subList(indexFrom-1, indexTo);
    }
}

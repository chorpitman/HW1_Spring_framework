package com.epam.utils;

import java.util.Collections;
import java.util.List;

public class Pagination {

    // TODO what is necessary???

    // TODO rename the method?
    public static <T> List<T> getData(List<T> entityList, int pageSize, int pageNum) {
        if (pageSize == 0 || pageNum == 0) {//FIXME recheck the condition!!!
            return Collections.emptyList();
        }

        int maxPage = (entityList.size() + pageSize - 1) / pageSize;
        if (pageNum > maxPage) {
            return Collections.emptyList();
        }

        int start = (pageNum - 1) * pageSize;
        int finish = pageNum * pageSize;
        if (finish > entityList.size()) {
            finish = entityList.size();
        }
        return entityList.subList(start, finish);
    }
}
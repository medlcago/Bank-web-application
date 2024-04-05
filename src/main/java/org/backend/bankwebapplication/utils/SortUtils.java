package org.backend.bankwebapplication.utils;

import org.springframework.data.domain.Sort;

public class SortUtils {
    /**
     * Построение объекта Sort на основе параметров сортировки.
     *
     * @param sort  Свойство для сортировки (может быть null)
     * @param order Направление сортировки ("asc" для сортировки по возрастанию, "desc" для сортировки по убыванию)
     * @return Объект Sort для заданных параметров сортировки
     */
    public static Sort buildSort(String sort, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order != null && order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        String[] sortProperties = {sort != null ? sort : "id"};
        return Sort.by(direction, sortProperties);
    }
}
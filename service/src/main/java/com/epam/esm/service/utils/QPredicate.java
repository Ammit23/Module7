package com.epam.esm.service.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {
    private List<Predicate> predicates = new ArrayList<>();

    public <T> QPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public Predicate buildAnd() {
        if (predicates.size() == 0) {
            return new BooleanBuilder();
        }
        return ExpressionUtils.allOf(predicates);
    }

    public static QPredicate builder() {
        return new QPredicate();
    }
}

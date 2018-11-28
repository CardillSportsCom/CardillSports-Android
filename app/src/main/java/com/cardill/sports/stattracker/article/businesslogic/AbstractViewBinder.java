package com.cardill.sports.stattracker.article.businesslogic;

public interface AbstractViewBinder<T> {
    void onDataLoaded(T data);
}
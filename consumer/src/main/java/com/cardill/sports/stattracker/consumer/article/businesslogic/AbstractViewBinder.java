package com.cardill.sports.stattracker.consumer.article.businesslogic;

public interface AbstractViewBinder<T> {
    void onDataLoaded(T data);
}
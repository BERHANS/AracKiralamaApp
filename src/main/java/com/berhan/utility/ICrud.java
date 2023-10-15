package com.berhan.utility;

import java.util.List;
import java.util.Optional;

public interface ICrud<T,ID> {

    T save(T entity);
    Iterable<T> saveAll(Iterable<T> entities);
    void delete(T entity);
    void deleteById(ID id);
    List<T> findAll();
    Optional<T> findById(ID id);
    boolean existById(ID id);
    List<T> findByEntity(T entity);
    List<T> findByColumnNameAndValue(String cloumnName, String value);

}

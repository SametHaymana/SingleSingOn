package com.sso.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface CrudRepository<T, ID> extends Repository<T, ID> {

  <S extends T> S save(S entity);
  
  <S extends T> Iterable<S> saveAll(Iterable<S> entities);
  
  Optional<T> findById(ID id);

  Optional<T> findByUid(UUID uid);

  boolean existsById(ID id);

  boolean existsByUid(UUID uid);
  
  Iterable<T> findAll();
  
  Iterable<T> findAllById(Iterable<ID> ids);
  
  long count();

  @Modifying
  @Query("UPDATE Client c SET c.deletedAt = CURRENT_TIMESTAMP WHERE c.id = :id")
  void softDelete(UUID id);
  
  /* 
    void deleteById(ID id);
    
    void delete(T entity);
    
    void deleteAllById(Iterable<? extends ID> ids);
    
    void deleteAll(Iterable<? extends T> entities);
    
    void deleteAll();
  */
}
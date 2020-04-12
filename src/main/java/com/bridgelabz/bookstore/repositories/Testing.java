package com.bridgelabz.bookstore.repositories;

import com.bridgelabz.bookstore.models.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class Testing implements UserRepository {

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public List<UserEntity> findAll( Sort sort ) {
        return null;
    }

    @Override
    public Page<UserEntity> findAll( Pageable pageable ) {
        return null;
    }

    @Override
    public List<UserEntity> findAllById( Iterable<Long> longs ) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById( Long aLong ) {

    }

    @Override
    public void delete( UserEntity entity ) {

    }

    @Override
    public void deleteAll( Iterable<? extends UserEntity> entities ) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends UserEntity> S save( S entity ) {
        return null;
    }

    @Override
    public <S extends UserEntity> List<S> saveAll( Iterable<S> entities ) {
        return null;
    }

    @Override
    public Optional<UserEntity> findById( Long aLong ) {
        return Optional.empty();
    }

    @Override
    public boolean existsById( Long aLong ) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserEntity> S saveAndFlush( S entity ) {
        return null;
    }

    @Override
    public void deleteInBatch( Iterable<UserEntity> entities ) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserEntity getOne( Long aLong ) {
        return null;
    }

    @Override
    public <S extends UserEntity> Optional<S> findOne( Example<S> example ) {
        return Optional.empty();
    }

    @Override
    public <S extends UserEntity> List<S> findAll( Example<S> example ) {
        return null;
    }

    @Override
    public <S extends UserEntity> List<S> findAll( Example<S> example, Sort sort ) {
        return null;
    }

    @Override
    public <S extends UserEntity> Page<S> findAll( Example<S> example, Pageable pageable ) {
        return null;
    }

    @Override
    public <S extends UserEntity> long count( Example<S> example ) {
        return 0;
    }

    @Override
    public <S extends UserEntity> boolean exists( Example<S> example ) {
        return false;
    }

    @Override
    public UserEntity findOneByUserName( String userName ) {
        return null;
    }

    @Override
    public boolean existsByUserName( String userName ) {
        return false;
    }
}

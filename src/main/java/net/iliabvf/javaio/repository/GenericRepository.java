package net.iliabvf.javaio.repository;

import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;

public interface GenericRepository<T,ID> {

    default ID addModelToFile(T model) throws CreationException, ReadingException{
        return null;
    }

    default T findByName(String searchRoute) throws ReadingException{
        return null;
    }

    default Boolean removeModelFromFile(T model) throws DeleteException{
        return null;
    }

    default T findById(Integer id) throws ReadingException{
        return null;
    }

    default ID save(T skill){
        return null;
    }

    default void deleteById(ID id){

    }

}

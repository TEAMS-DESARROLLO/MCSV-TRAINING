package com.bussinesdomain.training.services.impl;



import java.util.List;

import org.springframework.data.domain.Page;

import com.bussinesdomain.training.exception.ModelNotFoundException;
import com.bussinesdomain.training.exception.RepositoryException;
import com.bussinesdomain.training.commons.Filter;
import com.bussinesdomain.training.commons.IBaseInterfaceService;
import com.bussinesdomain.training.commons.SortModel;
import com.bussinesdomain.training.repository.IGenericRepository;


public abstract class CRUDImpl<T,ID> implements IBaseInterfaceService<T,ID>  {

    protected abstract IGenericRepository<T,ID> getRepo();

    @Override
    public Long count() {
        return getRepo().count();
    }

    @Override
    public T create(T entidad) {

        return getRepo().save(entidad);
    }

    @Override
    public void delete(T entidad) {
        getRepo().delete(entidad);
        
    }

    @Override
    public void deleteById(ID id) {
        getRepo().findById(id).orElseThrow(()->new ModelNotFoundException("ID NOT FOUND " + id )) ;
        try {
            getRepo().deleteById(id) ;
            
        } catch (Exception e) {
            throw new RepositoryException("DELETE ERROR, THE ID BEING USED IN OTHER RECORDS");
        }
        
    }

    @Override
    public Boolean exists(ID id) {
        return getRepo().existsById(id);
    }

    @Override
    public List<T> getAll() {
        return getRepo().findAll();
    }

    @Override
    public Page<?> pagination(Integer pagenumber, Integer rows, List<SortModel> sortModel, Filter filter) {
        return null;
    }

    @Override
    public T readById(ID id) {
        T rtn = getRepo().findById(id).orElseThrow(()->new ModelNotFoundException("ID NOT FOUND " + id)) ;
        return  rtn;
    }

    @Override
    public T update(T entidad, ID id) {
        return getRepo().save(entidad);
    }


    
}

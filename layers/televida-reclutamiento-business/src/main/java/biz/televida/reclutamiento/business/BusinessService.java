/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package biz.televida.reclutamiento.business;

import java.util.List;

/**
 *
 * @author genaro
 * @param <T>
 */
public interface BusinessService<T> {

    public List<T> getAll();

    public T getById(Long id);

    public T createOrUpdate(T dto) throws Exception;

}

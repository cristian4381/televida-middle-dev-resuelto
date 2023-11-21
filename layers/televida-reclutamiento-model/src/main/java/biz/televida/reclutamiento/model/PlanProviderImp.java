/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.model;

import biz.televida.provider.repository.RepositoryProviderImp;
import biz.televida.reclutamiento.model.entity.Plan;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author genaro
 */
public class PlanProviderImp extends RepositoryProviderImp<Plan> implements PlanProvider {

    public PlanProviderImp(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Plan.class);
    }

    public PlanProviderImp(EntityManagerFactory entityManagerFactory, long cacheSize) {
        super(entityManagerFactory, Plan.class, cacheSize);
    }

}

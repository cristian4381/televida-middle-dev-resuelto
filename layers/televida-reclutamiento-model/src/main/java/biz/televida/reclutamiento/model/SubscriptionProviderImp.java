/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.model;

import biz.televida.provider.repository.RepositoryProviderImp;
import biz.televida.reclutamiento.model.entity.Subscription;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author genaro
 */
public class SubscriptionProviderImp extends RepositoryProviderImp<Subscription> implements SubscriptionProvider {

    public SubscriptionProviderImp(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Subscription.class);
    }

    public SubscriptionProviderImp(EntityManagerFactory entityManagerFactory, long cacheSize) {
        super(entityManagerFactory, Subscription.class, cacheSize);
    }

}

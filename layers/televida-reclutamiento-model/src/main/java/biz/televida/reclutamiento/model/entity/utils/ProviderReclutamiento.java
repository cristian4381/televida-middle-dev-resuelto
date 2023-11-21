/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.model.entity.utils;

import biz.televida.provider.core.DataProvider;
import biz.televida.provider.core.FilterBuilder;
import biz.televida.provider.core.Page;
import biz.televida.provider.imp.DataProviderFactory;
import biz.televida.reclutamiento.model.PlanProvider;
import biz.televida.reclutamiento.model.SubscriptionPaymentsProvider;
import biz.televida.reclutamiento.model.SubscriptionProvider;
import biz.televida.reclutamiento.model.entity.Plan;
import biz.televida.reclutamiento.model.entity.Subscription;
import biz.televida.reclutamiento.model.entity.SubscriptionPayments;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author genaro
 */
public class ProviderReclutamiento {

    private static final String PROVIDERS = "reclutamientoProviders";

    private static Object getProvider(Class clazz) {
        DataProviderFactory factory = new DataProviderFactory();
        return factory.make(PROVIDERS, clazz);
    }

    public static void operations(Object genericDataProvider, Object object) {
        DataProvider dataProvider = (DataProvider) genericDataProvider;
        dataProvider.create(object);
        dataProvider.update(object, object);
        dataProvider.delete(object);
        FilterBuilder filterBuilder = dataProvider.makeFilterBuilder();
        Iterator iterator = dataProvider.find(filterBuilder, Page.ALL, null);
        if (iterator.hasNext()) {
            Object oldObject = iterator.next();
            dataProvider.update(oldObject, oldObject);
        }
    }

    public static void create(Object genericDataProvider, Object object) {
        DataProvider dataProvider = (DataProvider) genericDataProvider;
        dataProvider.create(object);
    }

    public static void update(Object genericDataProvider, Object object) {
        DataProvider dataProvider = (DataProvider) genericDataProvider;
        dataProvider.update(object, object);
    }

    public static void delete(Object genericDataProvider, Object object) {
        DataProvider dataProvider = (DataProvider) genericDataProvider;
        dataProvider.delete(object);
    }

    public static Object get(Object genericDataProvider, Long id) {
        DataProvider dataProvider = (DataProvider) genericDataProvider;
        return dataProvider.findById(id);
    }

    public static List<Object> get(Object genericDataProvider) {
        DataProvider dataProvider = (DataProvider) genericDataProvider;
        FilterBuilder filterBuilder = dataProvider.makeFilterBuilder();
        Iterator iterator = dataProvider.find(filterBuilder, Page.ALL, null);
        List<Object> objects = new ArrayList<>();
        if (iterator.hasNext()) {
            objects.add(iterator.next());
        }
        return objects;
    }

    public static PlanProvider getPlanProvider() {
        return (PlanProvider) getProvider(Plan.class);
    }

    public static SubscriptionProvider getSubscriptionProvider() {
        return (SubscriptionProvider) getProvider(Subscription.class);
    }

    public static SubscriptionPaymentsProvider getSubscriptionPaymentsProvider() {
        return (SubscriptionPaymentsProvider) getProvider(SubscriptionPayments.class);
    }
}

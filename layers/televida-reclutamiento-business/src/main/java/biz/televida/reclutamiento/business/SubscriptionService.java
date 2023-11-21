/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business;

import biz.televida.reclutamiento.business.converters.SubscriptionConverter;
import biz.televida.reclutamiento.business.dto.SubscriptionDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import biz.televida.reclutamiento.model.SubscriptionProvider;
import biz.televida.reclutamiento.model.entity.utils.ProviderReclutamiento;
import java.util.List;

/**
 *
 * @author genaro
 */
public class SubscriptionService implements BusinessService<SubscriptionDto> {

    private final SubscriptionProvider subscriptionProvider;
    private final SubscriptionConverter subscriptionConverter;

    public SubscriptionService() {
        this.subscriptionProvider = ProviderReclutamiento.getSubscriptionProvider();
        this.subscriptionConverter = new SubscriptionConverter();

    }

    @Override
    public List<SubscriptionDto> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SubscriptionDto getById(Long subscriptionId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SubscriptionDto createOrUpdate(SubscriptionDto dto) throws ValidateServiceException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

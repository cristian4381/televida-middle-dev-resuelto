/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business;

import biz.televida.reclutamiento.business.converters.SubscriptionConverter;
import biz.televida.reclutamiento.business.dto.SubscriptionDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import biz.televida.reclutamiento.model.SubscriptionProvider;
import biz.televida.reclutamiento.model.entity.Subscription;
import biz.televida.reclutamiento.model.entity.utils.ProviderReclutamiento;
import java.util.Iterator;
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
        Iterator<Subscription> iterator = subscriptionProvider.all("subscriptionId", "DESC", "subscriptionId");
        return subscriptionConverter.fromEntity(iterator);
    }

    @Override
    public SubscriptionDto getById(Long subscriptionId) {
        Subscription subscription = subscriptionProvider.find(subscriptionId);
        return subscriptionConverter.fromEntity(subscription);
    }

    @Override
    public SubscriptionDto createOrUpdate(SubscriptionDto dto) throws ValidateServiceException {
        if (dto.getSubscriptionId() == null) {
            return create(dto);
        }

        return update(dto);
    }

    private SubscriptionDto create(SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionConverter.fromDto(subscriptionDto);
        subscriptionProvider.add(subscription);
        return subscriptionConverter.fromEntity(subscription);
    }

    private SubscriptionDto update(SubscriptionDto subscriptionDto) throws ValidateServiceException {
        Subscription subscriptionOld = subscriptionProvider.find(subscriptionDto.getSubscriptionId());
        Subscription subscription = subscriptionConverter.toUpdate(subscriptionDto, subscriptionOld);

        if (subscription == null) {
            throw new ValidateServiceException("No existe la subscripcion");
        }
        subscriptionProvider.update(subscription);
        return subscriptionConverter.fromEntity(subscription);
    }
    
    public SubscriptionDto getByEmail(String email){
        Subscription subscription = subscriptionProvider.getByField("email", email);
        return subscriptionConverter.fromEntity(subscription);
    }
    
}

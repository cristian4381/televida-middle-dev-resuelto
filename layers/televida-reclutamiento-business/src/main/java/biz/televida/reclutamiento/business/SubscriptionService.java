/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business;

import biz.televida.reclutamiento.business.converters.SubscriptionConverter;
import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.dto.SubscriptionDto;
import biz.televida.reclutamiento.business.dto.SubscriptionPaymentsDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import biz.televida.reclutamiento.model.SubscriptionProvider;
import biz.televida.reclutamiento.model.entity.Subscription;
import biz.televida.reclutamiento.model.entity.utils.ProviderReclutamiento;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author genaro
 */
@Slf4j
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

    public SubscriptionDto getByEmail(String email) {
        Subscription subscription = subscriptionProvider.getByField("email", email);
        return subscriptionConverter.fromEntity(subscription);
    }

    public SubscriptionDto newSubscription(SubscriptionDto subscriptionDto) throws ValidateServiceException {
        PlanService planService = new PlanService();

        if (subscriptionDto.getPayments() == null || subscriptionDto.getPayments().isEmpty()) {
            throw new ValidateServiceException("El tipo de suscripcion es obligatorio");
        }

        PlanDto planDto = planService.getById(subscriptionDto.getPlan().getPlanId());

        if (planDto == null) {
            throw new ValidateServiceException("El plan es obligatorio");
        }
        LocalDate nextPaymentDate = LocalDate.now().plusDays(planDto.getPaidDays());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(nextPaymentDate.atStartOfDay(defaultZoneId).toInstant());
        String subscriptionType = subscriptionDto.getPayments().get(0).getSubscriptionType();
        
        subscriptionDto.setPayments(new ArrayList<>());
        subscriptionDto.setPlan(planDto);
        subscriptionDto.setNextPaymentDate(date);
        

        SubscriptionPaymentsDto subscriptionPayments = SubscriptionPaymentsDto.builder()
                .payment(subscriptionDto.getAmount())
                .paymentDate(new Date())
                .subscriptionType(subscriptionType)
                .build();
        
        
        subscriptionDto.getPayments().add(subscriptionPayments);
        
        log.debug("Nueva suscripcion => ",subscriptionDto);

        return create(subscriptionDto);
    }

}

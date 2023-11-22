/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business.converters;

import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.dto.SubscriptionDto;
import biz.televida.reclutamiento.business.dto.SubscriptionPaymentsDto;
import biz.televida.reclutamiento.model.entity.Plan;
import biz.televida.reclutamiento.model.entity.Subscription;
import biz.televida.reclutamiento.model.entity.SubscriptionPayments;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author genaro
 */
public class SubscriptionConverter extends AbstractConverter<Subscription, SubscriptionDto> {

    private final PlanConverter planConverter = new PlanConverter();
    private final SubscriptionPaymentsConverter subscriptionPaymentsConverter = new SubscriptionPaymentsConverter();

    @Override
    public Subscription fromDto(SubscriptionDto dto) {
        if (dto == null) {
            return null;
        }
        Plan plan = planConverter.fromDto(dto.getPlan());

        Subscription subscription = Subscription.builder()
                .subscriptionId(dto.getSubscriptionId())
                .plan(plan)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .nextPaymentDate(dto.getNextPaymentDate())
                .amount(dto.getAmount())
                .build();

        List<SubscriptionPayments> payments = new ArrayList<>();

        if (dto.getPayments() != null) {
            for (SubscriptionPaymentsDto subscriptionPaymentsDto : dto.getPayments()) {
                SubscriptionPayments payment = subscriptionPaymentsConverter.fromDto(subscriptionPaymentsDto);
                payment.setSubscription(subscription);
                payments.add(payment);
            }
        }

        subscription.setPayments(payments);
        return subscription;

    }

    @Override
    public SubscriptionDto fromEntity(Subscription entity) {
        if (entity == null) {
            return null;
        }

        PlanDto planDto = planConverter.fromEntity(entity.getPlan());

        SubscriptionDto subscriptionDto = SubscriptionDto.builder()
                .subscriptionId(entity.getSubscriptionId())
                .plan(planDto)
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .nextPaymentDate(entity.getNextPaymentDate())
                .amount(entity.getAmount())
                .fullName(entity.getFirstName() + " " + entity.getLastName())
                .build();
        List<SubscriptionPaymentsDto> paymentsDtos = subscriptionPaymentsConverter.fromEntity(entity.getPayments());
        
        subscriptionDto.setPayments(paymentsDtos);

        return subscriptionDto;

    }

    public Subscription toUpdate(SubscriptionDto subscriptionDto, Subscription subscription) {

        if (subscriptionDto == null || subscription == null) {
            return null;
        }
        
        

        Double amount = subscriptionDto.getAmount() == null ? subscription.getAmount() : subscriptionDto.getAmount();
        String email = subscriptionDto.getEmail() == null || subscriptionDto.getEmail().isEmpty() ? subscription.getEmail() : subscriptionDto.getEmail();
        String firstName = subscriptionDto.getFirstName() == null || subscriptionDto.getFirstName().isEmpty() ? subscription.getFirstName() : subscriptionDto.getFirstName();
        String lastName = subscriptionDto.getLastName() == null || subscriptionDto.getLastName().isEmpty() ? subscription.getLastName() : subscriptionDto.getLastName();
        Date nextPaymentDate = subscriptionDto.getNextPaymentDate() == null ? subscription.getNextPaymentDate() : subscriptionDto.getNextPaymentDate();
        
        String phone = subscriptionDto.getPhone() == null || subscriptionDto.getPhone().isEmpty() ? subscription.getPhone() : subscriptionDto.getPhone();
        Plan plan = subscriptionDto.getPlan() == null ? subscription.getPlan() : planConverter.fromDto(subscriptionDto.getPlan());

        subscription.setAmount(amount);
        subscription.setEmail(email);
        subscription.setFirstName(firstName);
        subscription.setLastName(lastName);
        subscription.setNextPaymentDate(nextPaymentDate);

        subscription.setPhone(phone);
        subscription.setPlan(plan);
        
        
        List<SubscriptionPayments> payments = new ArrayList<>(); 
        
        if(subscriptionDto.getPayments() != null){
            for (SubscriptionPaymentsDto subscriptionPaymentsDto : subscriptionDto.getPayments()) {
                SubscriptionPayments payment = subscriptionPaymentsConverter.fromDto(subscriptionPaymentsDto);
                payment.setSubscription(subscription);
                payments.add(payment);
            }
        
        }else{
            payments = subscription.getPayments();
        }

        subscription.setPayments(payments);
        return subscription;
    }
}

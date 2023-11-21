/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business.converters;

import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.dto.SubscriptionDto;
import biz.televida.reclutamiento.model.entity.Plan;
import biz.televida.reclutamiento.model.entity.Subscription;

/**
 *
 * @author genaro
 */
public class SubscriptionConverter extends AbstractConverter<Subscription, SubscriptionDto> {

    private final PlanConverter planConverter = new PlanConverter();

    @Override
    public Subscription fromDto(SubscriptionDto dto) {
        if (dto == null) {
            return null;
        }
        Plan plan = planConverter.fromDto(dto.getPlan());

        return Subscription.builder()
                .subscriptionId(dto.getSubscriptionId())
                .plan(plan)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .nextPaymentDate(dto.getNextPaymentDate())
                .amount(dto.getAmount())
                .build();

    }

    @Override
    public SubscriptionDto fromEntity(Subscription entity) {
        if (entity == null) {
            return null;
        }

        PlanDto planDto = planConverter.fromEntity(entity.getPlan());

        return SubscriptionDto.builder()
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

    }

}

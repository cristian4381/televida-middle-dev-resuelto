/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business.converters;

import biz.televida.reclutamiento.business.dto.SubscriptionPaymentsDto;
import biz.televida.reclutamiento.model.entity.SubscriptionPayments;
import biz.televida.reclutamiento.model.entity.enums.SubscriptionType;

/**
 *
 * @author cristian
 */
public class SubscriptionPaymentsConverter extends AbstractConverter<SubscriptionPayments, SubscriptionPaymentsDto> {

    @Override
    public SubscriptionPayments fromDto(SubscriptionPaymentsDto dto) {
        return SubscriptionPayments.builder()
                .payment(dto.getPayment())
                .paymentDate(dto.getPaymentDate())
                .subscriptionPaymentId(dto.getSubscriptionPaymentId())
                .subscriptionType(SubscriptionType.valueOf(dto.getSubscriptionType()))
                .build();
    }

    @Override
    public SubscriptionPaymentsDto fromEntity(SubscriptionPayments entity) {
        return SubscriptionPaymentsDto.builder()
                .payment(entity.getPayment())
                .paymentDate(entity.getPaymentDate())
                .subscriptionPaymentId(entity.getSubscriptionPaymentId())
                .subscriptionType(entity.getSubscriptionType().name())
                .build();
    }

}

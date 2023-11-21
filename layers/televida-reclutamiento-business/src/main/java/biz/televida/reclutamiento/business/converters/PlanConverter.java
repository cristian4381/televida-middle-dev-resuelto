/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business.converters;

import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.model.entity.Plan;
import biz.televida.reclutamiento.model.entity.enums.PlanTypes;

/**
 *
 * @author genaro
 */
public class PlanConverter extends AbstractConverter<Plan, PlanDto> {

    @Override
    public Plan fromDto(PlanDto dto) {
        if (dto == null) {
            return null;
        }

        return Plan.builder()
                .planId(dto.getPlanId())
                .description(dto.getDescription())
                .pricing(dto.getPricing())
                .paidDays(dto.getPaidDays())
                .type(PlanTypes.valueOf(dto.getType()))
                .build();

    }

    @Override
    public PlanDto fromEntity(Plan entity) {
        if (entity == null) {
            return null;
        }

        return PlanDto.builder()
                .planId(entity.getPlanId())
                .description(entity.getDescription())
                .pricing(entity.getPricing())
                .paidDays(entity.getPaidDays())
                .type(entity.getType().name())
                .build();
    }

    public Plan toUpdate(PlanDto planDto, Plan plan) {
        if (planDto == null && plan == null) {
            return null;
        }

        String description = planDto.getDescription().isEmpty() ? plan.getDescription() : planDto.getDescription();
        Double pricing = planDto.getPricing() == null ? plan.getPricing() : planDto.getPricing();
        Integer paidDays = planDto.getPaidDays() == null ? plan.getPaidDays() : planDto.getPaidDays();
        PlanTypes planTypes = planDto.getType().isEmpty() ? plan.getType() : PlanTypes.valueOf(planDto.getType());

        plan.setDescription(description);
        plan.setPricing(pricing);
        plan.setPaidDays(paidDays);
        plan.setType(planTypes);
        return plan;

    }
}

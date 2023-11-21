/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business;

import biz.televida.reclutamiento.business.converters.PlanConverter;
import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import biz.televida.reclutamiento.model.PlanProvider;
import biz.televida.reclutamiento.model.entity.Plan;
import biz.televida.reclutamiento.model.entity.utils.ProviderReclutamiento;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author genaro
 */
public class PlanService implements BusinessService<PlanDto> {

    private final PlanProvider planProvider;
    private final PlanConverter planConverter;

    public PlanService() {
        this.planProvider = ProviderReclutamiento.getPlanProvider();
        this.planConverter = new PlanConverter();
    }

    @Override
    public List<PlanDto> getAll() {
        Iterator<Plan> iterator = planProvider.all("planId", "DESC", "planId");
        return planConverter.fromEntity(iterator);
    }

    @Override
    public PlanDto getById(Long planId) {
        Plan plan = planProvider.find(planId);
        return planConverter.fromEntity(plan);
    }

    @Override
    public PlanDto createOrUpdate(PlanDto planDto) throws ValidateServiceException {
        if (planDto.getPlanId() == null) {
            return create(planDto);
        } else {
            return update(planDto);
        }
    }

    private PlanDto create(PlanDto planDto) {
        Plan plan = planConverter.fromDto(planDto);
        planProvider.add(plan);
        return planConverter.fromEntity(plan);
    }

    private PlanDto update(PlanDto planDto) throws ValidateServiceException {
        Plan oldPlan = planProvider.find(planDto.getPlanId());
        Plan plan = planConverter.toUpdate(planDto, oldPlan);
        if (plan == null) {
            throw new ValidateServiceException("No fue posible transformar los valores de la solicitud al plan");
        }
        planProvider.update(plan);
        return planConverter.fromEntity(plan);
    }

    public PlanDto getByDescription(String description) {
        Plan plan = planProvider.getByField("description", description);
        return planConverter.fromEntity(plan);

    }
}

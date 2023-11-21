/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package biz.televida.reclutamiento.business;

import biz.televida.integration.core.Context;
import biz.televida.integration.core.SpringBeanContext;
import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import biz.televida.reclutamiento.model.entity.enums.PlanTypes;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author genaro
 */
public class PlanServiceTest {

    private static final String PLAN_MENSUAL = "Plan mensual";
    private static final Double PRICING = 10D;
    private static final Integer PAID_DAYS = 30;
    private static final Long PLAN_ID = 1L;

    private final PlanService planService;

    public PlanServiceTest() {
        System.out.println("-------------------------- PlanServiceTest -------------------------");
        this.planService = new PlanService();
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("-------------------------- PlanServiceTest -------------------------");
        Context.closeContexts();
    }

    @BeforeClass
    public static void beforeClass() {
        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("spring-context.xml");
        SpringBeanContext beanContext = new SpringBeanContext(springContext);
        Context.setBeanContext(beanContext);
    }

    /**
     * Test of getAll method, of class PlanService.
     */
    @Test
    public void testGetAll() {
        try {
            System.out.println("getAll");
            List<PlanDto> planDtos = planService.getAll();
            planDtos.forEach(p -> System.out.println(p));
        } catch (Exception e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }

    /**
     * Test of getByPlanId method, of class PlanService.
     */
    @Test
    public void testGetByPlanId() {
        try {
            System.out.println("testGetByPlanId");
            PlanDto planDto = planService.getById(PLAN_ID);
            if (planDto == null) {
                System.out.println("Plan [" + planDto + "]");
                assertNull(planDto);
            } else {
                System.out.println("Plan [" + planDto + "]");
                assertNotNull(planDto);
            }
        } catch (Exception e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }

    /**
     * Test of createOrUpdate method, of class PlanService.
     */
    @Test
    public void testCreate() {
        try {
            System.out.println("testCreate");

            PlanDto existPlan = planService.getByDescription(PLAN_MENSUAL);
            if (existPlan == null) {
                PlanDto planDto = PlanDto.builder()
                        .description(PLAN_MENSUAL)
                        .pricing(PRICING)
                        .paidDays(PAID_DAYS)
                        .type(PlanTypes.SILVER.name())
                        .build();
                planService.createOrUpdate(planDto);
                assertNull(existPlan);
            } else {
                assertNotNull(existPlan);
            }

        } catch (ValidateServiceException e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }

    /**
     * Test of createOrUpdate method, of class PlanService.
     */
    @Test
    public void testUpdate() {
        try {
            System.out.println("testUpdate");

            PlanDto planDto = PlanDto.builder()
                    .planId(PLAN_ID)
                    .description(PLAN_MENSUAL)
                    .pricing(11D)
                    .paidDays(PAID_DAYS)
                    .type(PlanTypes.SILVER.name())
                    .build();
            planService.createOrUpdate(planDto);
        } catch (ValidateServiceException e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }

}

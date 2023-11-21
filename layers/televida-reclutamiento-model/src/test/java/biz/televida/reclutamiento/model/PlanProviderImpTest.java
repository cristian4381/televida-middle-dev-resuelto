/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package biz.televida.reclutamiento.model;

import biz.televida.integration.core.Context;
import biz.televida.integration.core.SpringBeanContext;
import biz.televida.reclutamiento.model.entity.Plan;
import biz.televida.reclutamiento.model.entity.enums.PlanTypes;
import biz.televida.reclutamiento.model.entity.utils.ProviderReclutamiento;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author genaro
 */
public class PlanProviderImpTest {

    private static final String PLAN_MENSUAL = "Plan mensual";
    private static final Double PRICING = 10D;
    private static final Integer PAID_DAYS = 30;

    private final PlanProvider planProvider;

    public PlanProviderImpTest() {
        System.out.println("-------------------------- PlanProviderImpTest -------------------------");
        this.planProvider = ProviderReclutamiento.getPlanProvider();
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("-------------------------- PlanProviderImpTest -------------------------");
        Context.closeContexts();
    }

    @BeforeClass
    public static void beforeClass() {
        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("spring-context.xml");
        SpringBeanContext beanContext = new SpringBeanContext(springContext);
        Context.setBeanContext(beanContext);
    }

    private Plan getPlan(String description) {
        return planProvider.getByField("description", description);
    }

    @Test
    public void testAdd() {
        try {
            System.out.println("testAdd");
            Plan plan = getPlan(PLAN_MENSUAL);

            if (plan == null) {
                Plan nuevoPlan = Plan.builder()
                        .description(PLAN_MENSUAL)
                        .pricing(PRICING)
                        .paidDays(PAID_DAYS)
                        .type(PlanTypes.SILVER)
                        .build();
                planProvider.add(nuevoPlan);

                assertEquals(PLAN_MENSUAL, nuevoPlan.getDescription());
            } else {
                System.out.println("El plan ya existe");
                assertNotNull(plan);
            }
        } catch (Exception e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package biz.televida.reclutamiento.model;

import biz.televida.integration.core.Context;
import biz.televida.integration.core.SpringBeanContext;
import biz.televida.reclutamiento.model.entity.Plan;
import biz.televida.reclutamiento.model.entity.Subscription;
import biz.televida.reclutamiento.model.entity.enums.PlanTypes;
import biz.televida.reclutamiento.model.entity.utils.ProviderReclutamiento;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author genaro
 */
public class SubscriptionProviderImpTest {

    private static final String PLAN_MENSUAL = "Plan mensual";
    private static final Double PRICING = 10D;
    private static final Integer PAID_DAYS = 30;

    private static final String EMAIL = "jperez@gmail.com";
    private static final String FIRST_NAME = "Juan";
    private static final String LAST_NAME = "Perez";
    private static final String PHONE = "502 4321 5678";

    private final SubscriptionProvider subscriptionProvider;
    private final PlanProvider planProvider;

    public SubscriptionProviderImpTest() {
        System.out.println("---------------------- SubscriptionProviderImpTest ---------------------");
        this.subscriptionProvider = ProviderReclutamiento.getSubscriptionProvider();
        this.planProvider = ProviderReclutamiento.getPlanProvider();
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("---------------------- SubscriptionProviderImpTest ---------------------");
        Context.closeContexts();
    }

    @BeforeClass
    public static void beforeClass() {
        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("spring-context.xml");
        SpringBeanContext beanContext = new SpringBeanContext(springContext);
        Context.setBeanContext(beanContext);
    }

    private Plan getPlan(String description) {
        Plan plan = planProvider.getByField("description", description);
        if (plan == null) {
            Plan nuevoPlan = Plan.builder()
                    .description(description)
                    .pricing(PRICING)
                    .paidDays(PAID_DAYS)
                    .type(PlanTypes.SILVER)
                    .build();
            planProvider.add(nuevoPlan);
            return nuevoPlan;
        } else {
            return plan;
        }
    }

    private Subscription getSubscriptionByEmail(String email) {
        return subscriptionProvider.getByField("email", email);
    }

    @Test
    public void testAdd() {
        try {
            System.out.println("testAdd");
            Subscription subscriptionByJPerez = getSubscriptionByEmail(EMAIL);
            Plan plan = getPlan(PLAN_MENSUAL);

            if (subscriptionByJPerez == null) {
                LocalDate nextPaymentDate = LocalDate.now().plusDays(plan.getPaidDays());
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Date date = Date.from(nextPaymentDate.atStartOfDay(defaultZoneId).toInstant());
                Subscription subscription = Subscription.builder()
                        .plan(plan)
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .email(EMAIL)
                        .phone(PHONE)
                        .nextPaymentDate(date)
                        .amount(plan.getPricing())
                        .build();
                subscriptionProvider.add(subscription);

                assertEquals(EMAIL, subscription.getEmail());
            } else {
                System.out.println("Existe la suscripcion del email[" + EMAIL + "]");
                assertNotNull(subscriptionByJPerez);
            }
        } catch (Exception e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }

}

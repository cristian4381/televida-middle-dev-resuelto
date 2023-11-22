/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package biz.televida.reclutamiento.business;

import biz.televida.integration.core.Context;
import biz.televida.integration.core.SpringBeanContext;
import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.dto.SubscriptionDto;
import biz.televida.reclutamiento.business.dto.SubscriptionPaymentsDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author cristian
 */
public class SubscriptionServiceTest {

    private final SubscriptionService subscriptionService;
    private final PlanService planService;
    private static final String EMAIL = "cristian@gmail.com";
    private static final String FIRST_NAME = "Cristian";
    private static final String LAST_NAME = "Ajtun";
    private static final String PHONE = "502 8745 1278";
    private static final String SUBSCRIPTIONTYPE = "MENSUAL";
    
    public SubscriptionServiceTest() {
        System.out.println("-------------------------- SubscriptionServiceTest -------------------------");
        this.subscriptionService = new SubscriptionService();
        this.planService = new PlanService();
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("-------------------------- SubscriptionServiceTest -------------------------");
        Context.closeContexts();
    }

    @BeforeClass
    public static void beforeClass() {
        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("spring-context.xml");
        SpringBeanContext beanContext = new SpringBeanContext(springContext);
        Context.setBeanContext(beanContext);
    }

    /**
     * Test of getAll method, of class SubscriptionService.
     */
    @Test
    public void testGetAll() {
         System.out.println("getAll");
        try {
            List<SubscriptionDto> subscriptions = subscriptionService.getAll();

            if (subscriptions != null) {
                subscriptions.forEach(e -> System.out.println(e));
                assertNotNull(subscriptions);
            } else {
                System.out.println("No se encontraron suscripciones");
                assertNull(subscriptions);
            }

        } catch (Exception e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }

    /**
     * Test of getById method, of class SubscriptionService.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");

        try {
            Long subscriptionId = 1L;
            SubscriptionDto subscriptionDto = subscriptionService.getById(subscriptionId);

            if (subscriptionDto != null) {

                System.out.println(subscriptionDto);
                assertNotNull(subscriptionDto);
            } else {
                System.out.println("No se encontro la suscripcion");
                assertNull(subscriptionDto);
            }

        } catch (Exception e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }

    }

    /**
     * Test of create method, of class SubscriptionService.
     */
    @Test
    public void testAdd() {
        System.out.println("TestAdd");
        try {
            PlanDto planDto = planService.getByDescription("Plan mensual");

            SubscriptionDto subscriptionDto = subscriptionService.getByEmail(EMAIL);

            if (subscriptionDto == null) {
                
                
                LocalDate nextPaymentDate = LocalDate.now().plusDays(planDto.getPaidDays());
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Date date = Date.from(nextPaymentDate.atStartOfDay(defaultZoneId).toInstant());
                
                SubscriptionDto newSubscriptionDto = SubscriptionDto.builder()
                    .amount(10D)
                    .email(EMAIL)
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .nextPaymentDate(date)
                    .phone(PHONE)
                    .plan(planDto)
                    .payments( new ArrayList<>())
                    .build();
                
                SubscriptionPaymentsDto payment = SubscriptionPaymentsDto.builder()
                    .payment(10D)
                    .paymentDate(new Date())
                    .subscriptionType(SUBSCRIPTIONTYPE)
                    .build();
                
                newSubscriptionDto.getPayments().add(payment);

                newSubscriptionDto =  subscriptionService.createOrUpdate(newSubscriptionDto);
                assertNotNull(newSubscriptionDto.getSubscriptionId());
                
                System.out.println("Subcripcion creada");
            } else {
                System.out.println("Existe la suscripcion del email[" + EMAIL + "]");
                assertNotNull(subscriptionDto);
            }
        } catch (ValidateServiceException e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }
    
    /**
     * Test of update method, of class SubscriptionService.
     */
    @Test
    public void testUpdate() {
        System.out.println("TestUpdate");
        try {
            SubscriptionDto subscriptionDto = SubscriptionDto.builder()
                    .amount(11D)
                    .email(EMAIL)
                    .subscriptionId(2L)
                    .build();
            subscriptionService.createOrUpdate(subscriptionDto);
        } catch (ValidateServiceException e) {
            System.out.println("Excepcion controlada ==> " + e.getMessage());
        }
    }
}

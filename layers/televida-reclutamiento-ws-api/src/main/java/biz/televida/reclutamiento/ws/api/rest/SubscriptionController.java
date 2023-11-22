/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.ws.api.rest;

import biz.televida.reclutamiento.business.SubscriptionService;
import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.dto.SubscriptionDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author cristian
 */
@Slf4j
@Path("/subscription")
@Tag(name = "subscription")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    private SubscriptionService getSubscriptionService() {
        loadSubscriptionService();
        return subscriptionService;
    }

    private synchronized void loadSubscriptionService() {
        if (subscriptionService == null) {
            subscriptionService = new SubscriptionService();
        }
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SubscriptionDto.class)
            ),
            description = "Listado de suscripciones"
    )
    public Response getAll() {
        try {
            List<SubscriptionDto> subscriptionDtos = getSubscriptionService().getAll();
            return Response.ok().entity(subscriptionDtos).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.noContent().build();
        }
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @RequestBody(
            description = "Ejemplo",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\n"
                            + "  \"plan\": {\n"
                            + "    \"planId\": 0\n"
                            + "  },\n"
                            + "  \"firstName\": \"String\",\n"
                            + "  \"lastName\": \"String\",\n"
                            + "  \"email\": \"String\",\n"
                            + "  \"phone\": \"String\",\n"
                            + "  \"amount\": 0,\n"
                            + "  \"payments\": [\n"
                            + "    {\n"
                            + "      \"subscriptionType\": \"String\"\n"
                            + "    }\n"
                            + "  ]\n"
                            + "}")
            )
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SubscriptionDto.class)
            ),
            description = "Crear una nueva suscripcion"
    )
    public Response newSubscription(SubscriptionDto newSubscription) {
        try {
            SubscriptionDto subscriptionDto = getSubscriptionService().newSubscription(newSubscription);
            return Response.ok(subscriptionDto).build();
        } catch (ValidateServiceException e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("invalid data").build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @RequestBody(
            description = "Ejemplo",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\n"
                            + "  \"subscriptionId\": 0,\n"
                            + "  \"firstName\": \"string\",\n"
                            + "  \"lastName\": \"string\",\n"
                            + "  \"email\": \"string\",\n"
                            + "  \"phone\": \"string\",\n"
                            + "  \"nextPaymentDate\": \"2023-11-22T07:27:30.785Z\",\n"
                            + "  \"amount\": 0,\n"
                            + "  \"fullName\": \"string\"\n"
                            + "}")
            )
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlanDto.class)
            ),
            description = "Actualizar Suscripcion"
    )
    public Response updateSubscription(SubscriptionDto newSubscription) {
        try {

            SubscriptionDto subscriptionDto = getSubscriptionService().createOrUpdate(newSubscription);

            return Response.ok().entity(subscriptionDto).build();

        } catch (ValidateServiceException e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("invalid data").build();
        }
    }
}

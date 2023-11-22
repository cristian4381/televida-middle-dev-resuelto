/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.ws.api.rest;

import biz.televida.reclutamiento.business.PlanService;
import biz.televida.reclutamiento.business.dto.PlanDto;
import biz.televida.reclutamiento.business.exceptions.ValidateServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author genaro
 */
@Slf4j
@Path("/plan")
@Tag(name = "plan")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class PlanController {

    private PlanService planService;

    private synchronized void loadPlanService() {
        if (planService == null) {
            planService = new PlanService();
        }
    }

    private PlanService getPlanService() {
        loadPlanService();
        return planService;
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlanDto.class)
            ),
            description = "Listado de planes"
    )
    public Response getAll() {
        try {
            List<PlanDto> planDtos = getPlanService().getAll();
            return Response.ok().entity(planDtos).build();
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
                            + "  \"description\": \"string\",\n"
                            + "  \"pricing\": 0,\n"
                            + "  \"paidDays\": 0,\n"
                            + "  \"type\": \"string\"\n"
                            + "}")
            )
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlanDto.class)
            ),
            description = "Crea un nuevo plan"
    )
    public Response createPlan(PlanDto newPlan) {
        try {

            PlanDto createdPlan = getPlanService().createOrUpdate(newPlan);

            return Response.ok().entity(createdPlan).build();

        } catch (ValidateServiceException e) {
            log.error(e.getMessage());
            return Response.noContent().build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlanDto.class)
            ),
            description = "Actualizar plan"
    )
    public Response updatePlanPrice(PlanDto plan) {
        try {

            PlanDto createdPlan = getPlanService().createOrUpdate(plan);

            return Response.ok().entity(createdPlan).build();

        } catch (ValidateServiceException e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("invalid data").build();
        }
    }
}

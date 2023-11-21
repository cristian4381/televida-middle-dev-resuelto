/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.ws.api.rest;

import biz.televida.reclutamiento.business.PlanService;
import biz.televida.reclutamiento.business.dto.PlanDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

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

}

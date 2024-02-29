package org.zydd.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import org.zydd.dto.request.FoodDataReq;
import org.zydd.models.Food;
import org.zydd.services.FoodServices;

import java.util.List;

@Path("/food")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoodResources {
    @Inject
    FoodServices services;

    @GET
    public RestResponse<List<Food>> getAllFood(){
        var response = services.getAllFood();
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public RestResponse<Food> getFoodByiD(@PathParam("id") Long id){
        var response = services.getFoodById(id);
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @POST
    public RestResponse<Food> createFood(FoodDataReq foodDataReq){
        var response = services.createFood(foodDataReq);
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public RestResponse<Food> updateFood(@PathParam("id") Long id,FoodDataReq foodDataReq){
        var response = services.updateFood(id, foodDataReq);
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteCustomer(@PathParam("id") Long id){
        services.deleteFoodById(id);
        return RestResponse.ok();
    }
}

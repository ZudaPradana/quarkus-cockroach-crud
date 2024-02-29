package org.zydd.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import org.zydd.dto.request.OrderDataReq;
import org.zydd.dto.response.OrderDataRes;
import org.zydd.services.OrderServices;

import java.util.List;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResources {
    @Inject
    OrderServices services;

    @GET
    public RestResponse<List<OrderDataRes>> getAllOrders() {
        List<OrderDataRes> orders = services.getAllOrder();
        return RestResponse.ok(orders);
    }

    @POST
    public RestResponse<OrderDataRes> createOrder(@Valid OrderDataReq orderDataReq) {
        OrderDataRes createdOrder = services.createOrder(orderDataReq);
        return RestResponse.ok(createdOrder);
    }
}

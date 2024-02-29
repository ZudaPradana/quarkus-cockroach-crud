package org.zydd.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import org.zydd.dto.request.CustomerDataReq;
import org.zydd.dto.response.CustomerDataRes;
import org.zydd.models.Customer;
import org.zydd.services.CustomerServices;

import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResources {
    @Inject
    CustomerServices services;

    @GET
    public RestResponse<List<Customer>> getAll(){
        var response = services.getAllCustomers();
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public RestResponse<CustomerDataRes> getCustomerById(@PathParam("id") Long id){
        var response = services.getCustomerById(id);
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @POST
    public RestResponse<Customer> createCustomer(CustomerDataReq customerDataReq){
        var response = services.createCustomer(customerDataReq);
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public RestResponse<Customer> updateCustomer(@PathParam("id") Long id, CustomerDataReq customerDataReq){
        var response = services.updateCustomer(id, customerDataReq);
        return RestResponse.ResponseBuilder.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteCustomer(@PathParam("id") Long id){
        services.deleteCustomer(id);
        return RestResponse.ok();
    }
}

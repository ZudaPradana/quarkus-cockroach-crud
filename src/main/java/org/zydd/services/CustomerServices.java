package org.zydd.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.zydd.dto.request.CustomerDataReq;
import org.zydd.dto.response.CustomerDataRes;
import org.zydd.models.Customer;
import org.zydd.repositories.CustomerRepositories;
import org.zydd.utils.ModelMapperConf;

import java.util.List;

@ApplicationScoped
@Slf4j
public class CustomerServices {
    @Inject
    CustomerRepositories repositories;

    @Inject
    ModelMapperConf mapperConf;


    public List<Customer> getAllCustomers(){
        log.info("get all customer data");
        return repositories.listAll();
    }

    public CustomerDataRes getCustomerById(Long id){
        var customer = repositories.findById(id);
        if(customer == null){
            log.error("Id Not Found");
            throw new RuntimeException("Id Not Found");
        }
        log.info("Retrieve id {}", id);
        return mapperConf.toCustomerDTO(customer);
    }

    @Transactional
    public Customer createCustomer(CustomerDataReq customerDataReq){
        Customer customer = Customer.builder()
                .name(customerDataReq.getCustomerName())
                .email(customerDataReq.getCustomerEmail())
                .phoneNumber(customerDataReq.getCustomerPhone())
                .build();
        repositories.persist(customer);
        log.info("create customer");
        return customer;
    }


    @Transactional
    public Customer updateCustomer(Long id, CustomerDataReq customerDataReq){
        var updateCustomer = repositories.findById(id);
        updateCustomer.setName(customerDataReq.getCustomerName());
        updateCustomer.setEmail(customerDataReq.getCustomerEmail());
        updateCustomer.setPhoneNumber(customerDataReq.getCustomerPhone());

        log.info("Customer with id {} is edited", id);
        repositories.persist(updateCustomer);
        return updateCustomer;
    }

    public void deleteCustomer(Long id){
        var customerId = getCustomerById(id).getId();
        repositories.deleteById(customerId);
        log.info("ID {} deleted", id);
    }
}

package org.zydd.controllers;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import org.zydd.models.Customer;

public interface ICustomer extends PanacheEntityResource<Customer, Long> {

}

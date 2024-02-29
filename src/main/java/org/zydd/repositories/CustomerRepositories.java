package org.zydd.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.zydd.models.Customer;

@ApplicationScoped
public class CustomerRepositories implements PanacheRepository<Customer> {

}


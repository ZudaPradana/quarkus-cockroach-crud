package org.zydd.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.zydd.dto.request.OrderDataReq;
import org.zydd.dto.response.CustomerDataRes;
import org.zydd.dto.response.OrderDataRes;
import org.zydd.dto.response.OrderDetailDataRes;
import org.zydd.models.Customer;
import org.zydd.models.Order;
import org.zydd.models.OrderDetail;

import java.lang.reflect.Type;
import java.util.List;

@ApplicationScoped
public class ModelMapperConf {

    private final ModelMapper modelMapper;

    public ModelMapperConf() {
        modelMapper = new ModelMapper();

        // Mapping for Customer and CustomerDTO
        modelMapper.typeMap(Customer.class, CustomerDataRes.class).addMappings(mapper -> {
            mapper.map(Customer::getId, CustomerDataRes::setId);
            mapper.map(Customer::getName, CustomerDataRes::setCustomerName);
        });

        modelMapper.typeMap(Order.class, OrderDataRes.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), OrderDataRes::setId);
            mapper.map(src -> src.getCustomer().getId(), OrderDataRes::setCustomerId);
            // Map other properties as needed
            // Contoh:
            mapper.map(src -> src.getQty(), OrderDataRes::setQty);
            mapper.map(src -> src.getPaymentMethod(), OrderDataRes::setPaymentMethod);
            mapper.map(src -> src.getTotalPrice(), OrderDataRes::setTotalPrice);
            mapper.map(src -> src.getTotalPaid(), OrderDataRes::setTotalPaid);
            mapper.map(src -> src.getTotalChange(), OrderDataRes::setTotalChange);
            mapper.map(src -> src.getOrderDate(), OrderDataRes::setOrderDate);
        });

        modelMapper.typeMap(OrderDataRes.class, Order.class).addMappings(mapper -> {
            // Map properties from OrderDataRes to Order
            // Contoh:
            mapper.map(OrderDataRes::getQty, Order::setQty);
            mapper.map(OrderDataRes::getPaymentMethod, Order::setPaymentMethod);
            mapper.map(OrderDataRes::getTotalPrice, Order::setTotalPrice);
            mapper.map(OrderDataRes::getTotalPaid, Order::setTotalPaid);
            mapper.map(OrderDataRes::getTotalChange, Order::setTotalChange);
            mapper.map(OrderDataRes::getOrderDate, Order::setOrderDate);
        });
    }

    public <T> T map(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <T> List<T> mapList(List<?> sourceList, Class<T> destinationType) {
        return modelMapper.map(sourceList, new TypeToken<List<T>>() {}.getType());
    }

    public CustomerDataRes toCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDataRes.class);
    }

    public Customer toCustomerEntity(CustomerDataRes customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public OrderDataRes toOrderDTO(Order order) {
        return modelMapper.map(order, OrderDataRes.class);
    }

    public Order toOrderEntity(OrderDataReq orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public OrderDataRes toOrderDataRes(Order order) {
        return modelMapper.map(order, OrderDataRes.class);
    }

    public List<OrderDetail> mapOrderDetailList(List<OrderDetailDataRes> orderDetailDataResList) {
        return modelMapper.map(orderDetailDataResList, new TypeToken<List<OrderDetail>>() {}.getType());
    }

    public void updateOrderEntity(OrderDataReq orderDataReq, Order order) {
        modelMapper.map(orderDataReq, order);
    }

    // Add more mapping methods if needed
}

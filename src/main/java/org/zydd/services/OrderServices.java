package org.zydd.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.zydd.dto.request.OrderDataReq;
import org.zydd.dto.response.OrderDataRes;
import org.zydd.dto.response.OrderDetailDataRes;

import org.zydd.models.Customer;
import org.zydd.models.Food;
import org.zydd.models.Order;
import org.zydd.models.OrderDetail;
import org.zydd.repositories.CustomerRepositories;
import org.zydd.repositories.FoodRepositories;
import org.zydd.repositories.OrderRepositories;
import org.zydd.utils.ModelMapperConf;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class OrderServices {
    @Inject
    OrderRepositories repositories;

    @Inject
    CustomerRepositories customerRepositories;

    @Inject
    FoodRepositories foodRepositories;

    @Inject
    ModelMapperConf modelMapperConf;

    public List<OrderDataRes> getAllOrder() {
        List<Order> orders = repositories.listAll();
        return orders.stream()
                .map(modelMapperConf::toOrderDataRes)
                .collect(Collectors.toList());
    }

    public Order getOrderByiD(long id){
        var orderId = repositories.findById(id);
        if(orderId.getId() == null){
            log.error("Id Not Found");
            throw new RuntimeException("Id Not Found");
        }
        return orderId;
    }

    @Transactional
    public OrderDataRes createOrder(OrderDataReq orderDataReq) {
        try {
            // Retrieve the customer entity from the database
            Customer customer = customerRepositories.findById(orderDataReq.getCustomerId());

            if (customer == null) {
                log.error("Customer not found with ID: {}", orderDataReq.getCustomerId());
                throw new RuntimeException("Customer not found with ID: " + orderDataReq.getCustomerId());
            }

            // Map OrderDataReq to Order entity
            Order newOrder = new Order();  // Let's create a new order without modelMapper for now

            // Set the customer for the order
            newOrder.setCustomer(customer);

            // Create OrderDetail entities from OrderDetailDataRes list
            List<OrderDetail> orderDetails = createOrderDetails(newOrder, orderDataReq.getOrderDetail());
            newOrder.setOrderDetails(orderDetails);

            // Set the orderDate to the current date
            newOrder.setOrderDate(LocalDate.now());

            // Set the paymentMethod for the order
            newOrder.setPaymentMethod(orderDataReq.getPaymentMethod());

            // Calculate total qty and set it to the order
            int totalQty = calculateTotalQty(orderDetails);
            newOrder.setQty(totalQty);

            // Calculate total price and set it to the order
            BigDecimal totalPrice = calculateTotalPrice(orderDetails);
            newOrder.setTotalPrice(totalPrice);

            // Calculate total paid and total change and set them to the order
            BigDecimal totalPaid = orderDataReq.getTotalPaid();
            newOrder.setTotalPaid(totalPaid);

            BigDecimal totalChange = totalPaid.subtract(totalPrice);
            newOrder.setTotalChange(totalChange);

            // Persist the new order entity
            repositories.persist(newOrder);
            log.info("Order persisted with ID: {}", newOrder.getId());

            List<OrderDetailDataRes> orderDetailDataResList = newOrder.getOrderDetails().stream()
                    .map(orderDetail -> {
                        OrderDetailDataRes orderDetailDataRes = new OrderDetailDataRes();
                        orderDetailDataRes.setFoodId(orderDetail.getFood().getId());
                        orderDetailDataRes.setQty(orderDetail.getQty());
                        return orderDetailDataRes;
                    })
                    .collect(Collectors.toList());

            // Create OrderDataRes for response
            OrderDataRes orderDataRes = OrderDataRes.builder()
                    .id(newOrder.getId())
                    .customerId(newOrder.getCustomer().getId())
                    .qty(newOrder.getQty())
                    .orders(orderDetailDataResList)
                    .paymentMethod(newOrder.getPaymentMethod())
                    .totalPrice(newOrder.getTotalPrice())
                    .totalChange(newOrder.getTotalChange())
                    .totalPaid(newOrder.getTotalPaid())
                    .orderDate(newOrder.getOrderDate())
                    .build();
            // Map other properties as needed

            return orderDataRes;
        } catch (Exception e) {
            log.error("Error while creating order", e);
            throw new RuntimeException("Error while creating order", e);
        }
    }

    private List<OrderDetail> createOrderDetails(Order order, List<OrderDetailDataRes> orderDetailDataResList) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailDataRes orderDetailDataRes : orderDetailDataResList) {
            // Retrieve the food entity from the database
            Food food = foodRepositories.findById(orderDetailDataRes.getFoodId());

            if (food == null) {
                log.error("Food not found with ID: {}", orderDetailDataRes.getFoodId());
                throw new RuntimeException("Food not found with ID: " + orderDetailDataRes.getFoodId());
            }

            // Create a new OrderDetail entity
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setFood(food);
            orderDetail.setQty(orderDetailDataRes.getQty());

            // Set the order for the orderDetail
            orderDetail.setOrder(order);

            // Add the created OrderDetail entity to the list
            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }


    private int calculateTotalQty(List<OrderDetail> orderDetails) {
        return orderDetails.stream().mapToInt(OrderDetail::getQty).sum();
    }


    private BigDecimal calculateTotalPrice(List<OrderDetail> orderDetails) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderDetail orderDetail : orderDetails) {
            BigDecimal foodPrice = orderDetail.getFood().getPrice();
            BigDecimal quantity = new BigDecimal(orderDetail.getQty());

            totalPrice = totalPrice.add(foodPrice.multiply(quantity));
        }

        return totalPrice;
    }
}

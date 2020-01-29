package com.example.customer.controller;

import com.example.customer.exception.AppException;
import com.example.customer.service.CustomerService;
import com.example.customer.view.CustomerView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Before
    public void setup() {
        customerController = new CustomerController(customerService);
    }

    @Test(expected = AppException.class)
    public void shouldThrownAppException() throws Exception {
        Mockito.when(customerService.create(Mockito.any(CustomerView.class)))
                .thenThrow(new AppException(500, "Internal Error"));
        customerController.createCustomer(new CustomerView());
    }

    @Test
    public void shouldReturnCustomer() throws Exception {
        Mockito.when(customerService.create(Mockito.any(CustomerView.class)))
                .thenReturn(new CustomerView("customer-id","name","email"));

        HttpEntity<CustomerView> customerViewHttpEntity = customerController.createCustomer(new CustomerView());
        Assert.assertNotNull(customerViewHttpEntity);
        Assert.assertNotNull(customerViewHttpEntity.getBody());
        Assert.assertNotNull(customerViewHttpEntity.getBody().getId());
        Assert.assertNotNull(customerViewHttpEntity.getBody().getEmail());
        Assert.assertNotNull(customerViewHttpEntity.getBody().getName());
    }

}

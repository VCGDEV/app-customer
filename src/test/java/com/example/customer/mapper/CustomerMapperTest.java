package com.example.customer.mapper;

import com.example.customer.model.Customer;
import com.example.customer.view.CustomerView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerMapperTest {

    private CustomerMapper mapper;

    @Before
    public void setup() {
        mapper = new CustomerMapper();
    }

    @Test
    public void shouldCreateNewView() {
        Customer customer = new Customer();
        CustomerView view = mapper.toView(customer);
        Assert.assertNotNull(view);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithInvalidEntity() {
        mapper.toView(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithInvalidView() {
        mapper.toEntity(null);
    }

    @Test
    public void shouldCreateNewEntity() {
        CustomerView view = new CustomerView();
        Customer customer = mapper.toEntity(view);
        Assert.assertNotNull(customer);
    }

}

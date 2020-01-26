package com.example.customer.service;

import com.example.customer.exception.AppException;
import com.example.customer.exception.NoUniqueCustomerException;
import com.example.customer.exception.ValidationException;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.model.Customer;
import com.example.customer.persistence.CustomerRepository;
import com.example.customer.view.CustomerView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {
    private Customer customer;
    private CustomerView view;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;

    private CustomerService customerService;

    @Before
    public void setup() {
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);

        customer = new Customer("some-id", "My customer name", "myemail@example.com");
        view = new CustomerView("some-id", "My customer name", "myemail@example.com");

        Mockito.when(customerMapper.toEntity(Mockito.any(CustomerView.class)))
                .thenReturn(customer);

        Mockito.when(customerMapper.toView(Mockito.any(Customer.class)))
            .thenReturn(view);
    }

    @Test
    public void shouldBeAValidCustomerView() {
        //view with not empty values
        Assert.assertTrue(customerService.isValid(view));
    }

    @Test
    public void shouldBeAnInvalidCustomerViewWhenNullValues() {
        Assert.assertFalse(customerService.isValid(new CustomerView()));
    }

    @Test
    public void shouldBeAnInvalidCustomerViewWhenEmptyValues() {
        Assert.assertFalse(customerService.isValid(new CustomerView("", "", "")));
    }

    @Test
    public void shouldBeAnInvalidCustomerViewWhenNameIsEmpty() {
        Assert.assertFalse(customerService.isValid(new CustomerView("", "", "example@example.com")));
    }

    @Test
    public void shouldBeAnInvalidCustomerViewWhenEmailIsEmpty() {
        Assert.assertFalse(customerService.isValid(new CustomerView("", "My customer name", "")));
    }

    @Test
    public void shouldBeAnInvalidCustomerViewWhenNull() {
        Assert.assertFalse(customerService.isValid(null));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowAValidationExceptionWhenViewIsInvalid() throws Exception {
        CustomerView view = new CustomerView("", "", "");
        customerService.create(view);
    }
    @Test(expected = NoUniqueCustomerException.class)
    public void shouldThrowNoUniqueException() throws Exception {
        Mockito.when(customerRepository.existsByEmail(view.getEmail())).thenReturn(true);
        customerService.create(view);
        Mockito.verify(customerRepository).existsByEmail(view.getEmail());
    }
    @Test(expected = AppException.class)
    public void shouldThrowAnAppException() throws Exception {
        //mail does not exist in db
        Mockito.when(customerRepository.existsByEmail(view.getEmail())).thenReturn(false);
        //error on saving
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenThrow(new DataAccessException("Could not save customer") {});

        customerService.create(view);
        Mockito.verify(customerRepository).existsByEmail(view.getEmail());
        Mockito.verify(customerRepository).save(Mockito.any(Customer.class));
    }
    @Test()
    public void shouldSaveCustomer() throws Exception {
        Mockito.when(customerRepository.existsByEmail(view.getEmail())).thenReturn(false);
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        CustomerView viewResult = customerService.create(view);
        Mockito.verify(customerMapper).toView(Mockito.any(Customer.class));
        Mockito.verify(customerMapper).toEntity(Mockito.any(CustomerView.class));
        Mockito.verify(customerRepository).existsByEmail(view.getEmail());
        Mockito.verify(customerRepository).save(Mockito.any(Customer.class));
        Assert.assertEquals(view.getEmail(), viewResult.getEmail());
        Assert.assertEquals(view.getId(), viewResult.getId());
        Assert.assertEquals(view.getName(), viewResult.getName());
    }
}

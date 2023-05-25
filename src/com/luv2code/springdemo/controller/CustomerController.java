package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject the customer dao
	// Remove following and replacing with service
	/*
 	@Autowired
	private CustomerDAO customerDAO;
	*/
	@Autowired
	private CustomerService customerService;
	
	
	//@RequestMapping("/list")
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get the customers from the service
		List<Customer> customers = customerService.getCustomers();
		
		// add customers to the model
		theModel.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// create model attribute to bind form data
		Customer customer = new Customer();
		theModel.addAttribute("customer", customer);
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		//save the customer using our service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	

}

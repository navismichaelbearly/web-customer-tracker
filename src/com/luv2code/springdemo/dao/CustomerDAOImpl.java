package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	//Removed @Transactional and added in @Service layer
	@Override
	public List<Customer> getCustomers(int theSortField) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// determine the sort field
		String theFieldName = null;
		
		switch(theSortField) {
			case SortUtils.FIRST_NAME:
				theFieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				theFieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				theFieldName = "email";
				break;
			default:
				theFieldName = "lastName";
		}
		
		// create a query
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by " + theFieldName, 
				Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// return the results.
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save the customer ..
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//retrieve/read from db using the primary key
		Customer customer = currentSession.get(Customer.class, theId);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//delete the customer
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = null;
		
		if(theSearchName !=null && theSearchName.trim().length() > 0) {
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		}
		else {
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}
		
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
	}

}

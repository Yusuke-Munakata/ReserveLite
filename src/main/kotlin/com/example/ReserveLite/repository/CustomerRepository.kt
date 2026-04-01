package com.example.ReserveLite.repository

import com.example.ReserveLite.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long>
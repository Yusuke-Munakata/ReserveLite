package com.example.ReserveLite.service

import com.example.ReserveLite.entity.Customer
import com.example.ReserveLite.form.CustomerCreateForm
import com.example.ReserveLite.repository.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class CustomerServiceTest {
  private val customerRepository = mockk<CustomerRepository>()
  private val customerService = CustomerService(customerRepository)

  @Test
  fun `createCustomer フォームの内容から顧客を登録できること`() {
    val form = CustomerCreateForm(
      name = "田中太郎",
      email = "taro@example.com"
    )

    val savedCustomerSlot = slot<Customer>()

    every { customerRepository.save(capture(savedCustomerSlot)) } answers { savedCustomerSlot.captured }

    val result = customerService.createCustomer(form)

    assertEquals("田中太郎", savedCustomerSlot.captured.name)
    assertEquals("taro@example.com", savedCustomerSlot.captured.email)
  
    assertEquals("田中太郎", result.name)
    assertEquals("taro@example.com", result.email)

    verify(exactly = 1) { customerRepository.save(any()) }
  }

  @Test
  fun `getCustomerById 存在するIDなら顧客情報を返すこと`() {
    val customer = Customer(
      id = 1L,
      name = "田中太郎",
      email = "taro.tanaka@example.com"
    )
    every { customerRepository.findByIdOrNull(1L) } returns customer

    val result = customerService.getCustomerById(1L)

    assertEquals(1L, result.id)
    assertEquals("田中太郎", result.name)
    assertEquals("taro.tanaka@example.com", result.email)
    verify(exactly = 1) { customerRepository.findByIdOrNull(1L) }
  }

  @Test
  fun `getCustomerById 存在しないIDなら例外を投げること`() {
    every { customerRepository.findByIdOrNull(999L) } returns null

    try {
      customerService.getCustomerById(999L)
    } catch (e: IllegalArgumentException) {
      assertEquals("指定したIDの顧客が存在しません: 999", e.message)
    }

    verify(exactly = 1) { customerRepository.findByIdOrNull(999L) }
  }
}
package com.example.ReserveLite.controller.page

import com.example.ReserveLite.service.CustomerService
import com.example.ReserveLite.entity.Customer
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@WebMvcTest(CustomerPageController::class)
class CustomerPageControllerTest {
  @Autowired
  private lateinit var mockMvc: MockMvc

  @MockitoBean
  private lateinit var customerService: CustomerService

  @Test
  fun `GET customers new 顧客作成フォームが表示されること`() {
    mockMvc.get("/customers/new")
      .andExpect {
        status { isOk() }
        view { name("customers/create") }
        model { attributeExists("customerCreateForm") }
      }
  }

  @Test
  fun `POST customers 正常系なら顧客を登録してリダイレクトする`() {
    whenever(customerService.createCustomer(any())).thenReturn(Customer(id = 1L, name = "田中太郎", email = "taro@example.com"))

    mockMvc.post("/customers") {
      param("name", "田中太郎")
      param("email", "taro@example.com")
    }.andExpect {
      status { is3xxRedirection() }
      redirectedUrl("/customers/1")
    }
  }

  @Test
  fun `POST customers 異常系ならエラーを表示する`() {
    mockMvc.post("/customers") {
      param("name", "")
      param("email", "")
    }.andExpect {
      status { isOk() }
      view { name("customers/create") }
      model { hasErrors() }
    }
  }

  @Test
  fun `GET customers id 顧客詳細が表示されること`() {
    val customer = Customer(name = "田中太郎", email = "taro.tanaka@example.com")
    whenever(customerService.getCustomerById(1L)).thenReturn(customer)

    mockMvc.get("/customers/1")
      .andExpect {
        status { isOk() }
        view { name("customers/detail") }
        model { attribute("customer", customer) }
      }
  }
}
package com.example.ReserveLite.controller.page

import com.example.ReserveLite.form.CustomerCreateForm
import com.example.ReserveLite.service.CustomerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import jakarta.validation.Valid

@Controller
@RequestMapping("/customers")
class CustomerPageController(
  private val customerService: CustomerService
) {
    @GetMapping("/new")
    fun showCreateForm(model: Model): String {
        model.addAttribute("customerCreateForm", CustomerCreateForm("", ""))
        return "customers/create"
    }

    @PostMapping
    fun createCustomer(
      @Valid @ModelAttribute customerCreateForm: CustomerCreateForm,
      bindingResult: BindingResult,
    ): String {
        if (bindingResult.hasErrors()) return "customers/create"

        customerService.createCustomer(customerCreateForm)
        return "redirect:/customers/new"
    }
}
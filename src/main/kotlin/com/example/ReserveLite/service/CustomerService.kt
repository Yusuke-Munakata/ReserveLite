package com.example.ReserveLite.service

import com.example.ReserveLite.entity.Customer
import com.example.ReserveLite.form.CustomerCreateForm
import com.example.ReserveLite.repository.CustomerRepository
import org.springframework.stereotype.Service

/**
 * 顧客情報を扱うサービス
 *
 * @property customerRepository 顧客情報の保存に使用するリポジトリ
 */
@Service
class CustomerService(
  private val customerRepository: CustomerRepository
) {
  /**
   * 顧客作成フォームの内容から新しい顧客を登録する
   *
   * @param form 登録する顧客情報を保持したフォーム
   * @return 保存済みの顧客エンティティ
   */
  fun createCustomer(form: CustomerCreateForm): Customer {
    val customer = Customer(
        name = form.name,
        email = form.email
    )
    return customerRepository.save(customer)
  }
}
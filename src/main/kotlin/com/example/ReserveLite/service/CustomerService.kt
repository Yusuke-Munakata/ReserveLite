package com.example.ReserveLite.service

import com.example.ReserveLite.entity.Customer
import com.example.ReserveLite.form.CustomerCreateForm
import com.example.ReserveLite.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.repository.findByIdOrNull

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
  @Transactional
  fun createCustomer(form: CustomerCreateForm): Customer {
    val customer = Customer(
        name = form.name,
        email = form.email
    )
    return customerRepository.save(customer)
  }

  /**
   * 顧客IDに基づいて顧客情報を取得する
   *
   * @param id 取得する顧客のID
   * @return 顧客エンティティ
   * @throws IllegalArgumentException 指定されたIDの顧客が存在しない場合
   */
  fun getCustomerById(id: Long): Customer {
    return customerRepository.findByIdOrNull(id)
        ?: throw IllegalArgumentException("指定したIDの顧客が存在しません: $id")
  }
}
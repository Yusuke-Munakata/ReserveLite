package com.example.ReserveLite.form

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CustomerCreateForm(
    @field:NotBlank(message = "名前は必須項目です。")
    @field:Size(max = 100, message = "名前は100文字以内で入力してください。")
    var name: String = "",

    @field:NotBlank(message = "メールアドレスは必須項目です。")
    @field:Email(message = "メールアドレスの形式が正しくありません。")
    @field:Size(max = 255, message = "メールアドレスは255文字以内で入力してください。")
    var email: String = ""
)
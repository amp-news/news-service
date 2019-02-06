package com.amp.news.user.service

import com.amp.news.user.domain.User

class UserServiceMock: UserService {

  override fun loadById(id: Long): User {
    return User(-1, "fn_test", "ln_test")
  }

}
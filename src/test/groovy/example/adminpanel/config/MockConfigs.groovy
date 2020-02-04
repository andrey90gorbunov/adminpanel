/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.config

import example.adminpanel.admin.repository.AdminUserRepository
import example.adminpanel.admin.service.AdminUserService
import example.adminpanel.core.security.service.CustomUserDetailsService
import example.adminpanel.core.web.assemblers.UserViewAssembler
import example.adminpanel.core.web.assemblers.impl.UserViewAssemblerImpl
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.security.core.userdetails.UserDetailsService

@TestConfiguration
class MockConfigs {

  @Bean
  AdminUserService mockAdminUserService() {
    Mockito.mock(AdminUserService)
  }

  @Bean
  AdminUserRepository mockAdminRepository() {
    Mockito.mock(AdminUserRepository)
  }

  @Bean
  MongoTemplate mockMongoTemplate() {
    Mockito.mock(MongoTemplate)
  }

  @Bean
  MongoMappingContext mockMongoMappingContext() {
    Mockito.mock(MongoMappingContext)
  }

  @Bean
  UserViewAssembler userViewAssembler() {
    return new UserViewAssemblerImpl()
  }

  @Bean
  UserDetailsService userDetailsService() {
    new CustomUserDetailsService()
  }
}

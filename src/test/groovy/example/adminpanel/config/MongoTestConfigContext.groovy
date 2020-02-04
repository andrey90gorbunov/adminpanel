/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.config


import example.adminpanel.admin.config.AdminConfig
import example.adminpanel.admin.service.AdminUserService
import example.adminpanel.admin.service.impl.AdminUserServiceImpl
import example.adminpanel.core.config.CoreConfig
import example.adminpanel.core.config.SecurityConfig
import example.adminpanel.core.security.service.CustomUserDetailsService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.core.userdetails.UserDetailsService

@TestConfiguration
@Import([SecurityConfig.class, CoreConfig.class, AdminConfig.class])
class MongoTestConfigContext {
  @Bean
  UserDetailsService userDetailsService() {
    new CustomUserDetailsService()
  }

  @Bean
  AdminUserService adminUserService() {
    new AdminUserServiceImpl()
  }
}

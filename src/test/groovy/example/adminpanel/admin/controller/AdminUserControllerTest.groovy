/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.admin.controller

import example.adminpanel.admin.repository.AdminUserRepository
import example.adminpanel.admin.service.AdminUserService
import example.adminpanel.config.MockConfigs
import example.adminpanel.config.MockMvcBase
import example.adminpanel.core.dto.Role
import example.adminpanel.core.dto.User
import example.adminpanel.core.dto.UserAddress
import example.adminpanel.core.security.CustomUserDetails
import example.adminpanel.core.web.dto.UserCreate
import example.adminpanel.core.web.dto.UserFilter
import example.adminpanel.core.web.dto.UserUpdate
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.lang.reflect.Method
import java.time.LocalDate

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@CompileStatic
@ContextConfiguration(classes = [MockConfigs.class])
@WebMvcTest(controllers = AdminUserController.class)
class AdminUserControllerTest extends MockMvcBase {

  @Autowired
  private AdminUserService mockAdminUserService

  @Autowired
  private AdminUserRepository mockAdminUserRepository

  @BeforeClass
  void before() {
    when(mockAdminUserRepository.findByLogin(anyString())).thenReturn(new User(login: "admin", role: Role.ROLE_ADMIN))
  }

  @BeforeMethod
  void beforeMethod(Method method) {
    restDocumentation.beforeTest(getClass(), method.getName())
  }

  @AfterMethod
  void afterMethod(Method method) {
    restDocumentation.afterTest()
  }

  @Test
  void createUser() {
    when(mockAdminUserService.createUser(any(UserCreate))).thenReturn(getUser())
    mockMvc.
      perform(
        post("/admin/user").with(user(new CustomUserDetails(getAdmin()))).contentType(MediaType.APPLICATION_JSON).accept(
          MediaType.
            APPLICATION_JSON
        ).content(objectMapper.writeValueAsString(getUserCreate()))
      ).andExpect(status().isOk());

  }

  @Test
  void getUserById() {
    when(mockAdminUserService.getUserById(anyString())).thenReturn(getUser())
    mockMvc.
      perform(
        get("/admin/user/{userId}", getUser().id).with(user(new CustomUserDetails(getAdmin()))).contentType(MediaType.APPLICATION_JSON)
      ).andExpect(status().isOk());

  }

  @Test
  void getUsers() {
    when(mockAdminUserService.getUsers(any(UserFilter))).thenReturn(new PageImpl<User>([getUser()]))
    mockMvc.
      perform(
        get("/admin/user").with(user(new CustomUserDetails(getAdmin()))).contentType(MediaType.APPLICATION_JSON)
      ).andExpect(status().isOk());

  }

  @Test
  void updateUser() {
    def updatedUser = getUser()
    updatedUser.lastName = "Петров"
    when(mockAdminUserService.updateUser(anyString(), any(UserUpdate))).thenReturn(updatedUser)
    mockMvc.
      perform(
        put("/admin/user/{userId}", getUser().id).with(user(new CustomUserDetails(getAdmin()))).contentType(MediaType.APPLICATION_JSON).accept(
          MediaType.
            APPLICATION_JSON
        ).content(objectMapper.writeValueAsString(new UserUpdate(lastName: "Петров")))
      ).andExpect(status().isOk());

  }

  @Test
  void deleteUser() {
    mockMvc.
      perform(
        delete("/admin/user/{userId}", getUser().id).with(user(new CustomUserDetails(getAdmin()))).contentType(MediaType.APPLICATION_JSON).accept(
          MediaType.
            APPLICATION_JSON
        )
      ).andExpect(status().isOk());

  }


  private static UserCreate getUserCreate() {
    new UserCreate(
      firstName: 'Иван',
      lastName: 'Иванов',
      login: 'ivan123',
      password: 'ivan123',
      birthday: LocalDate.of(1990, 5, 5),
      address: new UserAddress(city: 'Москва', street: 'Ленина', house: 15, flat: 3),
      aboutUser: 'Новый юзер'
    )
  }

  private static User getUser() {
    new User(
      id: UUID.randomUUID().toString(),
      login: 'ivan123',
      firstName: 'Иван',
      lastName: 'Иванов',
      birthday: LocalDate.of(1990, 5, 5),
      address: new UserAddress(city: 'Москва', street: 'Ленина', house: 15, flat: 3),
      aboutUser: 'Новый юзер'
    )
  }

  private static User getAdmin() {
    new User(
      id: UUID.randomUUID().toString(),
      login: 'admin',
      role: Role.ROLE_ADMIN
    )
  }
}

package example.adminpanel.admin.service

import example.adminpanel.config.MongoTestConfigContext
import example.adminpanel.core.dto.Role
import example.adminpanel.core.dto.UserAddress
import example.adminpanel.core.exception.UserValidationException
import example.adminpanel.core.web.dto.AddressFilter
import example.adminpanel.core.web.dto.UserCreate
import example.adminpanel.core.web.dto.UserFilter
import example.adminpanel.core.web.dto.UserUpdate
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import java.time.LocalDate

import static example.adminpanel.core.util.LocalDateUtils.toRange

@Import([MongoTestConfigContext.class])
@DataMongoTest
@CompileStatic
class AdminUserServiceTest extends AbstractTestNGSpringContextTests {
  private final static String COLLECTION_NAME = "user"

  @Autowired
  private AdminUserService adminUserService

  @Autowired
  private MongoTemplate mongoTemplate

  @DataProvider(name = 'userFilters')
  Object[][] userFilters() {
    return [
      [new UserFilter(), 1],
      [new UserFilter(firstName: "Ив"), 1],
      [new UserFilter(firstName: "Же"), 0],
      [new UserFilter(lastName: "Ив"), 1],
      [new UserFilter(lastName: "Же"), 0],
      [new UserFilter(login: "iv"), 1],
      [new UserFilter(login: "ll"), 0],
      [new UserFilter(birthday: toRange(Optional.of(LocalDate.of(1990, 5, 5)), Optional.empty())), 1],
      [new UserFilter(birthday: toRange(Optional.of(LocalDate.of(1990, 5, 6)), Optional.empty())), 0],
      [new UserFilter(birthday: toRange(Optional.empty(), Optional.of(LocalDate.of(1990, 5, 5)))), 1],
      [new UserFilter(birthday: toRange(Optional.empty(), Optional.of(LocalDate.of(1990, 5, 4)))), 0],
      [new UserFilter(birthday: toRange(Optional.of(LocalDate.of(1980, 1, 1)), Optional.of(LocalDate.of(2000, 1, 1)))), 1],
      [new UserFilter(role: Role.ROLE_USER), 1],
      [new UserFilter(role: Role.ROLE_ADMIN), 0],
      [new UserFilter(addressFilter: new AddressFilter(country: "Russia", city: "Москва")), 1],
      [new UserFilter(addressFilter: new AddressFilter(country: "США", city: "Москва")), 0],
      [new UserFilter(login: "iv", firstName: "Ив", lastName: "Иванов"), 1],
      [new UserFilter(login: "iv", firstName: "Ив", lastName: "Петров"), 0]
    ] as Object[][]
  }


  @BeforeMethod
  void clean() {
    mongoTemplate.dropCollection(COLLECTION_NAME)
  }

  @Test
  void testCreateUser() {
    def userCreate = getUserCreate()
    def user = adminUserService.createUser(userCreate)
    adminUserService.getUserById(user.id).with {
      assert id != null
      assert firstName == userCreate.firstName
      assert lastName == userCreate.lastName
      assert login == userCreate.login
      assert birthday == userCreate.birthday
      assert aboutUser == userCreate.aboutUser
    }
  }

  @Test(expectedExceptions = UserValidationException)
  void testCreateUserWithExistingLogin() {
    def userCreate = getUserCreate()
    adminUserService.createUser(userCreate)
    adminUserService.createUser(userCreate)
  }

  @Test(expectedExceptions = UserValidationException)
  void testCreateUserWithValidationError() {
    def userCreate = getUserCreate()
    userCreate.birthday = LocalDate.now().plusDays(20)
    adminUserService.createUser(userCreate)
  }

  @Test
  void testUpdateUser() {
    def userCreate = getUserCreate()
    def user = adminUserService.createUser(userCreate)
    adminUserService.updateUser(user.id, new UserUpdate(lastName: 'Петров', password: 'petrov123')).with {
      assert login == userCreate.login
      assert firstName == userCreate.firstName
      assert lastName == 'Петров'
    }
  }

  @Test
  void testDeleteUser() {
    def user = adminUserService.createUser(getUserCreate())
    assert adminUserService.getUsers(new UserFilter()).content.size() == 1
    adminUserService.deleteUser(user.id)
    assert adminUserService.getUsers(new UserFilter()).content.size() == 0

  }

  @Test(dataProvider = "userFilters")
  void testFilterUser(UserFilter userFilter, Integer size) {
    adminUserService.createUser(getUserCreate())
    assert adminUserService.getUsers(userFilter).content.size() == size

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

}




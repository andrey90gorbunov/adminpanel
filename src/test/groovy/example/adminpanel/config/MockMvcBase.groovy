/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.config

import capital.scalable.restdocs.AutoDocumentation
import capital.scalable.restdocs.jackson.JacksonResultHandlers
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.cli.CliDocumentation
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testng.annotations.BeforeClass

@CompileStatic
abstract class MockMvcBase extends AbstractTestNGSpringContextTests {

  @Autowired
  private WebApplicationContext context

  @Autowired
  protected ObjectMapper objectMapper

  protected MockMvc mockMvc

  protected final ManualRestDocumentation restDocumentation = new ManualRestDocumentation()

  @BeforeClass
  void setUp()
    throws Exception {
    this.mockMvc = MockMvcBuilders
      .webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
      .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
      .alwaysDo(
        MockMvcRestDocumentation.document(
          "{class-name}/{method-name}",
          Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
          Preprocessors.preprocessResponse(
            ResponseModifyingPreprocessors.replaceBinaryContent(),
            ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
            Preprocessors.prettyPrint()
          )
        )
      )
      .apply(
        MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation)
                                .uris()
                                .withScheme("http")
                                .withHost("localhost")
                                .withPort(8080)
                                .and().snippets()
                                .withDefaults(
                                  CliDocumentation.curlRequest(),
                                  HttpDocumentation.httpRequest(),
                                  HttpDocumentation.httpResponse(),
                                  AutoDocumentation.requestFields(),
                                  AutoDocumentation.responseFields(),
                                  AutoDocumentation.pathParameters(),
                                  AutoDocumentation.requestParameters(),
                                  AutoDocumentation.description(),
                                  AutoDocumentation.methodAndPath(),
                                  AutoDocumentation.section()
                                )
      )
      .build();
  }


}

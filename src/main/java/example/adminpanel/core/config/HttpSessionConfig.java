/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel.core.config;

import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@EnableMongoHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 3)
public class HttpSessionConfig {
}

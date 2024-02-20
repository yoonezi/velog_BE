package com.study.velog;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@Sql("classpath:member_init.sql")
@SpringBootTest
@WithUserDetails(value = "yz@naver.com", setupBefore = TestExecutionEvent.TEST_EXECUTION,
        userDetailsServiceBeanName = "customUserDetailsService")
public abstract class IntegrationTestSupport {
}

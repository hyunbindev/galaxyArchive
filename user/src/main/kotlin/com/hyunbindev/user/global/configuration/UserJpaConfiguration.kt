package com.hyunbindev.user.global.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.hyunbindev.user.domain"])
@EnableJpaRepositories(basePackages = ["com.hyunbindev.user.adapter.outbound"])
class UserJpaConfiguration {
}
package com.laptrinhmangnangcao.ltm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // Thay thế đoạn mã sau bằng mã để xác định người dùng hiện tại
        // Điều này có thể được thực hiện thông qua Spring Security hoặc bất kỳ cách nào bạn muốn.
        return () -> java.util.Optional.of("system"); // Ví dụ: Trả về "system" cho người dùng mặc định.
    }
}


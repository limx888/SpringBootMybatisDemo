package com.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="task")
@EqualsAndHashCode(callSuper = false)
public class Task {
    private String corn1;
    private String corn2;
}

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

    public String getCorn1() {
        return corn1;
    }

    public void setCorn1(String corn1) {
        this.corn1 = corn1;
    }

    public String getCorn2() {
        return corn2;
    }

    public void setCorn2(String corn2) {
        this.corn2 = corn2;
    }
}

package com.demo.validator;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 参数校验
 */
@Data
public class Book {
    @NotNull(message = "id 不能为空", groups = Groups.Update.class)
    private Integer id;
    @NotBlank(message = "name不能为空", groups = Groups.Default.class)
    @Length(min = 2, max = 10, message = "name长度必须在 {min} - {max} 之间")
    private String name;
    @DecimalMin(value = "0.1", message = "价格不能低于 {value}", groups = Groups.Default.class)
    private BigDecimal price;
}

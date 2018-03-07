package com.sample.easypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private String id;
    @Excel(name = "学生姓名")
    @NotEmpty(message = "学生姓名不能为空")
    private String name;
    @Excel(name = "学生性别", replace = {"男_1", "女_2"}, suffix = "生")
    @NotNull(message = "学生性别不能为空")
    private Integer sex;
    @Excel(name = "出生日期", format = "yyyy-MM-dd")
    private Date birthday;

    private String errorMsg;

}

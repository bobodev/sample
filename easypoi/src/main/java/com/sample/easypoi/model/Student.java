package com.sample.easypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private String id;
    @Excel(name = "学生姓名")
    private String name;
    @Excel(name = "学生性别", replace = {"男_1", "女_2"}, suffix = "生")
    private int sex;
    @Excel(name = "出生日期", format = "yyyy-MM-dd")
    private Date birthday;
}

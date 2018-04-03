package com.sample.scaffold.contract.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class SingleDto implements java.io.Serializable {

    /**
     * 字符串
     */
    @NotEmpty(message = "propStr不允许为空")
    @Size(min = 0, max = 50, message = "propStr允许输入长度范围为【0-50】")
    private String propStr;

    /**
     * 整数
     */
    @NotNull(message = "propInteger不允许为空")
    @Digits(integer = 8, fraction = 0, message = "propInteger数字的值超出了允许范围(只允许在8位整数)")
    private Integer propInteger;

    /**
     * 长整型
     */
    @NotNull(message = "propLong不允许为空")
    @Digits(integer = 8, fraction = 0, message = "propLong数字的值超出了允许范围(只允许在8位整数)")
    private Long propLong;

    /**
     * 小数
     */
    @NotNull(message = "propDouble不允许为空")
    @Digits(integer = 8, fraction = 2, message = "propDouble数字的值超出了允许范围(只允许在8位整数和2位小数范围内)")
    private Double propDouble;

    /**
     * 日期
     */
    @NotNull(message = "propDate不允许为空")
    @JSONField(format = "yyyy-MM-dd")
    private Date propDate;

    /**
     * 备注
     */
    @NotEmpty(message = "description不允许为空")
    @Size(min = 0, max = 200, message = "propStr允许输入长度范围为【0-200】")
    private String description;

    public String getPropStr() {
        return propStr;
    }

    public void setPropStr(String propStr) {
        this.propStr = propStr;
    }

    public Integer getPropInteger() {
        return propInteger;
    }

    public void setPropInteger(Integer propInteger) {
        this.propInteger = propInteger;
    }

    public Long getPropLong() {
        return propLong;
    }

    public void setPropLong(Long propLong) {
        this.propLong = propLong;
    }

    public Double getPropDouble() {
        return propDouble;
    }

    public void setPropDouble(Double propDouble) {
        this.propDouble = propDouble;
    }

    public Date getPropDate() {
        return propDate;
    }

    public void setPropDate(Date propDate) {
        this.propDate = propDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

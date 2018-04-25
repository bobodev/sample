package com.sample.scaffold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by sunguangzhu on 15/8/22.
 */
@Entity
@Table(name = "t_single")
public class Single extends BaseModel {

    /**
     * 字符串
     */
    private String propStr;

    /**
     * 整数
     */
    private Integer propInteger;

    /**
     * 长整型
     */
    private Long propLong;

    /**
     * 小数
     */
    private Double propDouble;

    /**
     * 日期
     */
    private Date propDate;

    /**
     * 备注
     */
    private String description;

    /**
     * 构造函数
     */
    public Single() {
    }

    /**
     * 字符串的get方法
     */
    @Column(name="`propStr`",length = 50)
    public String getPropStr() {
        return propStr;
    }

    public void setPropStr(String propStr) {
        this.propStr = propStr;
    }

    /**
     * 整数的get方法
     */
    @Column(name="`propLong`")
    public Long getPropLong() {
        return propLong;
    }

    public void setPropLong(Long propLong) {
        this.propLong = propLong;
    }

    /**
     * 长整型的get方法
     */
    @Column(name="`propInteger`")
    public Integer getPropInteger() {
        return propInteger;
    }

    public void setPropInteger(Integer propInteger) {
        this.propInteger = propInteger;
    }

    /**
     * 小数的get方法
     */
    @Column(name="`propDouble`",nullable = false)
    public Double getPropDouble() {
        return propDouble;
    }

    public void setPropDouble(Double propDouble) {
        this.propDouble = propDouble;
    }

    /**
     * 日期的get方法
     */
    @Column(name="`propDate`")
    public Date getPropDate() {
        return propDate;
    }

    public void setPropDate(Date propDate) {
        this.propDate = propDate;
    }

    /**
     * 描述的get方法
     */
    @Column(name="`description`",length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

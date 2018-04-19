package com.sample.java8.lambda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanchongyang
 * @date 2018/4/18 下午2:38
 */
public class Account implements Serializable {
    private static String[] USER_NAME_ARRAY = new String[]{"bob", "martin", "joe"};

    private Long id;
    private String userName;

    public Account() {
    }

    public Account(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static List<Account> build(int len) {
        List<Account> accountList = new ArrayList<>(len);
        for (int i = 1; i <= len; i++) {
            accountList.add(new Account(Long.valueOf(i), USER_NAME_ARRAY[i%3]));
        }

        return accountList;
    }

    public static void print(Account account) {
        System.out.println(account.toString());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}

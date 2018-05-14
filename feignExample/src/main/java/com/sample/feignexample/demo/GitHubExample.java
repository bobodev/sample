/**
 * Copyright 2012-2018 The Feign Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.sample.feignexample.demo;

import com.sample.feignexample.base.ApiResponse;
import com.sample.feignexample.bean.User;
import feign.*;
import feign.codec.Decoder;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * Inspired by {@code com.example.retrofit.GitHubClient}
 */
public class GitHubExample {

  interface GitHub {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /api/hello?name={name}")
    ApiResponse hello(@Param("name") String name);

    @RequestLine("GET /api/hello")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ApiResponse hello2(@Param("name") String name);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/hello3")
    ApiResponse hello3(User user);

    static GitHub connect() {
      Decoder decoder = new GsonDecoder();
      return Feign.builder()
              .encoder(new FormEncoder())
            .decoder(new GsonDecoder())
          .logger(new Slf4jLogger())
          .logLevel(Logger.Level.FULL)
          .target(GitHub.class, "http://localhost:8080");
    }

    static GitHub connect2() {
      Decoder decoder = new GsonDecoder();
      return Feign.builder()
              .encoder(new FormEncoder(new GsonEncoder()))
              .decoder(new GsonDecoder())
              .logger(new Slf4jLogger())
              .logLevel(Logger.Level.FULL)
              .target(GitHub.class, "http://localhost:8080");
    }
  }


  static class GitHubClientError extends RuntimeException {
    private String message; // parsed from json

    @Override
    public String getMessage() {
      return message;
    }
  }

  public static void main(String... args) {
    test();
   //testUser();
  }

  public static  void test(){
    GitHub github = GitHub.connect();

    ApiResponse xiaoxiao = github.hello2("小白");
    System.out.println(xiaoxiao);
  }

  public static  void testUser(){
    GitHub github = GitHub.connect2();

    ApiResponse xiaoxiao = github.hello3(new User(1,"小白"));
    System.out.println(xiaoxiao);
  }


}
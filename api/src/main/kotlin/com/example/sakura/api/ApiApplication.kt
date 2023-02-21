package com.example.sakura.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan


@SpringBootApplication(scanBasePackages = ["com.example.sakura"])
@ServletComponentScan(basePackages = ["com.example.sakura"])
class ApiApplication {

}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
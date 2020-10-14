package ru.ifmo.dbstuff

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DbstuffApplication

fun main(args: Array<String>) {
    runApplication<DbstuffApplication>(*args)
}

package ru.ifmo.dbstuff.mongo

import org.springframework.data.annotation.Id
import java.time.LocalDate


data class Person (
        @Id val id: String,
        val firstName: String,
        val lastName: String,
        val privileges: Boolean
)

data class Room (
        @Id
        val id: String,
        val capacity: Int,
        val insects: Boolean,
        val warning: Int,
        val disinfection: LocalDate
)

data class StudyInfo (
        @Id
        val id: String,
        val type: String,
        val paid: Int,
        val person: Person
)

data class Campus (
        @Id
        val id: String,
        val address: String,
        val numberOfRooms: Int,
        val rooms: List<Room>
)

data class Stay (
        @Id
        val id: String,
        val person: Person,
        val room: Room,
        val from: LocalDate,
        val to: LocalDate
)

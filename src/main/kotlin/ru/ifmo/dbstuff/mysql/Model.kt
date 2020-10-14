package ru.ifmo.dbstuff.mysql

import java.time.LocalDate
import javax.persistence.*


@Entity
data class Conference (
        @Id var id: String,
        val name: String,
        val address: String,
        val date: LocalDate,
        @ManyToMany(fetch = FetchType.EAGER)
        var participants: Set<Participant>
)

@Entity
data class Participant (
        @Id var id: String,
        val fullName: String,
        val position: String
)

@Entity
data class Publication (
        @Id var id: String,
        val name: String,
        @ManyToMany(fetch = FetchType.EAGER)
        var authors: List<Participant>,
        val date: LocalDate
)

@Entity
data class Edition (
        @Id var id: String,
        val name: String,
        val editionType: String,
        val language: String,
        @ManyToMany(fetch = FetchType.EAGER)
        var publications: List<Publication>
)

@Entity
data class LibraryRecord (
        @Id var id: String,
        @ManyToOne(fetch = FetchType.EAGER)
        var edition: Publication,
        @ManyToOne(fetch = FetchType.EAGER)
        var participant: Participant,
        val fromDate: LocalDate,
        val toDate: LocalDate
)
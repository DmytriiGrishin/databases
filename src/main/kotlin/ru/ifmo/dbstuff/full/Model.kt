package ru.ifmo.dbstuff.full

import java.time.LocalDate
import javax.persistence.*

@Entity
data class BirthPlace (
        @Id var id: String,
        val district: String,
        val city: String,
        val country: String
)

@Entity
data class Campus (
        @Id val id: String,
        val address: String,
        val numberOfRooms: Int,
        @OneToMany(mappedBy = "campus", fetch = FetchType.EAGER)
        val rooms: List<Room>
)

@Entity
data class FactTable1 (
        @Id var id: String,
        val diplomaWithHonors: Int,
        val usualDiploma: Int,
        val publications: Int,
        val studentLibraryCards: Int,
        val conferences: Int,
        val employeeLibraryCards: Int,
        val liveInCampus: Int,
        val notLiveInCampus: Int,
        @ManyToOne
        val time: Time
)

@Entity
data class FactTable2 (
        @Id val id: String,
        val people: Int,
        @ManyToOne
        val birthPlace: BirthPlace,
        @ManyToOne
        val time: Time
)

@Entity
data class FactTable3 (
        @Id val id: String,
        val people: Int,
        @ManyToOne
        val publisher: Publisher,
        @ManyToOne
        val time: Time
)

@Entity
data class FactTable4 (
        @Id var id: String,
        val avgPeopleInOneRoom: Int,
        val aOnlyStudents: Int,
        val aAndBOnlyStudents: Int,
        val aToEStudents: Int,
        val studentsWithDebts: Int,
        @ManyToOne
        val campus: Campus,
        @ManyToOne
        val time: Time
)

@Entity
data class Time (
    @Id val id: String,
    val year: Int
)


@Entity
data class Publisher (
    @Id val id: String,
    val edition: String
)

@Entity
data class Person (
        @Id val id: String,
        val firstName: String,
        val lastName: String,
        val sex: String,
        val birthDay: LocalDate,
        val privileges: Boolean,
        val birthPlace: String
)

@Entity
data class Room (
        @Id
        val id: String,
        val capacity: Int,
        val insects: Boolean,
        val warning: Int,
        val disinfection: LocalDate,
        @ManyToOne
        val campus: Campus
)

@Entity
data class StudyInfo (
        @Id
        val id: String,
        val type: String,
        val paid: Int,
        @OneToOne
        val person: Person
)

@Entity
data class Stay (
        @Id
        val id: String,
        @OneToOne
        val person: Person,
        @OneToOne
        val room: Room,
        val from: LocalDate,
        val to: LocalDate
)

@Entity
data class Conference (
        @Id val id: String,
        val name: String,
        val address: String,
        val date: LocalDate,
        @ManyToMany
        val participants: Set<Participant>
)

@Entity
data class Participant (
        @Id val id: String,
        val fullName: String,
        val position: String
)

@Entity
data class Publication (
        @Id val id: String,
        val name: String,
        @ManyToMany
        val authors: List<Participant>,
        val date: LocalDate
)

@Entity
data class Edition (
        @Id val id: String,
        val name: String,
        val editionType: String,
        val language: String,
        @ManyToMany
        val publications: List<Publication>
)

@Entity
data class LibraryRecord (
        @Id val id: String,
        @ManyToOne
        val edition: Edition,
        @ManyToOne
        val participant: Participant,
        val fromDate: LocalDate,
        val toDate: LocalDate
)
@Entity
data class University (@Id val id: String,
                       val name: String)
@Entity
data class Subject (@Id val id: String,
                    @ManyToOne
                    val discipline: Discipline,
                    @ManyToOne
                    val spec: Spec,
                    @ManyToOne
                    val teacher: Employee,
                    val semester: Int,
                    val year: String,
                    val maxScore: Int)


@Entity
data class Teacher (@Id val id: String,
                    @OneToOne
                    val person: Person,
                    @ManyToOne
                    val uni: University)

@Entity
data class Student (@Id val id: String,
                    @OneToOne
                    val person: Person,
                    @ManyToOne
                    val uni: University,
                    @OneToOne
                    val spec: Spec,
                    val format: String,
                    val type: String,
                    val inDate: LocalDate,
                    val outDate: LocalDate?,
                    @ManyToOne
                    val group: Group)

@Entity
data class Group(@Id val id: String,
                 val code: String,
                 val course: String,
                 val year: String,
                 val startDate: LocalDate,
                 val endDate: LocalDate,
                 val prevNames: String)

@Entity
data class Spec (@Id val id: String,
                 val name: String,
                 @ManyToOne
                 val uni: University)

@Entity
data class Score (@Id val id: String,
                  @ManyToOne
                  val student: Student,
                  @ManyToOne
                  val subject: Subject,
                  val score: Int,
                  val scoreLetter: String,
                  val scoreDate: LocalDate)


@Entity
data class Discipline (@Id val id: String,
                       val name: String,
                       val lectureHours: Int,
                       val practiceHours: Int,
                       val labHours: Int,
                       val controlType: String)


@Entity
data class Employee (@Id val id: String,
                     @OneToOne
                     val person: Person,
                     @ManyToOne
                     val uni: University,
                     val unit: String,
                     val position: String,
                     val startDate: LocalDate,
                     val endDate: LocalDate)


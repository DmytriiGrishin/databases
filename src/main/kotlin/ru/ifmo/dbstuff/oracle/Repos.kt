package ru.ifmo.dbstuff.oracle

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.ifmo.dbstuff.oracle.model.*

@Repository
interface OracleDisciplineRepo: JpaRepository<Discipline, String>

@Repository
interface OracleEmployeeRepo: JpaRepository<Employee, String>

@Repository
interface OracleGroupRepo: JpaRepository<Group, String>

@Repository
interface OraclePersonRepo: JpaRepository<Person, String>

@Repository
interface OracleScoreRepo: JpaRepository<Score, String>

@Repository
interface OracleSpecRepo: JpaRepository<Spec, String>

@Repository
interface OracleStudentRepo: JpaRepository<Student, String>

@Repository
interface OracleSubjectRepo: JpaRepository<Subject, String>

@Repository
interface OracleUniversityRepo: JpaRepository<University, String>


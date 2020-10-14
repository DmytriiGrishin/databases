package ru.ifmo.dbstuff.postgress

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.ifmo.dbstuff.postgress.model.*

@Repository("postgresDisciplineRepo")
interface DisciplineRepo: JpaRepository<Discipline, String>

@Repository("postgresEmployeeRepo")
interface EmployeeRepo: JpaRepository<Teacher, String>

@Repository("postgresPersonRepo")
interface PersonRepo: JpaRepository<Person, String>

@Repository("postgresScoreRepo")
interface ScoreRepo: JpaRepository<Score, String>

@Repository("postgresSpecRepo")
interface SpecRepo: JpaRepository<Spec, String>

@Repository("postgresStudentRepo")
interface StudentRepo: JpaRepository<Student, String>

@Repository("postgresSubjectRepo")
interface SubjectRepo: JpaRepository<Subject, String>

@Repository("postgresUniversityRepo")
interface UniversityRepo: JpaRepository<University, String>


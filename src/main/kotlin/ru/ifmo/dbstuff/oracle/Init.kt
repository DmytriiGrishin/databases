package ru.ifmo.dbstuff.oracle

import com.github.javafaker.Faker
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.ifmo.dbstuff.oracle.model.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.random.Random


@Component
class InitOracle(val oracleDisciplineRepo: OracleDisciplineRepo,
           val oracleEmployeeRepo: OracleEmployeeRepo,
           val oracleGroupRepo: OracleGroupRepo,
           val oraclePersonRepo: OraclePersonRepo,
           val oracleScoreRepo: OracleScoreRepo,
           val oracleSpecRepo: OracleSpecRepo,
           val oracleStudentRepo: OracleStudentRepo,
           val oracleSubjectRepo: OracleSubjectRepo,
           val oracleUniversityRepo: OracleUniversityRepo) {

    @EventListener(classes = [ContextRefreshedEvent::class])
    fun init() {
        val faker = Faker()
        val disciplines = createDisciplines(faker)
        oracleDisciplineRepo.saveAll(disciplines)
        val uni = createUni(faker)
        oracleUniversityRepo.save(uni)
        val persons = createPersons(faker)
        oraclePersonRepo.saveAll(persons)
        val specs = createSpecs(faker, uni)
        oracleSpecRepo.saveAll(specs)
        val groups = createGroups(faker)
        oracleGroupRepo.saveAll(groups)
        persons.subList(0, 150)
        val students = createStudents(faker, persons.subList(0, 150), uni, specs, groups)
        oracleStudentRepo.saveAll(students)
        val teachers = createTeachers(faker, persons.subList(150, 200), uni)
        oracleEmployeeRepo.saveAll(teachers)
        val subjects = createSubjects(faker, teachers, disciplines, specs)
        oracleSubjectRepo.saveAll(subjects)
        val score = createScore(faker, students, subjects)
        oracleScoreRepo.saveAll(score)
    }

    private fun createGroups(faker: Faker): List<Group> =
        (1..5).map {
            Group(
                    id = it.toString(),
                    course = abs(Random.nextInt() % 1) + 1,
                    code = faker.code().asin(),
                    year = abs(Random.nextInt() % 5) + 2015
            )
        }

    fun createDisciplines(random: Faker): List<Discipline> =
            (1..10).map {
                Discipline(
                        id = it.toString(),
                        labHours = abs(Random.nextInt() % 200),
                        lectureHours = abs(Random.nextInt() % 200),
                        practiceHours = abs(Random.nextInt() % 200),
                        name = random.educator().course(),
                        controlType = random.options().option("ЭКЗАМЕН", "ЗАЧЕТ")
                )
            }.toList()

    fun createUni(random: Faker): University =
            University(
                    id = UUID.randomUUID().toString(),
                    name = random.university().name()
            )


    fun createPersons(random: Faker): List<Person> =
            (1..200).map {
                Person(
                        id = it.toString(),
                        birthDay = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate().minusYears(30),
                        birthPlace = random.address().city(),
                        name = random.name().fullName(),
                        sex = random.options().option("М", "Ж")
                )
            }.toList()

    fun createSpecs(random: Faker, uni: University): List<Spec> =
            (1..5).map {
                Spec(
                        id = it.toString(),
                        uni = uni,
                        name = random.educator().course()
                )
            }.toList()

    fun createStudents(random: Faker, persons: List<Person>, uni: University, specs: List<Spec>, groups: List<Group>): List<Student> =
            persons.map {
                val inDate = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                Student(
                        id = UUID.randomUUID().toString(),
                        person = it,
                        spec = specs.random(),
                        uni = uni,
                        format = random.options().option("ПЛАТНОЕ", "БЮДЖЕТНОЕ"),
                        group = groups.random(),
                        inDate = inDate,
                        outDate =
                                random.date().between(
                                        Date.from(inDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                        Date.from(ZonedDateTime.now().toInstant())
                                ).toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate(),
                        type = random.options().option("ОЧНОЕ", "ЗАОЧНОЕ")
                )
            }.toList()

    fun createTeachers(random: Faker, persons: List<Person>, uni: University): List<Employee> =
            persons.map {
                val inDate = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                Employee(
                        id = UUID.randomUUID().toString(),
                        person = it,
                        uni = uni,
                        startDate = inDate,
                        endDate =
                                random.date().between(
                                        Date.from(inDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                        Date.from(ZonedDateTime.now().toInstant())
                                ).toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate(),
                        unit = random.educator().campus(),
                        position = random.job().position()
                )
            }.toList()

    fun createSubjects(random: Faker, teachers: List<Employee>, disciplines: List<Discipline>, specs: List<Spec>): List<Subject> =
            (1..80).map {
                Subject(
                        teacher = teachers.random(),
                        discipline = disciplines.random(),
                        spec = specs.random(),
                        maxScore = abs(Random.nextInt() % 40) + 60,
                        semester = abs(Random.nextInt() % 1) + 1,
                        year = abs(Random.nextInt() % 5) + 2015,
                        id = UUID.randomUUID().toString()
                )
            }.toList()
    fun createScore(random: Faker, students: List<Student>, subjects: List<Subject>): List<Score> =
            students.map {
                val subject = subjects.filter { sub -> sub.spec.id == it.spec.id }.random()
                val score = abs(Random.nextInt() % subject.maxScore) + 1
                val scoreLetter = when (score) {
                    in 0..subject.maxScore/2 -> "F"
                    in subject.maxScore/2..(subject.maxScore/8 + subject.maxScore/2) -> "C"
                    in (subject.maxScore/8 + subject.maxScore/2)..(subject.maxScore/4 + subject.maxScore/2) -> "B"
                    in (subject.maxScore/4 + subject.maxScore/2)..(subject.maxScore) -> "A"
                    else -> "A"
                }
                Score(
                        id = UUID.randomUUID().toString(),
                        subject = subject,
                        score = score,
                        student = it,
                        scoreDate = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        scoreLetter = scoreLetter
                )
            }.toList()
}
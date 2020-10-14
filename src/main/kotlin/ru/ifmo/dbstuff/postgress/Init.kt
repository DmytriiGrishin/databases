package ru.ifmo.dbstuff.postgress

import com.github.javafaker.Faker
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.ifmo.dbstuff.postgress.model.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.random.Random


@Component
class Init(val postgresDisciplineRepo: DisciplineRepo,
           val postgresEmployeeRepo: EmployeeRepo,
           val postgresPersonRepo: PersonRepo,
           val postgresScoreRepo: ScoreRepo,
           val postgresSpecRepo: SpecRepo,
           val postgresStudentRepo: StudentRepo,
           val postgresSubjectRepo: SubjectRepo,
           val postgresUniversityRepo: UniversityRepo) {

    @EventListener(classes = [ContextRefreshedEvent::class])

    fun init() {
        val faker = Faker()
        val disciplines = createDisciplines(faker)
        postgresDisciplineRepo.saveAll(disciplines)
        val uni = createUni(faker)
        postgresUniversityRepo.save(uni)
        val persons = createPersons(faker)
        postgresPersonRepo.saveAll(persons)
        val specs = createSpecs(faker, uni)
        postgresSpecRepo.saveAll(specs)
        persons.subList(0, 150)
        val students = createStudents(faker, persons.subList(0, 150), uni, specs)
        postgresStudentRepo.saveAll(students)
        val teachers = createTeachers(faker, persons.subList(150, 200), uni)
        postgresEmployeeRepo.saveAll(teachers)
        val subjects = createSubjects(faker, teachers, disciplines, specs)
        postgresSubjectRepo.saveAll(subjects)
        val score = createScore(faker, students, subjects)
        postgresScoreRepo.saveAll(score)
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

    fun createStudents(random: Faker, persons: List<Person>, uni: University, specs: List<Spec>): List<Student> =
            persons.map {
                Student(
                    id = UUID.randomUUID().toString(),
                    person = it,
                    spec = specs.random(),
                    uni = uni
                )
            }.toList()

    fun createTeachers(random: Faker, persons: List<Person>, uni: University): List<Teacher> =
            persons.map {
                Teacher(
                    id = UUID.randomUUID().toString(),
                    person = it,
                    uni = uni
                )
            }.toList()

    fun createSubjects(random: Faker, teachers: List<Teacher>, disciplines: List<Discipline>, specs: List<Spec>): List<Subject> =
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
                Score(
                        id = UUID.randomUUID().toString(),
                        subject = subject,
                        score = abs(Random.nextInt() % subject.maxScore),
                        student = it,
                        scoreDate = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                )
            }.toList()
}
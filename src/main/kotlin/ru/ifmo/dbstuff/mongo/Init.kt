package ru.ifmo.dbstuff.mongo

import com.github.javafaker.Faker
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.random.Random


@Component
class MongoMysql(
        val mongoPersonRepo: MongoPersonRepo,
        val mongoRoomRepo: MongoRoomRepo,
        val mongoStudyInfoRepo: MongoStudyInfoRepo,
        val mongoCampusRepo: MongoCampusRepo,
        val mongoStayRepo: MongoStayRepo
        ) {

    @EventListener(classes = [ContextRefreshedEvent::class])
    fun init() {
        val faker = Faker()
        val persons = createPersons(faker)
        mongoPersonRepo.saveAll(persons)
        val rooms = createRooms(faker)
        mongoRoomRepo.saveAll(rooms)
        val campus = createCampuses(faker, rooms)
        mongoCampusRepo.saveAll(campus)
        val studyInfos = createStudyInfos(faker, persons)
        mongoStudyInfoRepo.saveAll(studyInfos)
        val stay = createStay(faker, persons, rooms)
        mongoStayRepo.saveAll(stay)
    }


    fun createPersons(random: Faker): List<Person> =
            (1..200).map {
                Person(
                        id = it.toString(),
                        firstName = random.name().firstName(),
                        lastName = random.name().lastName(),
                        privileges = random.bool().bool()
                )
            }.toList()

    fun createRooms(random: Faker): List<Room> =
            (1..50).map {
                Room(
                        id = it.toString(),
                        capacity = abs(Random.nextInt() % 4) + 1,
                        disinfection = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        insects = random.bool().bool(),
                        warning = abs(Random.nextInt() % 10) + 1
                )
            }.toList()

    fun createStudyInfos(random: Faker, persons: List<Person>): List<StudyInfo> =
            persons.map {
                StudyInfo(
                        id = it.toString(),
                        type = random.options().option("ПЛАТНОЕ", "БЮДЖЕТНОЕ"),
                        paid = abs(Random.nextInt() % 10000),
                        person = it
                )
            }.toList()

    fun createCampuses(random: Faker, rooms: List<Room>): List<Campus> =
            (1..3).map {
                val subList = rooms.subList(rooms.size / 3 * (it - 1), rooms.size / 3 * it)
                Campus(
                        id = UUID.randomUUID().toString(),
                        rooms = subList,
                        address = random.address().fullAddress(),
                        numberOfRooms = subList.size
                )
            }.toList()

    fun createStay(random: Faker, persons: List<Person>, rooms: List<Room>): List<Stay> =
            persons.map {
                val inDate = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                Stay(
                        id = UUID.randomUUID().toString(),
                        person = it,
                        from = inDate,
                        to = random.date().between(
                                Date.from(inDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                Date.from(ZonedDateTime.now().toInstant())
                        ).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        room = rooms.random()
                )
            }.toList()

}
package ru.ifmo.dbstuff.mysql

import com.github.javafaker.Faker
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.ifmo.dbstuff.mysql.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.random.Random


@Component
class InitMysql(
        val conferenceRepo: ConferenceRepo,
        val participantRepo: ParticipantRepo,
        val publicationRepo: PublicationRepo,
        val editionRepo: EditionRepo,
        val libraryRecordRepo: LibraryRecordRepo
        ) {

    @EventListener(classes = [ContextRefreshedEvent::class])
    fun init() {
        val faker = Faker()
        val participants = createParticipant(faker)
        participantRepo.saveAll(participants)
        val conferences = createConference(faker, participants)
        conferenceRepo.saveAll(conferences)
        val publications = createPublications(faker, participants)
        publicationRepo.saveAll(publications)
        val editions = createEditions(faker, publications)
        editionRepo.saveAll(editions)
        val libraryRecords = createLibrary(faker, participants, publications)
        libraryRecordRepo.saveAll(libraryRecords)
    }

    fun createPublications(random: Faker, participants: List<Participant>): List<Publication> =
        (1..10).map {
            val from = abs(Random.nextInt()) % 50
            val to =  abs(Random.nextInt()) % (50 - from) + from
            Publication(
                id = it.toString(),
                authors = participants.shuffled().subList(from, to),
                    name = random.book().title(),
                    date = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            )
        }.toList()

    fun createParticipant(random: Faker): List<Participant> =
        (1..50).map {
            Participant(
                id = it.toString(),
                    position = random.job().position(),
                    fullName = random.name().fullName()
            )
        }.toList()

    fun createConference(random: Faker, participants: List<Participant>): List<Conference> =
            (1..5).map {
                val from = abs(Random.nextInt()) % 50
                val to =  abs(Random.nextInt()) % (50 - from) + from
                Conference(
                    id = it.toString(),
                    participants = participants.shuffled().subList(from, to).toSet(),
                        date = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                        name = random.esports().event(),
                        address = random.address().fullAddress()
                )
            }.toList()


    fun createEditions(random: Faker, publications: List<Publication>): List<Edition> =
            (1..5).map {
                val from = abs(Random.nextInt()) % 10
                val to =  abs(Random.nextInt()) % (10 - from) + from
                Edition(
                    id = it.toString(),
                    publications = publications.shuffled().subList(from, to),
                        name = random.book().publisher(),
                        editionType = random.options().option("вак", "ринц"),
                        language = random.nation().language()
                )
            }.toList()

    fun createLibrary(random: Faker, participants: List<Participant>, editions: List<Publication>): List<LibraryRecord> =
            (1..80).map {
                val inDate = random.date().past(365 * 5, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()

                LibraryRecord(
                    id = it.toString(),
                    edition = editions.random(),
                    participant = participants.random(),
                        fromDate = inDate,
                        toDate =
                                random.date().between(
                                        Date.from(inDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                        Date.from(ZonedDateTime.now().toInstant())
                                ).toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                )
            }.toList()

}
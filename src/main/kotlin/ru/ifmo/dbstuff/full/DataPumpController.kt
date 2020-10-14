package ru.ifmo.dbstuff.full

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.ifmo.dbstuff.mysql.EditionRepo
import java.util.*

@RestController
class DataPumpController(
        val editionRepo: EditionRepo,
        val timeRepo: TimeRepo,
        val publisherRepo : PublisherRepo,
        val factTable3Repo: FactTable3Repo
) {

    @GetMapping("pump3")
    fun pump3() {
        fact3()
    }

    private fun fact3() {

        val editions = editionRepo.findAll()
        val publishers = editions.map { Publisher(id = it.id, edition = it.name) }
        publisherRepo.saveAll(publishers)
        val years = editions.flatMap { it.publications }.map { it.date.year }
        years.forEach {
            if (!timeRepo.existsById(it.toString()))
                timeRepo.save(Time(id = it.toString(), year = it))
        }
        val facts = years.map { year ->
            editions.map {
                val authors = it.publications.filter { it.date.year == year }.flatMap { it.authors }.count()
                Pair(authors, it.id)
            }.map {
                FactTable3(
                        id = UUID.randomUUID().toString(),
                        people = it.first,
                        publisher = publisherRepo.findById(it.second).get(),
                        time = timeRepo.findById(year.toString()).get()
                )
            }
        }.flatten()

        factTable3Repo.saveAll(facts)
    }

}
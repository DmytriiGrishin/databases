package ru.ifmo.dbstuff

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import ru.ifmo.dbstuff.mongo.MongoPersonRepo
import ru.ifmo.dbstuff.mongo.Person

@SpringBootTest
@EnableMongoRepositories
class DbstuffApplicationTests(@Autowired val mongoPersonRepo: MongoPersonRepo) {

    @Test
    fun contextLoads() {
        mongoPersonRepo.save(Person(
                id = "id",
                lastName = "lastName1",
                firstName = "firstName1",
                privileges = true
        ))
        Assertions.assertEquals("firstName1", mongoPersonRepo.findById("id").get().firstName)
    }

}

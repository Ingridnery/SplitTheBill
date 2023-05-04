package br.edu.scl.ifsp.ads.splitthebill.controller

import androidx.room.Room
import br.edu.scl.ifsp.ads.splitthebill.model.PersonDao
import br.edu.scl.ifsp.ads.splitthebill.model.PersonDaoRoom
import br.edu.scl.ifsp.ads.splitthebill.view.ValueActivity
import java.util.concurrent.CountDownLatch

class ValueController(private val valueActivity: ValueActivity){
    private val personDao: PersonDao = Room.databaseBuilder(valueActivity, PersonDaoRoom::class.java, PersonDaoRoom.PERSON_DATABASE_FILE).build().getPersonDao()

private fun calculateValuePerPerson() : Double {
    var total = 0.00
    val latch = CountDownLatch(1)
    Thread {
        val persons = personDao.retrievePersons()
        val size = persons.size
        for (person in persons){
            total += person.value
        }
        total /= size
        latch.countDown()
    }.start()
    latch.await()
    return total
}

    fun getPersons(){
        Thread {
            val persons = personDao.retrievePersons()
            valueActivity.runOnUiThread {
                valueActivity.updatePersonList(persons, calculateValuePerPerson())
            }
        }.start()
    }

}
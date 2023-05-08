package br.edu.scl.ifsp.ads.splitthebill.controller

import androidx.room.Room
import br.edu.scl.ifsp.ads.splitthebill.model.Person
import br.edu.scl.ifsp.ads.splitthebill.model.PersonDao
import br.edu.scl.ifsp.ads.splitthebill.model.PersonDaoRoom
import br.edu.scl.ifsp.ads.splitthebill.view.MainActivity
interface OnPersonInsertedListener {
    fun onPersonInserted()
}
class PersonController(private val mainActivity: MainActivity) {
    private val personDao: PersonDao = Room.databaseBuilder(mainActivity, PersonDaoRoom::class.java, PersonDaoRoom.PERSON_DATABASE_FILE).build().getPersonDao()
    fun insertPerson(person: Person, listener: OnPersonInsertedListener){
        Thread{
            personDao.createPerson(person)
            mainActivity.runOnUiThread {
                listener.onPersonInserted()
            }
        }.start()
    }
    fun getPersons(){
        Thread {
            val persons = personDao.retrievePersons()
            mainActivity.runOnUiThread {
                mainActivity.updatePersonList(persons)
            }
        }.start()
    }
    fun getPerson(id: Int) = personDao.retrievePerson(id)

    fun editPerson(person: Person) {
        Thread{
            personDao.updatePerson(person)
        }.start()
    }
    fun removePerson(person: Person){
        Thread{
            personDao.deletePerson(person)
        }.start()
    }
    fun removeAllPersons(){
        Thread{
            personDao.deleteAllPersons()
        }.start()
    }

}
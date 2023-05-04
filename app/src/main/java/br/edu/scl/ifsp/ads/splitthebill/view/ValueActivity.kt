package br.edu.scl.ifsp.ads.splitthebill.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.splitthebill.adapter.PersonAdapter
import br.edu.scl.ifsp.ads.splitthebill.controller.ValueController
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityValueBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Person

class ValueActivity : AppCompatActivity () {
    private val avb: ActivityValueBinding by lazy {
        ActivityValueBinding.inflate(layoutInflater)
    }
    private val valueController: ValueController by lazy {
        ValueController(this)
    }
    private val personList : MutableList<Person> = mutableListOf()
    private val personAdapter: PersonAdapter by lazy {
        PersonAdapter(this, personList,"ValueActivity")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(avb.root)
        supportActionBar?.subtitle = ("Information of Values")
        valueController.getPersons()
        avb.personsLv.adapter = personAdapter


    }
    fun updatePersonList(_personList: MutableList<Person>, valuePerPerson: Double){
        personList.clear()
        for (person in _personList){
            person.value = valuePerPerson - person.value
        }
        personList.addAll(_personList)
        personAdapter.notifyDataSetChanged()
    }
}
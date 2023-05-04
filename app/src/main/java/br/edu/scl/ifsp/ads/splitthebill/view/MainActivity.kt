package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.adapter.PersonAdapter
import br.edu.scl.ifsp.ads.splitthebill.controller.PersonController
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Person

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val personList : MutableList<Person> = mutableListOf()
    private val personAdapter: PersonAdapter by lazy {
        PersonAdapter(this, personList,"MainActivity")
    }
    private val personController: PersonController by lazy {
        PersonController(this)
    }
    private lateinit var arl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        supportActionBar?.subtitle = ("Split the Bill")
        personController.getPersons()
        amb.personsLv.adapter = personAdapter

        arl= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == RESULT_OK){
                val person = result.data?.getParcelableExtra<Person>("Person")

                person?.let{_person ->
                    val position = personList.indexOfFirst{it.id == _person.id}
                    if(position != -1){
                        personList[position] = _person
                        personController.editPerson(_person)
                        Toast.makeText(this,"Person updated!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        personController.insertPerson(_person)
                        Toast.makeText(this,"Person inserted!", Toast.LENGTH_SHORT).show()
                        personAdapter.notifyDataSetChanged()
                        personController.getPersons()

                    }
                    personAdapter.notifyDataSetChanged()
                }
            }
        }
        registerForContextMenu(amb.personsLv)

        amb.personsLv.setOnItemClickListener { _, _, position, _ ->
            val person = personList[position]
            val personIntent = Intent(this@MainActivity, PersonActivity::class.java)
            personIntent.putExtra("Person", person)
            personIntent.putExtra("ViewPerson", true)
            arl.launch(personIntent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addPersonMi -> {
                val personIntent = Intent(this, PersonActivity::class.java)
                arl.launch(personIntent)
                true
            }
            R.id.calculateValueMi -> {
                val calculateIntent = Intent(this, ValueActivity::class.java)
                arl.launch(calculateIntent)
                true
            }
            else -> false
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View, menuInfo: ContextMenu.ContextMenuInfo?){
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val person = personList[position]
        return when(item.itemId){
            R.id.editPersonMi ->{
                val person = personList[position]
                val personIntent = Intent(this, PersonActivity::class.java)
                personIntent.putExtra("Person", person)
                arl.launch(personIntent)
                true
            }
            R.id.removePersonMi ->{
                personList.removeAt(position)
                personController.removePerson(person)
                personAdapter.notifyDataSetChanged()
                Toast.makeText(this,"Person removed!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }
    fun updatePersonList(_personList: MutableList<Person>){
        personList.clear()
        personList.addAll(_personList)
        personAdapter.notifyDataSetChanged()
    }
}
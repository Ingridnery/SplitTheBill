package br.edu.scl.ifsp.ads.splitthebill.view

import android.app.Activity
import android.content.Intent
import br.edu.scl.ifsp.ads.splitthebill.model.Person
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityPersonBinding

class PersonActivity:  AppCompatActivity()  {
    private val acb: ActivityPersonBinding by lazy {
        ActivityPersonBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(acb.root)
        supportActionBar?.subtitle = ("Information of Person")

        val receivedPerson = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("Person", Person::class.java)
        }
        else{
            intent.getParcelableExtra("Person")
        }
        receivedPerson?.let{ _receivedPerson->
            with(acb){
                with(_receivedPerson){
                    nameEt.setText(name)
                    valueEt.setText(value.toString())
                    itemsEt.setText(items)

                }
            }
        }
        val viewPerson = intent.getBooleanExtra("ViewPerson", false)
        with(acb){
            nameEt.isEnabled = !viewPerson
            valueEt.isEnabled = !viewPerson
            itemsEt.isEnabled = !viewPerson
            saveBt.visibility= if(viewPerson) View.GONE else View.VISIBLE
        }
        acb.saveBt.setOnClickListener(){
            val person: Person = Person(receivedPerson?.id, acb.nameEt.text.toString(),
                acb.valueEt.text.toString().toDouble(), acb.itemsEt.text.toString())

            val resultIntent = Intent()
            resultIntent.putExtra("Person", person)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

    }
}
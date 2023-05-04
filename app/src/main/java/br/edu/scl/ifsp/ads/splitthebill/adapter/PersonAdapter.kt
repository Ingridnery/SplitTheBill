package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.databinding.DetailContactBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Person

class PersonAdapter(context: Context, private val personList:MutableList<Person>, private val caller: String):
        ArrayAdapter<Person>(context, R.layout.detail_contact, personList) {
        private lateinit var dcb: DetailContactBinding
        override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val person: Person = personList[position]
                var detailContactView = convertView
                if (detailContactView == null) {
                        dcb = DetailContactBinding.inflate(
                                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                                parent, false
                        )
                        detailContactView = dcb.root

                        val dcvh = DetailContactViewHolder(
                                detailContactView.findViewById(R.id.nameTv),
                                detailContactView.findViewById(R.id.valueTv)
                        )
                        detailContactView.tag = dcvh
                }
                with(detailContactView.tag as DetailContactViewHolder){
                        nameTv.text = person.name

                        if(caller == "ValueActivity"){
                               if(person.value <0){
                                   val value = kotlin.math.abs(person.value)
                                   val formattedValue = String.format("%.2f", value)
                                   valueTv.text = "Deve receber R$ $formattedValue"

                               }
                               else{
                                   valueTv.text = String.format("Deve pagar R$ %.2f", person.value)

                               }
                               //valueTv.text = person.value.toString()
                       }
                        else{
                                valueTv.text = person.value.toString()
                        }
                }
                return detailContactView
        }
private data class DetailContactViewHolder(
        val nameTv: android.widget.TextView,
        val valueTv: android.widget.TextView)



}
package com.mustafaguvenc.personlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_person.view.*

class PersonListAdapter (var personList : List<Person>)
    : RecyclerView.Adapter<PersonListAdapter.PersonListViewHolder>(){

    var idList = HashSet<Int>() // kişi id lerini tutmak için
    var personListToSet = HashSet<Person>()
    var updatePage = listOf<Person>()
    var isComletedPersonforAdapter =false

    class PersonListViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person,parent,false)
        return PersonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonListViewHolder, position: Int) {
            val fullName = personList.get(position).fullName
            val personId = personList.get(position).id.toString()
            holder.view.textView2.text= fullName + " - ( " + personId +" )"
            if(!isComletedPersonforAdapter){
                idList.add(personList.get(position).id) // listelenen tüm kişilerin id si HashSette tutulur
                personListToSet.add(personList.get(position)) // listelenen tüm kişiler HashSette tutulur
            }

        }

    override fun getItemCount(): Int {
        return personList.size
    }

    fun updatePersonList(newPersonList: List<Person>, isComletedPerson: Boolean) {
        if(isComletedPerson){
            isComletedPersonforAdapter=true
            personList=newPersonList
            notifyDataSetChanged()
        }else{
            updatePage = newPersonList
            // daha önce listelenen kişilerin elenmesi
            for(i in newPersonList){
                if(idList.contains(i.id)){
                    updatePage=updatePage-i
                }
            }
            personList=updatePage

            if(updatePage.size!=0) notifyDataSetChanged() // hiç kişi kalmaz ise listeyi yenileme yapılmaz
        }


    }

}
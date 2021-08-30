package com.mustafaguvenc.personlist


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PersonListViewModel: ViewModel() {

    var dataSource = DataSource()
    private lateinit var fetchCompletionHandler : FetchCompletionHandler

    var fetchList = MutableLiveData<List<Person>>()
    var completeMessage = MutableLiveData<String>()
    var fetchNext : String? = " " // mainActivity den fetch metodunu çalıştırmak için

    fun getPersonList(next : String?){
        fetchCompletionHandler= object :FetchCompletionHandler{
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {

                if(p1?.people?.size==0){

                    completeMessage.value="No one is here !"
                    fetchNext = p1.next

                }else  if(p2!=null ){

                    Log.e("DataSourceError",p2.errorDescription)
                    dataSource.fetch(p1?.next,fetchCompletionHandler)

                }else{

                    fetchList.value = p1?.people
                    fetchNext = p1?.next

                }
            }
        }

        dataSource.fetch(next,fetchCompletionHandler)
    }
}
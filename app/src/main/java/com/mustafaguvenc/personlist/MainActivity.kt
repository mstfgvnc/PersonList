package com.mustafaguvenc.personlist


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private  lateinit var viewModel : PersonListViewModel
    private val adapter = PersonListAdapter(arrayListOf())

    // ilk kişi grupları tamamlandığında toplam kişi sayısını tutmak için oluşturuldu.
    var totalPeopleSize = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)
        viewModel.getPersonList(null)

        recyclerView.layoutManager=LinearLayoutManager(applicationContext)
        recyclerView.adapter=adapter

        // pull to refresh olayı
        swipeRefresh.setOnRefreshListener {

           swipeRefresh.isEnabled=false
           viewModel.getPersonList(viewModel.fetchNext)
           recyclerView.visibility=View.GONE
           personLoading.visibility=View.VISIBLE
           swipeRefresh.isRefreshing=false

        }

        // liste tamamlandığında reload olayı
        reload.setOnClickListener {

            adapter.updatePersonList(adapter.personListToSet.toList().sortedBy { it.id },true) // hashsette tututan kişi listesi adaptere gönderiliyor. id'ye göre sıralama yapılıyor
            recyclerView.visibility=View.VISIBLE
            swipeRefresh.isEnabled=true
            message.visibility=View.GONE
            it.visibility=View.GONE
        }

        observeLiveData()

    }
    fun observeLiveData(){
        viewModel.fetchList.observe(this, Observer {
            it?.let {

                message.visibility=View.GONE
                reload.visibility=View.GONE
                adapter.updatePersonList(it,false)  // listelemek için kişiler adaptere gönderilir

                if(adapter.updatePage.size!=0){
                    personLoading.visibility=View.GONE
                    recyclerView.visibility= View.VISIBLE
                    swipeRefresh.isEnabled=true

                } else{
                    // ekranda gösterilecek kişi yok ise yeni grup kişiler çağırılır.
                    viewModel.getPersonList(viewModel.fetchNext)
                }

            }
        })
        viewModel.completeMessage.observe(this, Observer {

                if((totalPeopleSize==-1 || adapter.idList.size==totalPeopleSize) && // toplam kişi sayısı başlangıçta -1 kabul edilir, ikinci ve diğer yüklemelerde toplama eşit olması beklenir
                    adapter.idList.size!=0 && // başlangıçta boş liste gelmesi durumunda
                    adapter.idList.size== Collections.max(adapter.idList)){  // tüm liste tamamlanmadan boş liste dönmesi durumu

                    reload.visibility=View.VISIBLE
                    message.visibility=View.VISIBLE
                    message.text=it
                    recyclerView.visibility=View.GONE
                    personLoading.visibility=View.GONE
                    totalPeopleSize = adapter.idList.size
                    swipeRefresh.isEnabled=false

                }else{

                    viewModel.getPersonList(viewModel.fetchNext)

                }

        })

    }



}
# Kişi Listesi

## Varsayımlar ve Parametreler 

* Belirsiz sayıda "Person" tipinde veri üreten bir veri kaynağı bulunmaktadır.("DataSorce.kt" sınıfı)
* "Person" tipi id ve isim parametrelerinden oluşmaktadır.
* Veriler id sırası ile üretilmektedir.
* Veri kaynağının "fetch" metodu ile üretilen verinin bir kısmı rastgele alınabilmektedir.
* Veriler alınırken boş liste, hata veya alınan verilerin tekrar gelmesi gibi sorunlar ile karşılaşılabilir.

## Amaç 

* Üretilen verilerin tamamını parçalar halinde almak ve ekranda listelemek.
* Listelenecek veriler aynı id değerine sahip olamazlar.

### Kullanılan Teknolojijer

* Hashset
* Swipe Refresh Layout
* ViewModel
* RecyclerView


## İzlenen Yol

* İlk liste alınarak olduğu gibi ekranda listelenmiştir.
* *Swipe Refresh Layout* ile sayfa yenilenerek diğer veriler için istek gönderilmiştir. 
* Alınan veriler bir *HashSet*te tutulmaktadır.Bu sayede yeni alınan liste ile mevcutta bulunan liste karşılaştırılarak farklı olan veriler ekranda listelenir.
* Veri alma durumunda, hata olması durumunda , boş liste dönmesi durumunda ve gelen listedeki verilerin tamamının mevcuttaki veriler olması durumlarında, kullanıcının etkilenmemesi için "Progress Bar" kullanılmıştır.Ve bu durumlarda istek yenilenmiştir.
* Boş liste dönmesi durumunda, eğer mevcutta tutulan veriler boş değilse, mevcutta bulunan verilerin en büyük id değerine bakılır. Eğer en büyük değer alınan veri sayısına eşit ise verilerin tamamının alındığı varsayılır. 
* Ve ekrana "No one is here !" yazısı verilir. Aynı zamanda verileri tekrar yüklemek için "RELOAD" butonu çıkmaktadır.
* RELOAD durumunda artık tüm verilerin elimizde olduğu varsayımına göre veriler "HashSet" ten alınarak id sırasına göre ekranda listelenir.
* Bu durumda "Swipe Refresh Layout" ile sayfa yenilendiğinde veri kaynağından tekrar veri listeleri alınır. ( Alınmayan verilerin olması ihtimaline karşı .)



![enter image description here](https://github.com/mstfgvnc/PersonList/blob/master/app/src/main/res/ss/1.jpg?raw=true)  ![enter image description here](https://github.com/mstfgvnc/PersonList/blob/master/app/src/main/res/ss/2.jpg?raw=true)  ![enter image description here](https://github.com/mstfgvnc/PersonList/blob/master/app/src/main/res/ss/3.jpg?raw=true)  ![enter image description here](https://github.com/mstfgvnc/PersonList/blob/master/app/src/main/res/ss/4.jpg?raw=true)  ![enter image description here](https://github.com/mstfgvnc/PersonList/blob/master/app/src/main/res/ss/5.jpg?raw=true)  



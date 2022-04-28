package com.sebade.relasiroom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HostNavigationActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_navigation)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navController = findNavController(R.id.container_fragment)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.itemIconTintList = null


        val list = mutableListOf(
            R.id.dashboardFragment,
            R.id.ticketFragment,
            R.id.profileFragment,
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (list.contains(destination.id)) {
                bottomNavigationView.visibility = View.VISIBLE
            } else {
                Log.d("TAG", "onCreate: ${destination.id} || ${R.id.home_page_fragment}")
                bottomNavigationView.visibility = View
                    .GONE
            }
        }
//        val database = DatabaseMov.getDatabase(application)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    /**
    private fun insertPlayer(listPlayer: MutableList<PlayItem?>, database: DatabaseMov) {
        val listInsertPlayer = mutableListOf<ActorTable>()
        listPlayer.forEach { playItem ->
            playItem!!.nama?.let { nama ->
                listInsertPlayer.add(
                    ActorTable(nama, playItem.url)
                )
            }
        }
        GlobalScope.launch {
            database.databaseDao.insertPlayer(listInsertPlayer)
        }
    }



    private fun insertFilm(film: List<FilmItem?>?, database: DatabaseMov) {
        val listFilm = mutableListOf<FilmTable>()
        val listPlayer = mutableListOf<ActorTable>()
        val refPlayerFilmList = mutableListOf<FilmActorCrossRef>()

        film?.forEach { it ->
            it!!.play?.let { listPlay ->
                listPlay.forEach { playerItem ->
                    val refFilmPlay =
                        FilmActorCrossRef(it.title.toString(), playerItem!!.nama.toString())
                    val actor =
                        ActorTable(playerItem!!.nama!!.toString(), playerItem!!.url!!.toString())
                    if(actor !in listPlayer){
                        listPlayer.add(actor)
                    }
                    refPlayerFilmList.add(refFilmPlay)
                }
            }
            it!!.title?.let { it1 ->
                val film = FilmTable(
                    it.director ?: it.directors, it.genre, it.rating,
                    it1, it.judul, it.poster, it.desc
                )
                listFilm.add(film)
            }
        }
        Log.d("TAG", "insertFilm: $refPlayerFilmList")
        GlobalScope.launch {
            database.databaseDao.insertFilm(listFilm)
            database.databaseDao.insertPlayer(listPlayer)
            database.databaseDao.insertRefFilmPlayer(refPlayerFilmList)
//            Log.d("TAG", "insertFilm: ${database.databaseDao.getFilm()} ")
        }


    }

    private fun insertUser(userResponse: List<UserItem?>?, database: DatabaseMov) {
        userResponse!!.forEach {
            if (it != null) {
                if (it.username == "1" && it.password == "1") {
                    var userTable = UserTable(
                        null,
                        it.password,
                        it.nama,
                        it.saldo,
                        it.email,
                        it.url,
                        it.username
                    )
                    GlobalScope.launch {
                        database.databaseDao.insertUser(userTable)
                        var get = database.databaseDao.getUser()
//                        Log.d("TAG", "insertUser: $get")
                    }

                }
            } else {
                return@forEach
            }
        }
    }
    */
}

/**

val client = ApiConfig.retrofitService.getAllData()
client.enqueue(object : retrofit2.Callback<NetworkResponse> {
    override fun onResponse(call: Call<NetworkResponse>, response: Response<NetworkResponse>) {
        if (response.isSuccessful) {
            val userResponse: List<UserItem?>? = response.body()?.user
            insertUser(userResponse, database)

            with(response.body()) {
                insertFilm(this?.film, database)

//                        var listPlayer = mutableListOf<PlayItem?>()
//                        this?.film?.forEach { filmItem ->
//                            filmItem?.play?.forEach { playItem ->
//                                if (playItem !in listPlayer) {
//                                    listPlayer.add(playItem)
//                                }
//                            }
//                        }
//                        insertPlayer(listPlayer, database)

                GlobalScope.launch {
                    Log.d("TAG", "onResponse: ${database.databaseDao.getTransaction()} ")
                }
            }

        }
    }

    override fun onFailure(call: Call<NetworkResponse>, t: Throwable) {
        TODO("Not yet implemented")
    }

})

 */
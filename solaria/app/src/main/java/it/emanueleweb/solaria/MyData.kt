package it.emanueleweb.solaria

import it.emanueleweb.solaria.db.AppDatabase
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object MyData {

    private val favDao = AppDatabase.getDatabase(MyApplication.appContext).favDao()
    private var data: Data = Data(listOf())

    //

    @Serializable
    data class Entity(
        val id: String,
        val name: String,
        val description: String,
        var favourite: Boolean = false,
        val image: String)

    @Serializable
    data class Data(val data: List<Entity>)

    //

    private val _luoghi = mutableListOf<Entity>()
    val luoghi: List<Entity>
        get() = _luoghi

    private val _attrattive = mutableListOf<Entity>()
    val attrattive: List<Entity>
        get() = _attrattive

    private val _eventi = mutableListOf<Entity>()
    val eventi: List<Entity>
        get() = _eventi

    private var _luoghiSearch = mutableListOf<Entity>()
    val luoghiSearch: List<Entity>
        get() = _luoghiSearch

    private var _attrattiveSearch = mutableListOf<Entity>()
    val attrattiveSearch: List<Entity>
        get() = _attrattiveSearch

    private var _eventiSearch = mutableListOf<Entity>()
    val eventiSearch: List<Entity>
        get() = _eventiSearch

    var searchLock = "l"
    var searchKey = ""

    //

    fun populateAll() {

        data.data.forEach {

            val id = it.id
            val name = it.name
            val description = it.description
            val image = it.image
            val fav = favDao.getFav(it.id)?.let { true } ?: false
            val entity = Entity(id, name, description, fav, image)

            when(id[0]) {

                'l' -> { _luoghi.add(entity) }
                'a' -> { _attrattive.add(entity) }
                'e' -> { _eventi.add(entity) }
            }
        }
    }

    fun search(s: String) {

        when(searchLock) {

            "l" -> {
                _luoghiSearch = luoghi.filter { it.name.contains(s, true) }
                        as MutableList<Entity>
            }

            "a" -> {
                _attrattiveSearch = attrattive.filter { it.name.contains(s, true) }
                        as MutableList<Entity>
            }

            "e" -> {
                _eventiSearch = eventi.filter { it.name.contains(s, true) }
                        as MutableList<Entity>
            }
        }
    }

    fun searchFav(s: String) {

        _luoghiSearch = luoghi.filter { it.name.contains(s, true) }
                as MutableList<Entity>

        _attrattiveSearch = attrattive.filter { it.name.contains(s, true) }
                as MutableList<Entity>

        _eventiSearch = eventi.filter { it.name.contains(s, true) }
                as MutableList<Entity>
    }

    //

    fun deserialize() {

        val json = MyIO.LocalIO.loadFile("data.json")
        this.data = Json.decodeFromString(json)
    }
}

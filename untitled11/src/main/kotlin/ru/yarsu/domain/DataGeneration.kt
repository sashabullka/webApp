package ru.yarsu.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper




import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.ThreadLocalRandom
class DataGeneration(){


fun id(): String {
    var id = ""
    id+=((10000..99999).random()).toString()
    return id

}

fun dateGenerationProject(): LocalDateTime? {
    val minDay = LocalDate.of(2013, 12, 31).toEpochDay()
    val maxDay = LocalDate.of(2021, 12, 31).toEpochDay()
    val randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)
    val localTime = LocalTime.of((0..23).random(), (0..59).random())
    val localDateTime = LocalDateTime.of(LocalDate.ofEpochDay(randomDay), localTime)
    return localDateTime
}

fun dateGenerationPeople(x: Int): LocalDateTime? {
    val minDay = LocalDate.of(x+1, 12, 31).toEpochDay()
    val maxDay = LocalDate.of(2024, 12, 31).toEpochDay()
    val randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)
    val localTime = LocalTime.of((0..23).random(), (0..59).random())
    val localDateTime = LocalDateTime.of(LocalDate.ofEpochDay(randomDay), localTime)
    return localDateTime
}




fun coordinates(): String {
    var id = ""
    id+=((0..50).random()).toString() + "°" + ((0..50).random()).toString() + "'" + ((0..50).random()).toString() + "." + ((0..50).random()).toString() + "\"N" + " " + ((0..50).random()).toString() + "°" + ((0..50).random()).toString() + "'" + ((0..50).random()).toString() + "." + ((0..50).random()).toString() + "\"E"
    return id
}

fun cities(x: Int): String {

    val city = listOf("Atlanta", "Boston","Chicago", "Dallas","Detroit", "Honolulu","Houston", "Miami","Texas","Memphis")
    return city.get(x)
}

fun street(x: Int): String {

    val street = listOf("Broadway", "96th Street","59th Street", "Madison Avenue","Wall Street", "34th Street","Avenue A", "Avenue B","Park Avenue","Avenue D")
    return street.get(x)
}

fun description(x: Int): String {
    val descripton = listOf("Separate the sidewalks with anti-parking bollards. They are elegant, do not interfere with pedestrians and prevent violators from parking on the sidewalk.",
        "Raise the crossings. A crossing at the same level as the sidewalk is convenient; you don’t have to go up or down anywhere. And cars have an additional incentive to brake in time.",
    "Point the lights at pedestrians. In Moscow, the lights are often directed only at the roadway, and the sidewalks are not illuminated at all. To prevent pedestrians from walking in the dark, it is customary all over the world to install special lights that illuminate sidewalks and pedestrian paths.",
    "Lay paths along natural routes. That is, along the shortest routes. If this is not done, then pedestrians will still build paths and walk through holes in fences",
    "Wide the sidewalks. Narrow sidewalks are terribly inconvenient. We need to make 2.5 meters the minimum acceptable width of sidewalks.",
    "Remove obstacles from sidewalks. Billboards clutter the road. Advertising is an important thing, but there are other places for it. The same applies to telephone booths, lamp posts. They need to be placed so as not to cause difficulties for pedestrians.",
    "Provide seating. Whatever a person prefers, he is unlikely to refuse the opportunity to sit down somewhere after a long walk. Therefore, in squares, parks, courtyards and other similar places there should be a lot of benches, benches, movable chairs.",
    "Arrange special areas for walking dogs. A dog is man’s friend, but not always. The areas should be small, but within walking distance. One area for every four houses will make the yards cleaner and calmer.",
    "Think about protection from rain, wind and cold. You want to go for a walk even in bad weather. Canopies, wind barriers and infrared heaters can contribute to this. Naturally, they should not be installed everywhere, but in the most popular and attractive places.",
    "Seed the tram tracks with lawn. The tram rails themselves are not the most attractive sight. It will be much better if they are sowed with lawn. This will not only improve the aesthetics of the street, but will also make the tram quieter.")
    return descripton.get(x)
}

fun generation(){

    val proect = mutableListOf<Proect>()

    var projectDate = arrayOf<LocalDateTime?>()
    for(i in 0..9){
        projectDate += dateGenerationProject()

    }


    var people = arrayOf<Array<People>>()
    for (i in 0..9) {
        var array = arrayOf<People>()
        for (j in 0..99) {
            array += People(id(),dateGenerationPeople(projectDate[i].toString().split("-")[0].toInt()))
        }
        people += array
    }

    for(i in 0..9) {
        proect.add(Proect(cities(i), street(i), coordinates(), description(i), people[i], projectDate[i]
        ))
    }


        File("Data.json").createNewFile()
        val objectMapper = jacksonObjectMapper()
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        val string = objectMapper.writeValueAsString(proect)
        File("Data.json").writeText(string, Charsets.UTF_8)




} }

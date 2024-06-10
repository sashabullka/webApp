import kotlin.math.pow
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.pebbletemplates.pebble.PebbleEngine
import java.io.File
import java.io.StringWriter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ThreadLocalRandom


val objectMapper = jacksonObjectMapper()

class Proect(
    val namePunkt: String,
    val nameTerritory: String,
    val coordinates: String,
    val descripton: String,
    val people: Array<People>,
    val date: String,
)

class People(
    val id: String,
    val date: String,
)

fun id(): String {
    var id = ""
    id+=((10000..99999).random()).toString()
    return id

}

fun dateGenerationProject(): String {
    val minDay = LocalDate.of(2013, 12, 31).toEpochDay()
    val maxDay = LocalDate.of(2021, 12, 31).toEpochDay()
    val randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)
    return LocalDate.ofEpochDay(randomDay).toString()
}

fun dateGenerationPeople(x: Int): String {
    val minDay = LocalDate.of(x+1, 12, 31).toEpochDay()
    val maxDay = LocalDate.of(2024, 12, 31).toEpochDay()
    val randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)
    val localTime = LocalTime.of((0..23).random(), (0..59).random())
    val localDateTime = LocalDateTime.of(LocalDate.ofEpochDay(randomDay), localTime)
    return localDateTime.toString()
}


fun coordinates(): String {
    var id = ""
    id+=((0..50).random()).toString() + "°" + ((0..50).random()).toString() + "'" + ((0..50).random()).toString() + "." + ((0..50).random()).toString() + "\"N" + " " + ((0..50).random()).toString() + "°" + ((0..50).random()).toString() + "'" + ((0..50).random()).toString() + "." + ((0..50).random()).toString() + "\"E"
    return id
}

fun cities(x: Int): String {

    val city = listOf("Ярославль", "Санкт-Петербург","Москва", "Самара","Краснодар", "Липецк","Казань", "Кострома","Владикавказ","Владимир")
    return city.get(x)
}

fun street(x: Int): String {

    val street = listOf("Колмогорово", "Лескова","Зелинского", "Маяковского","Булгаково", "Иваново","Ленино", "Сталино","Маринино","Вороново")
    return street.get(x)
}

fun description(x: Int): String {
    val descripton = listOf("Отделить тротуары антипарковочными столбиками. Они изящные, не мешают пешеходам и не дают нарушителям парковаться на тротуаре.",
        "Приподнять переходы. Переход на одном уровне с тротуаром — это удобно, не нужно никуда спускаться и подниматься. А у машин есть дополнительный стимул вовремя затормозить.",
        "Направить фонари на пешеходов. В Москве часто фонари направлены только на проезжую часть, а тротуары не освещены совсем. Чтобы пешеходы не ходили в темноте, во всём мире принято устанавливать специальные фонари, освещающие тротуары и пешеходные дорожки.",
        "Прокладывать дорожки по естественным маршрутам. То есть, по наикратчайшим. Если этого не делать, то пешеходы всё равно будут прокладывать тропы и ходить через дыры в заборах",
        "Расширить тротуары. Узкие тротуары — это ужасно неудобно. Нужно сделать 2,5 метра минимально допустимой шириной тротуаров.",
        "Убрать препятствия с тротуаров. Рекламные щиты загромождают дорогу. Реклама — важная штука, но для неё найдутся другие места. То же самое относится к телефонным будкам, фонарным столбам. Их нужно ставить так, чтобы не вызывать затруднений у пешеходов.",
        "Обеспечить сидячие места. Что бы ни предпочитал человек, вряд ли он откажется от возможности где-нибудь присесть после долгой прогулки. Поэтому в скверах, парках, дворах и других похожих местах должно быть много скамеек, лавочек, передвижных стульев.",
        "Устроить специальные площадки для выгула собак. Собака — друг человека, но не всегда. Площадки должны быть небольшими, но в пешей доступности. Одна площадка на каждые четыре дома сделает дворы чище и спокойнее.",
        "Подумать о защите от дождя, ветра и холода. Гулять хочется даже в плохую погоду. Навесы, заграждения от ветра и инфракрасные обогреватели смогут этому поспособствовать. Естественно, ставить их нужно не везде, а в самых популярных и привлекательных местах.",
        "Засеять трамвайные пути газоном. Сами по себе трамвайные рельсы — не самое привлекательное зрелище. Станет гораздо лучше, если их засеять газоном. Это не только повысит эстетичность улицы, но и сделает трамвай тише")
    return descripton.get(x)
}

fun main(args: Array<String>) {
    val proecty = File("file.json")
    val proect = mutableListOf<Proect>()

    var projectDate = arrayOf<String>()
    for(i in 0..9){
        projectDate += dateGenerationProject()
    }


    var people = arrayOf<Array<People>>()
    for (i in 0..9) {
        var array = arrayOf<People>()
        for (j in 0..99) {
            array += People(id(),dateGenerationPeople(projectDate[i].split("-")[0].toInt()))
        }
        people += array
    }

    for(i in 0..9) {
        proect.add(Proect(cities(i), street(i), coordinates(), description(i), people[i], projectDate[i]))
    }
    val string = objectMapper.writeValueAsString(proect)
    proecty.writeText(string, Charsets.UTF_8)

    val catalog = File("portal")
    val catalogProjects = File("portal/myProjects")
    val catalogProjects2 = File("portal/index.html")
    catalog.mkdir()
    catalogProjects.mkdir()
    catalogProjects2.createNewFile()
    val sourceDir = File("bootstrap-5.3.3-dist")
    val targetDir = File("portal")


    try {
        sourceDir.copyRecursively(targetDir)


    } catch (e: Exception) {
        println("Exception occurred while copying.")
    }
     for(i in 0..9){
         val fileName = "portal/myProjects/Проект" + (i+1).toString() + ".html"
         var file = File(fileName)
         file.createNewFile()
     }


    val proects: List<Proect> = objectMapper.readValue(File("file.json").readText())

    val engine = PebbleEngine.Builder().build() // Создаём шаблонизатор
    val template1 = engine.getTemplate("home.peb")
    val writer1 = StringWriter()
    template1.evaluate(writer1)
    File("portal/index.html").writeText(writer1.toString(), Charsets.UTF_8)
    val template = engine.getTemplate("proects.peb") // Компилируем шаблон home.peb
    for(i in 0..9){
    val data = mapOf("proect" to proects[i])
    val writer = StringWriter()
    template.evaluate(writer, data) // Записать сформированную шаблоном
        File("portal/myProjects/Проект"+(i+1).toString()+".html").writeText(writer.toString(), Charsets.UTF_8)

    }
}
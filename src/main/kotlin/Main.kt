
import java.io.File


fun main() {

    val dictionary = mutableListOf<Word>()
    val wordsFile: File = File("words.txt")
    val wordsList = wordsFile.readLines()
    for(i in wordsList){
        val line = i.split("|")
        val word = Word(original = line[0], translate = line[1], correctAnswerCount = line[2].toIntOrNull() ?: 0)
        dictionary.add(word)
    }

    while(true) {


        //СТАРТ МЕНЮ
        println("Меню: 2 - Статистика, 0 - Выход")
        println("Выберите нужный пункт меню")
        val userInput = readLine()!!.toInt()
        when (userInput) {
            2 -> {  val dictionaryFilter = dictionary.filter{ //Фильтруем для выученных слов
                it.correctAnswerCount >= 3
            }
                val quantityWordsCorrect = dictionaryFilter.size //Количество выученных слов
                val percentageRatio = (100 / dictionary.size) * quantityWordsCorrect //Процентное соотношение
                println("Количество слов в словаре: ${dictionary.size}\nВыучено слов ${dictionaryFilter.size} " +
                    "из ${dictionary.size} | $percentageRatio%")}
            0 -> break
            else -> println("Такого пункта меню не существует, попробуйте ещё раз")
        }
    }
}
data class Word(
    val original:String,
    val translate:String,
    var correctAnswerCount: Int = 0, //Счётчик слов
)
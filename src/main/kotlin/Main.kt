
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
        println("Меню: 1 - Учить слова, 2 - Статистика, 0 - Выход")

        when (readLine()?.toInt()) {
            2 -> {  val dictionaryFilter = dictionary.filter{ //Фильтруем для выученных слов
                it.correctAnswerCount >= 3
            }
                val totalQuantityWords = dictionary.size
                val quantityWordsCorrect = dictionaryFilter.size //Количество выученных слов
                val percentageRatio = (100 / dictionary.size) * quantityWordsCorrect //Процентное соотношение
                println("Выучено $quantityWordsCorrect из $totalQuantityWords слов | $percentageRatio%")}
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
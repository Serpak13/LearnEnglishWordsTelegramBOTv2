
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

    val quantityWords = dictionary.size  //Количество слов в словаре
    val dictionary2 = dictionary.filter{ //Фильтруем для выученных слов
        it.correctAnswerCount != 0
    }
    val quantityWordsCorrect = dictionary2.size //Количество выученных слов
    val percentageRatio = (quantityWordsCorrect.toFloat() / quantityWords.toFloat()) * 100 //Процентное соотношение



    while(true) {
        println("Меню: 1- Учить слова, 2 - Статистика, 0 - Выход")
        println("Выберите нужный пункт меню")
        val userInput = readLine()!!.toInt()
        when (userInput) {
            2 ->println("Количество слов в словаре: $quantityWords\nВыучено слов ${dictionary2.size}  из $quantityWords | $percentageRatio%")
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
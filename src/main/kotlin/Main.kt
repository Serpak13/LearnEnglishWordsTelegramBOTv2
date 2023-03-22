import java.io.BufferedWriter
import java.io.File
import java.util.Dictionary


fun main() {

    val dictionary = mutableListOf<Word>()
    val wordsFile: File = File("words.txt")
    val wordsList = wordsFile.readLines()
    for (i in wordsList) {
        val line = i.split("|")
        val word = Word(original = line[0], translate = line[1], correctAnswerCount = line[2].toIntOrNull() ?: 0)
        dictionary.add(word)
    }

    while (true) {
        //СТАРТ МЕНЮ
        println("Меню: 1 - Учить слова, 2 - Статистика, 0 - Выход")

        when (readLine()?.toInt()) {
            1 -> {
                while (true) {
                    val unlearnedWords = dictionary.filter { it.correctAnswerCount < 3 }.shuffled().take(4)
                    if (unlearnedWords.isNotEmpty()) {
                        val correctAnswerWord = unlearnedWords.random() //Загаданное слово
                        val originalWord = correctAnswerWord.original // Ориганал загаданного слова
                        val translateWord = correctAnswerWord.translate // Перевод загаданного слова
                        val indexCorrectAnswerWord = unlearnedWords.indexOf(correctAnswerWord) // Индекс элемента
                        println(
                            "$originalWord\n1 - ${unlearnedWords[0].translate}, 2 - ${unlearnedWords[1].translate}, " +
                                    "3 - ${unlearnedWords[2].translate}, 4 - ${unlearnedWords[3].translate}"
                        )
                        println("Введите праильный вариант, если хотите выйти в главное меню, нажмите 0")
                        val userInput = readLine()?.toInt()
                        if (userInput == 0) break // выход в главное меню
                        else if (userInput == indexCorrectAnswerWord + 1) {  //Сравнение
                            println("ПРАВИЛЬНО")
                            correctAnswerWord.correctAnswerCount ++   //Инкремент счётчика правильных слов
                           saveDictionary(dictionary)
                        } else println("Неправильно - $originalWord [$translateWord]")
                    } else {
                        println("Вы выучили все слова")
                        break
                    }
                }
            }

            2 -> {
                val totalQuantityWords = dictionary.size
                val quantityWordsCorrect =
                    dictionary.filter { it.correctAnswerCount >= 3 }.size//Количество выученных слов
                val percentageRatio = (100 / dictionary.size) * quantityWordsCorrect //Процентное соотношение
                println("Выучено $quantityWordsCorrect из $totalQuantityWords слов | $percentageRatio%")
            }

            0 -> break
            else -> println("Такого пункта меню не существует, попробуйте ещё раз")
        }
    }
}

fun saveDictionary(dictionary: List<Word>){
    val writer = File("words.txt")
    writer.writeText("")
    for (i in dictionary){
        writer.appendText(i.original)
        writer.appendText("|")
        writer.appendText(i.translate)
        writer.appendText("|")
        writer.appendText(i.correctAnswerCount.toString())
        writer.appendText("\n")
    }

}

data class Word(
    val original: String,
    val translate: String,
    var correctAnswerCount: Int = 0, //Счётчик слов
)
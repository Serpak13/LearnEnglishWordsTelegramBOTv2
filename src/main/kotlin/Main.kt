import java.lang.Exception

data class Word(
    val original: String,
    val translate: String,
    var correctAnswerCount: Int = 0, //Счётчик слов
)

fun Question.asConsoleString(): String {
    val variants = this.variants
        .mapIndexed { index: Int, word: Word -> "${index + 1} - ${word.translate}" }
        .joinToString(separator = "\n")
    return this.correctAnswer.original + "\n" + variants + "\n0 - выйти в меню"
}

fun main() {

    val trainer = try {
        LearnWordsTrainer(3, 4)
    } catch (e:Exception){
        println("Невозможно загрузить словарь")
        return
    }

    while (true) {
        //СТАРТ МЕНЮ
        println("Меню: 1 - Учить слова, 2 - Статистика, 0 - Выход")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                while (true) {
                    val question = trainer.getNextQuestion()
                    if (question == null) {
                        println("Все слова выучены")
                        break
                    } else {
                        println(question.asConsoleString())

                        val userAnswerInput = readLine()?.toIntOrNull()
                        if (userAnswerInput == 0) break

                        if (trainer.checkAnswer(userAnswerInput?.minus(1))) {  //Сравнение
                            println("Правильно\n")
                        } else println("Неправильно - ${question.correctAnswer.original} - это ${question.correctAnswer.translate}\n")
                    }
                }
            }

            2 -> {
                val statistics = trainer.getStatistics()
                println("Выучено ${statistics.learned} из ${statistics.totalQuantityWords} слов | ${statistics.percentageRatio}%")
            }

            0 -> break
            else -> println("Введите 1, 2 или 0")
        }
    }
}



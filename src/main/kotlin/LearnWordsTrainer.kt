import java.io.File
import java.lang.IndexOutOfBoundsException

data class Statistics(
    val learned: Int,
    val totalQuantityWords: Int,
    val percentageRatio: Int,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)

class LearnWordsTrainer(private val learnedAnswerCount: Int = 3, private val countOfQuestionWords: Int = 4) {
    private var question: Question? = null
    private val dictionary = loadDictionary()

    fun getStatistics(): Statistics {
        val totalQuantityWords = dictionary.size
        val learned = dictionary.filter { it.correctAnswerCount >= learnedAnswerCount }.size//Количество выученных слов
        val percentageRatio = (100 / dictionary.size) * learned //Процентное соотношение
        return Statistics(learned, totalQuantityWords, percentageRatio)
    }

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { it.correctAnswerCount < learnedAnswerCount }
        if (notLearnedList.isEmpty()) return null
        val questionWords = if(notLearnedList.size < countOfQuestionWords){
           val learnedList = dictionary.filter { it.correctAnswerCount >= learnedAnswerCount }.shuffled()
            notLearnedList.shuffled().take(countOfQuestionWords) +
                    learnedList.take(countOfQuestionWords - notLearnedList.size)

        } else {
            notLearnedList.shuffled().take(countOfQuestionWords)
        }.shuffled()

        val correctAnswer = questionWords.random()
        question = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        return question
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {
        return question?.let{
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.correctAnswerCount++
                saveDictionary(dictionary)
                true
            } else {
                false
            }
        } ?: false

    }

    private fun loadDictionary(): List<Word> {
        try{
            val dictionary = mutableListOf<Word>()
            val wordsFile = File("words.txt")
            wordsFile.readLines().forEach {
                val splitLine = it.split("|")
                dictionary.add(Word(splitLine[0], splitLine[1], splitLine[2].toIntOrNull() ?: 0))
            }
            return dictionary
        } catch (e: IndexOutOfBoundsException){
            throw IllegalStateException("Некорректный файл")
        }
    }
    private fun saveDictionary(dictionary: List<Word>) {
        val writer = File("words.txt")
        writer.writeText("")
        for (i in dictionary) {
            writer.appendText("${i.original}|${i.translate}|${i.correctAnswerCount}\n")
        }
    }
}



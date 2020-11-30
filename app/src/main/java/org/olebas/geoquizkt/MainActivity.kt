package org.olebas.geoquizkt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, answer = true, isAnswered = false),
        Question(R.string.question_oceans, answer = true, isAnswered = false),
        Question(R.string.question_mideast, answer = false, isAnswered = false),
        Question(R.string.question_africa, answer = false, isAnswered = false),
        Question(R.string.question_americas, answer = true, isAnswered = false),
        Question(R.string.question_asia, answer = true, isAnswered = false)
    )

    private var currentIndex = 0
    private var rightAnswers = 0
    private var answered = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        questionTextView.setOnClickListener {
            nextQuestion()
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
            updateQuestion()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            updateQuestion()
        }

        nextButton.setOnClickListener {
            nextQuestion()
        }

        prevButton.setOnClickListener {
            prevQuestion()
        }

        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onResume() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun prevQuestion() {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else questionBank.size - 1
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        if (questionBank[currentIndex].isAnswered) {
            enableButtons(false)
        } else {
            enableButtons(true)
        }
        if (answered == questionBank.size) {
            resultToast()
            answered = 0
        }
    }

    private fun enableButtons(isEnable: Boolean) {
        if (isEnable) {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        } else {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId: Int
        if (userAnswer == correctAnswer) {
            rightAnswers++
            messageResId = R.string.correct_toast
        } else {
            messageResId = R.string.incorrect_toast
        }
        answered++
        questionBank[currentIndex].isAnswered = true
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun resultToast() {
        val roundNumber: DecimalFormat = DecimalFormat("#0.##")
        val percent: Double = rightAnswers.toDouble() / questionBank.size.toDouble()
        Toast.makeText(this, "Result: ${roundNumber.format(percent * 100)}%", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
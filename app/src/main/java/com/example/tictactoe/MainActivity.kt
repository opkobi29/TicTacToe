package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var textViewTurn: TextView
    private lateinit var buttonRestart: Button

    private var currentPlayer = "X" // "X" starts
    private var board = Array(9) { "" } // Empty board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Array(9) { i ->
            findViewById(resources.getIdentifier("button$i", "id", packageName))
        }

        textViewTurn = findViewById(R.id.textViewTurn)
        buttonRestart = findViewById(R.id.buttonRestart)

        for (i in buttons.indices) {
            buttons[i].setOnClickListener { onButtonClick(i) }
        }

        buttonRestart.setOnClickListener {
            restartGame()
        }
    }

    private fun onButtonClick(index: Int) {
        if (board[index].isNotEmpty()) return // already filled

        board[index] = currentPlayer
        buttons[index].text = currentPlayer
        buttons[index].setTextColor(if (currentPlayer == "X") Color.BLUE else Color.RED)

        if (checkWin()) {
            textViewTurn.text = getString(R.string.x_wins, currentPlayer)
            buttonRestart.visibility = View.VISIBLE
            disableButtons()
        } else if (board.all { it.isNotEmpty() }) {
            textViewTurn.text = getString(R.string.its_a_tie)
            textViewTurn.setBackgroundColor("#800080".toColorInt())
            buttonRestart.visibility = View.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            updateTurnDisplay()
        }
    }

    private fun updateTurnDisplay() {
        textViewTurn.text = getString(R.string.turn, currentPlayer)
        textViewTurn.setBackgroundColor(if (currentPlayer == "X") Color.BLUE else Color.RED)
    }

    private fun checkWin(): Boolean {
        val winPositions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // rows
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // columns
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                       // diagonals
        )
        for (pos in winPositions) {
            if (board[pos[0]] == currentPlayer &&
                board[pos[1]] == currentPlayer &&
                board[pos[2]] == currentPlayer
            ) {
                return true
            }
        }
        return false
    }

    private fun disableButtons() {
        for (button in buttons) {
            button.isEnabled = false
        }
    }

    private fun restartGame() {
        board = Array(9) { "" }
        for (button in buttons) {
            button.text = ""
            button.isEnabled = true
        }
        currentPlayer = "X"
        updateTurnDisplay()
        buttonRestart.visibility = View.GONE
    }
}

 


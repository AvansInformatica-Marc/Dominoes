package nl.marc.dominoes.players

import nl.marc.dominoes.game.BoardSide
import nl.marc.dominoes.game.DominoStone
import nl.marc.dominoes.game.PlayerInterface

@ExperimentalStdlibApi
object HumanConsolePlayer {
    fun createPlayer(game: PlayerInterface): () -> Unit = {
        makeMove(game)
    }

    private fun makeMove(game: PlayerInterface) {
        println("You have these dominoes: ${game.availableDominoes.joinToString(" ")}")
        val opponentDominoes = game.opponentDominoes
        if(opponentDominoes != null)
            println("Your opponent has these dominoes: ${opponentDominoes.joinToString(" ")}")

        askForAction(game)

        println()
    }

    private fun askForAction(game: PlayerInterface) {
        print("Select a domino, type 'n' to get a new domino (if available):")

        val selectedAction = readLine()!!.toLowerCase().trim()
        val domino = DominoStone.fromText(selectedAction)

        if(selectedAction == "n" || selectedAction == "new") {
            val newDomino = game.getNewDomino()
            println(if(newDomino == null) "There are currently no new domino available..." else "You got: $newDomino")
        } else if (domino == null) {
            println("⚠ Unknown action, try again...")
            askForAction(game)
            return
        } else if(game.board.isEmpty()) {
            game.layDominoOnBoard(domino, BoardSide.LEFT)
        } else {
            print("Type 'r' to add domino to the right, 'l' to add to the left side of the board: ")
            val side = when(readLine()?.toLowerCase()?.trim()) {
                "r", "right" -> BoardSide.RIGHT
                "l", "left" -> BoardSide.LEFT
                else -> null
            }

            try {
                game.layDominoOnBoard(domino, side!!)
            } catch (error: Exception) {
                println("⚠ Sorry, that's not possible. Try again.")
                askForAction(game)
                return
            }
        }
    }
}
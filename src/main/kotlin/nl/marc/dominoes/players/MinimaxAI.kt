package nl.marc.dominoes.players

import nl.marc.dominoes.game.BoardSide
import nl.marc.dominoes.game.Player
import nl.marc.dominoes.game.PlayerInterface
import kotlin.math.max
import kotlin.math.min

@ExperimentalStdlibApi
object MinimaxAI {
    fun createAI(game: PlayerInterface): () -> Unit = { makeMove(game) }

    private fun makeMove(game: PlayerInterface) {
        val possibleTurns = getPossibleTurns(game)
        if(possibleTurns.isEmpty()) {
            game.getNewDomino()
            return
        }

        val (domino, boardSide) = getPossibleTurns(game).maxBy { (domino, boardSide) ->
            val (selfCopy, opponentCopy) = game.copy()
            selfCopy.layDominoOnBoard(domino, boardSide)
            getValueForNode(opponentCopy, game.player)
        }!!

        game.layDominoOnBoard(domino, boardSide)
    }

    private fun getValueForNode(game: PlayerInterface, player: Player): Int {
        if(game.isDone) {
            val gameCopy = game.copy()
            return when {
                gameCopy.find { it.player == player }?.availableDominoes?.isEmpty() ?: false -> 1
                gameCopy.find { it.player != player }?.availableDominoes?.isEmpty() ?: false -> -1
                else -> 0
            }
        } else {
            val possibleNextTurns = getPossibleTurns(game)

            return if(possibleNextTurns.isEmpty()) {
                val (selfCopy, opponentCopy) = game.copy()
                selfCopy.getNewDomino()
                getValueForNode(opponentCopy, player)
            } else {
                var bestValue = if(game.player == player) Int.MIN_VALUE else Int.MAX_VALUE
                val minimaxFunction: (Int, Int) -> Int = if(game.player == player) ::max else ::min
                for ((domino, side) in possibleNextTurns) {
                    val (selfCopy, opponentCopy) = game.copy()
                    selfCopy.layDominoOnBoard(domino, side)
                    bestValue = minimaxFunction(bestValue, getValueForNode(opponentCopy, player))
                }
                bestValue
            }
        }
    }

    private fun getPossibleTurns(game: PlayerInterface) = game.availableDominoes.filter {
        it.left == game.firstNumber || it.right == game.firstNumber
    }.map {
        it to BoardSide.LEFT
    } + game.availableDominoes.filter {
        it.left == game.lastNumber || it.right == game.lastNumber
    }.map {
        it to BoardSide.RIGHT
    }
}


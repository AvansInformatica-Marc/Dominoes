package nl.marc.dominoes

import nl.marc.dominoes.game.RegularDominoGame
import nl.marc.dominoes.players.HumanConsolePlayer
import nl.marc.dominoes.players.MinimaxAI

@ExperimentalStdlibApi
fun main() {
    val playerThatWon = RegularDominoGame.create(
        MinimaxAI::createAI,
        HumanConsolePlayer::createPlayer
    )

    println()
    println(if(playerThatWon == null) "It's tie" else "Player $playerThatWon won!")
    println()
}
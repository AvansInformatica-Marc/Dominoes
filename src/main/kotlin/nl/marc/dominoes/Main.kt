package nl.marc.dominoes

import nl.marc.dominoes.game.RegularDominoGame
import nl.marc.dominoes.players.HumanConsolePlayer
import nl.marc.dominoes.players.MinimaxAI

@ExperimentalStdlibApi
fun main() {
    val playerThatWon = RegularDominoGame.create(
        HumanConsolePlayer::createPlayer,
        MinimaxAI::createAI
    )

    println()
    println("Player $playerThatWon won!")
    println()
}
package nl.marc.dominoes.game

@ExperimentalStdlibApi
class PlayerInterface(val player: Player, private val game: GameInterface, private val log: (String) -> Unit = { println(it) }) {
    val board: Collection<DominoStone>
        get() = game.board

    val availableDominoes: Collection<DominoStone>
        get() = game.getHandForPlayer(player)

    val opponentDominoes: Collection<DominoStone>?
        get() = if(game.isTransparent) game.getHandForPlayer(enumValues<Player>().first { it != player }) else null

    val isDone
        get() = game.isDone

    val firstNumber
        get() = game.firstNumber

    val lastNumber
        get() = game.lastNumber

    fun getNewDomino(): DominoStone? {
        log("Player ${if(player == Player.PLAYER1) 1 else 2} took a new domino")
        return game.giveDominoToPlayer(player)
    }

    fun layDominoOnBoard(domino: DominoStone, boardSide: BoardSide) {
        log("Player ${if(player == Player.PLAYER1) 1 else 2} laid $domino to the ${if(boardSide == BoardSide.RIGHT) "right" else "left"} side of the board")
        game.layDominoOnBoard(player, domino, boardSide)
    }

    fun copy(): List<PlayerInterface> {
        val newGame = game.copy(player)
        val player1 = PlayerInterface(Player.PLAYER1, newGame) {}
        val player2 = PlayerInterface(Player.PLAYER2, newGame) {}
        return when(player) {
            Player.PLAYER1 -> listOf(player1, player2)
            Player.PLAYER2 -> listOf(player2, player1)
        }
    }
}
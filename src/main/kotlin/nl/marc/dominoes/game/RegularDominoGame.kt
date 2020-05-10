package nl.marc.dominoes.game

@ExperimentalStdlibApi
class RegularDominoGame private constructor(isTransparent: Boolean): GameInterface(isTransparent) {
    override val unusedDominoes = DominoUtils.createDominoSet().toMutableList()

    override val board = ArrayDeque<DominoStone>()

    override val handPlayer1 = mutableListOf<DominoStone>()

    override val handPlayer2 = mutableListOf<DominoStone>()

    init {
        for (player in enumValues<Player>())
            for (index in 0 until 7)
                giveDominoToPlayer(player)
    }

    companion object {
        @ExperimentalStdlibApi
        fun create(createPlayer1: (PlayerInterface) -> () -> Unit, createPlayer2: (PlayerInterface) -> () -> Unit, isTransparent: Boolean = true): Int {
            val game: GameInterface = RegularDominoGame(isTransparent)

            val player1 = createPlayer1(PlayerInterface(Player.PLAYER1, game))
            val player2 = createPlayer2(PlayerInterface(Player.PLAYER2, game))

            while (!game.isDone) {
                listOf(player1, player2).forEachIndexed { index, player ->
                    if (!game.isDone) {
                        println()
                        println("Board: ${if(game.board.isEmpty()) "empty" else game.board.joinToString("")}")
                        println()

                        println("Player ${index + 1}, it's your turn!")
                        player()

                        println()
                    }
                }
            }

            return if(game.handPlayer1.isEmpty()) 1 else 2
        }
    }
}
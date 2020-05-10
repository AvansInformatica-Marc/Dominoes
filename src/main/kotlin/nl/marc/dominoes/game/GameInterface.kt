package nl.marc.dominoes.game

@ExperimentalStdlibApi
abstract class GameInterface(val isTransparent: Boolean = true) {
    abstract val unusedDominoes: MutableCollection<DominoStone>

    abstract val board: ArrayDeque<DominoStone>

    abstract val handPlayer1: MutableCollection<DominoStone>

    abstract val handPlayer2: MutableCollection<DominoStone>

    val firstNumber
        get() = board.firstOrNull()?.left

    val lastNumber
        get() = board.lastOrNull()?.right

    private val searchFunction: (DominoStone) -> Boolean = {
        it.left == firstNumber || it.right == firstNumber || it.left == lastNumber || it.right == lastNumber
    }

    val isDone: Boolean
        get() = handPlayer1.isEmpty() ||
                handPlayer2.isEmpty() ||
                (unusedDominoes.isEmpty() && handPlayer1.none(searchFunction) && handPlayer2.none(searchFunction))

    fun getHandForPlayer(player: Player) = when (player) {
        Player.PLAYER1 -> handPlayer1
        Player.PLAYER2 -> handPlayer2
    }

    fun giveDominoToPlayer(player: Player): DominoStone? {
        if(unusedDominoes.isEmpty())
            return null

        val domino = unusedDominoes.random()
        unusedDominoes -= domino
        getHandForPlayer(player) += domino
        return domino
    }

    fun layDominoOnBoard(player: Player, newStone: DominoStone, boardSide: BoardSide) {
        if(newStone !in getHandForPlayer(player))
            throw IllegalArgumentException()

        val addStoneFunction = when (boardSide) {
            BoardSide.LEFT -> ::addStoneToTheLeft
            BoardSide.RIGHT -> ::addStoneToTheRight
        }

        addStoneFunction(newStone)

        getHandForPlayer(player) -= newStone
    }

    fun copy(player: Player): GameInterface {
        val hand1 = handPlayer1.toMutableList()
        val hand2 = handPlayer2.toMutableList()
        val unused = unusedDominoes.toMutableList()
        if(!isTransparent) when (player) {
            Player.PLAYER1 -> hand2 += unused
            Player.PLAYER2 -> hand1 += unused
        }
        return object : GameInterface() {
            override val unusedDominoes: MutableCollection<DominoStone> = mutableListOf()

            override val board: ArrayDeque<DominoStone> = ArrayDeque(this@GameInterface.board)

            override val handPlayer1: MutableCollection<DominoStone> = hand1

            override val handPlayer2: MutableCollection<DominoStone> = hand2
        }
    }

    private fun addStoneToTheLeft(newStone: DominoStone) {
        board.addFirst(
            when (firstNumber) {
                null, newStone.right -> newStone
                newStone.left -> newStone.reversed()
                else -> throw IllegalArgumentException()
            }
        )
    }

    private fun addStoneToTheRight(newStone: DominoStone) {
        board.addLast(
            when (lastNumber) {
                null, newStone.left -> newStone
                newStone.right -> newStone.reversed()
                else -> throw IllegalArgumentException()
            }
        )
    }
}
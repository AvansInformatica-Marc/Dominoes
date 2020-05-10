package nl.marc.dominoes.game

object DominoUtils {
    fun createDominoSet(): Collection<DominoStone> = List(7 + 6 + 5 + 4 + 3 + 2 + 1) {
        when {
            it < 7 -> DominoStone(0, it)
            it < 7 + 6 -> DominoStone(1, it - 6)
            it < 7 + 6 + 5 -> DominoStone(2, it - 6 - 5)
            it < 7 + 6 + 5 + 4 -> DominoStone(3, it - 6 - 5 - 4)
            it < 7 + 6 + 5 + 4 + 3 -> DominoStone(4, it - 6 - 5 - 4 - 3)
            it < 7 + 6 + 5 + 4 + 3 + 2 -> DominoStone(
                5,
                it - 6 - 5 - 4 - 3 - 2
            )
            else -> DominoStone(6, 6)
        }
    }
}
package nl.marc.dominoes.game

data class DominoStone(val left: Int, val right: Int) {
    override fun toString() = "[$left|$right]"

    fun reversed() = DominoStone(right, left)

    companion object {
        fun fromText(text: String): DominoStone? {
            val (left, right) = """\[([0-6])\|([0-6])]""".toRegex().find(text)?.destructured ?: return null
            return DominoStone(left.toInt(), right.toInt())
        }
    }
}

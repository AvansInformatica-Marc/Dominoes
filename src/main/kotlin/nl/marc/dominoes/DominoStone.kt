package nl.marc.dominoes

data class DominoStone(val left: Int, val right: Int) {
    override fun toString() = "[ $left | $right ]"
}

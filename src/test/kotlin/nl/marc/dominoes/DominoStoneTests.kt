package nl.marc.dominoes

import nl.marc.dominoes.game.DominoStone
import kotlin.test.Test
import kotlin.test.assertEquals

class DominoStoneTests {
    @Test
    fun testTextParser() {
        // Arrange
        val text = "[2|4]"
        val expectedResult = DominoStone(2, 4)

        // Act
        val actualResult = DominoStone.fromText(text)

        // Assert
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testTextParserReversed() {
        // Arrange
        val text = "[4|2]"
        val expectedResult = DominoStone(2, 4).reversed()

        // Act
        val actualResult = DominoStone.fromText(text)

        // Assert
        assertEquals(expectedResult, actualResult)
    }
}
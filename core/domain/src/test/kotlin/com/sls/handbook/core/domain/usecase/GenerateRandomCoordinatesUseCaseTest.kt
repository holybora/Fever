package com.sls.handbook.core.domain.usecase

import org.junit.Assert.assertTrue
import org.junit.Test

class GenerateRandomCoordinatesUseCaseTest {

    private val useCase = GenerateRandomCoordinatesUseCase()

    @Test
    fun `latitude is within valid range`() {
        repeat(100) {
            val coordinates = useCase()
            assertTrue(
                "Latitude ${coordinates.latitude} out of range",
                coordinates.latitude >= -90.0 && coordinates.latitude < 90.0,
            )
        }
    }

    @Test
    fun `longitude is within valid range`() {
        repeat(100) {
            val coordinates = useCase()
            assertTrue(
                "Longitude ${coordinates.longitude} out of range",
                coordinates.longitude >= -180.0 && coordinates.longitude < 180.0,
            )
        }
    }

    @Test
    fun `successive calls produce different coordinates`() {
        val first = useCase()
        val second = useCase()
        assertTrue(
            "Expected different coordinates",
            first.latitude != second.latitude || first.longitude != second.longitude,
        )
    }
}

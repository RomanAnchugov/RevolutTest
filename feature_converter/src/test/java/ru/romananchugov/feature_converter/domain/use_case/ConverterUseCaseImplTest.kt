package ru.romananchugov.feature_converter.domain.use_case

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import ru.romananchugov.feature_converter.data.model.ConverterDataModel
import ru.romananchugov.feature_converter.domain.respository.ConverterRepository

class TestRepo : ConverterRepository {
    override suspend fun getConverterList(base: String): ConverterDataModel {
        return ConverterDataModel(
            converterList = emptyList()
        )
    }
}

@RunWith(MockitoJUnitRunner::class)
class ConverterUseCaseImplTest {

    @Mock
    private lateinit var repo: ConverterRepository

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun testFun() = runBlocking {
        val useCaseToTest = ConverterUseCaseImpl(TestRepo())
        assertEquals(useCaseToTest.testFun(), 3)
    }
}
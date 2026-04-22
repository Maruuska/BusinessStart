package com.example.ppmob.data

import com.example.ppmob.data.dto.BankDto
import com.example.ppmob.data.mapper.BankMapper
import com.example.ppmob.data.remote.ApiInterface
import com.example.ppmob.data.repository.BankRepositoryImpl
import com.example.ppmob.domain.model.Rezult
import com.example.ppmob.utils.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class BankRepositoryImplTest {

    // Правило для корутин
    @get:Rule
    val coroutineRule = TestCoroutineRule()

    // Создаем мок ApiInterface с помощью MockK
    @MockK
    private lateinit var mockApiInterface: ApiInterface

    private lateinit var repository: BankRepositoryImpl

    @Before
    fun setUp() {
        // Инициализация MockK
        MockKAnnotations.init(this, relaxUnitFun = true)

        // Создаем экземпляр репозитория с моком
        repository = BankRepositoryImpl(mockApiInterface)
    }

    @After
    fun tearDown() {
        // Проверяем, что все моки были вызваны как ожидалось
        confirmVerified(mockApiInterface)
        // Сбрасываем моки
        clearAllMocks()
    }

    // ============= ТЕСТЫ =============

    @Test
    fun `getBanks() - при успешном ответе возвращает Rezult Success со списком банков`() = runTest {
        // Arrange (Подготовка)
        val bankDtos = listOf(
            BankDto(1, "Bank A", "BANKAUS33", "Address A","comment","image"),
            BankDto(2, "Bank B", "BANKBUS44", "Address B","comment","image")
        )
        val expectedBanks = BankMapper.toDomainList(bankDtos)

        // Настраиваем поведение мока
        coEvery { mockApiInterface.getBanks() } returns bankDtos

        // Act (Действие)
        val result = repository.getBanks()

        // Assert (Проверка)
        assert(result is Rezult.Success)
        val successResult = result as Rezult.Success
        assert(successResult.data == expectedBanks)
        assert(successResult.data.size == 2)
        assert(successResult.data[0].name == "Bank A")

        // Проверяем, что метод был вызван ровно 1 раз
        coVerify(exactly = 1) { mockApiInterface.getBanks() }
    }

    @Test
    fun `getBanks() - при пустом ответе возвращает Rezult Success с пустым списком`() = runTest {
        // Arrange
        val emptyList = emptyList<BankDto>()
        coEvery { mockApiInterface.getBanks() } returns emptyList

        // Act
        val result = repository.getBanks()

        // Assert
        assert(result is Rezult.Success)
        val successResult = result as Rezult.Success
        assert(successResult.data.isEmpty())

        coVerify(exactly = 1) { mockApiInterface.getBanks() }
    }

    @Test
    fun `getBanks() - при исключении IOException возвращает Rezult Failure`() = runTest {
        // Arrange
        val ioException = IOException("Network error")
        coEvery { mockApiInterface.getBanks() } throws ioException

        // Act
        val result = repository.getBanks()

        // Assert
        assert(result is Rezult.Failure)
        val failureResult = result as Rezult.Failure
        assert(failureResult.exception == ioException)
        assert(failureResult.exception.message == "Network error")

        coVerify(exactly = 1) { mockApiInterface.getBanks() }
    }

    @Test
    fun `getBanks() - при любом другом исключении возвращает Rezult Failure`() = runTest {
        // Arrange
        val runtimeException = RuntimeException("Unexpected error")
        coEvery { mockApiInterface.getBanks() } throws runtimeException

        // Act
        val result = repository.getBanks()

        // Assert
        assert(result is Rezult.Failure)
        val failureResult = result as Rezult.Failure
        assert(failureResult.exception == runtimeException)

        coVerify(exactly = 1) { mockApiInterface.getBanks() }
    }

    @Test
    fun `getBanks() - проверка маппинга DTO в Domain модель`() = runTest {
        // Arrange
        val bankDtos = listOf(
            BankDto(100, "Mapped Bank", "MPBKUS33", "1 день","ddfd","dfgf")
        )
        coEvery { mockApiInterface.getBanks() } returns bankDtos

        // Act
        val result = repository.getBanks()

        // Assert
        assert(result is Rezult.Success)
        val banks = (result as Rezult.Success).data

        assert(banks[0].id == 100)
        assert(banks[0].name == "Mapped Bank")
        assert(banks[0].specialization == "MPBKUS33")
        assert(banks[0].time == "1 день")
        assert(banks[0].comment == "ddfd")
        assert(banks[0].image == "dfgf")
    }

    @Test
    fun `getBanks() - проверка что метод suspend и обрабатывает большие данные`() = runTest {
        // Arrange
        val largeBankList = (1..100).map { i ->
            BankDto(
                id = i,
                name = "Bank $i",
                specialization = "SWIFT$i",
                time = "Address $i",
                comment = "com $i",
                image = "img $i"
            )
        }
        coEvery { mockApiInterface.getBanks() } returns largeBankList

        // Act
        val result = repository.getBanks()

        // Assert
        assert(result is Rezult.Success)
        val successResult = result as Rezult.Success
        assert(successResult.data.size == 100)

        coVerify(exactly = 1) { mockApiInterface.getBanks() }
    }

    @Test
    fun `getBanks() - проверка что метод не вызывает повторно при ошибке`() = runTest {
        // Arrange
        var callCount = 0
        coEvery { mockApiInterface.getBanks() } answers {
            callCount++
            throw IOException("Error on call #$callCount")
        }

        // Act
        val result1 = repository.getBanks()
        val result2 = repository.getBanks()

        // Assert
        assert(result1 is Rezult.Failure)
        assert(result2 is Rezult.Failure)
        assert(callCount == 2) // Каждый вызов вызывает метод

        coVerify(exactly = 2) { mockApiInterface.getBanks() }
    }
}
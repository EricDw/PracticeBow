package com.publicmethod.practicebow.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.publicmethod.kotlintestingutils.assertTrueWithMessage
import com.publicmethod.practicebow.LeftItemRepo
import com.publicmethod.practicebow.RightItemRepo
import com.publicmethod.practicebow.threading.MockContextProvider
import com.publicmethod.practicebow.threading.MockThreader
import com.publicmethod.practicebow.ui.main.algebras.MainCommand
import com.publicmethod.practicebow.ui.main.algebras.MainCommand.GetItemsCommand
import com.publicmethod.practicebow.ui.main.algebras.MainState
import com.publicmethod.practicebow.ui.main.algebras.Scopes
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @Rule()
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(contextProvider = MockContextProvider())
    }

    @Test
    fun given_initializationCommand_return_initializationState() {
        // Arrange
        val input = MainCommand.InitializeCommand(MockThreader())
        val expectedOutput = true

        // Act
        viewModel.issueCommand(input)
        val state = viewModel.stateLiveData.value!!
        val actualOutput = state is MainState.InitializationState

        // Assert
        assertTrueWithMessage(input = input,
                expectedOutput = expectedOutput,
                actualOutput = actualOutput)
    }

    @Test
    fun given_GetItemCommand_of_right_return_GetItemState() {
        // Arrange
        val input = MainCommand.GetItemCommand(Scopes.GetItemScope(
                "ID",
                RightItemRepo), MockThreader())

        val expectedOutput = true

        // Act
        viewModel.issueCommand(input)
        val state = viewModel.stateLiveData.value!!
        val actualOutput = state is MainState.ShowItemState

        // Assert
        assertTrueWithMessage(input = input,
                expectedOutput = expectedOutput,
                actualOutput = actualOutput)
    }

    @Test
    fun given_GetItemCommand_of_left_return_NoItemErrorState() {
        // Arrange
        val input = MainCommand.GetItemCommand(Scopes.GetItemScope(
                "ID",
                LeftItemRepo), MockThreader())

        val expectedOutput = true

        // Act
        viewModel.issueCommand(input)
        val state = viewModel.stateLiveData.value!!
        val actualOutput = state is MainState.NoItemsErrorState

        // Assert
        assertTrueWithMessage(input = input,
                expectedOutput = expectedOutput,
                actualOutput = actualOutput)
    }

    @Test
    fun given_GetItemsCommand_of_right_return_GetItemsState() {
        // Arrange
        val input = GetItemsCommand(
                Scopes.GetItemsScope(RightItemRepo),
                MockThreader())

        val expectedOutput = true

        // Act
        viewModel.issueCommand(input)
        val state = viewModel.stateLiveData.value!!
        val actualOutput = state is MainState.ShowItemsState

        // Assert
        assertTrueWithMessage(input = input,
                expectedOutput = expectedOutput,
                actualOutput = actualOutput)
    }

    @Test
    fun given_GetItemsCommand_of_left_return_NoItemErrorState() {
        // Arrange
        val input = GetItemsCommand(
                Scopes.GetItemsScope(LeftItemRepo),
                MockThreader())

        val expectedOutput = true

        // Act
        viewModel.issueCommand(input)
        val state = viewModel.stateLiveData.value!!
        val actualOutput = state is MainState.NoItemsErrorState

        // Assert
        assertTrueWithMessage(input = input,
                expectedOutput = expectedOutput,
                actualOutput = actualOutput)
    }
}
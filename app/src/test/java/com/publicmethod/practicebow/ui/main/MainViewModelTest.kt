package com.publicmethod.practicebow.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Option
import arrow.core.Some
import com.publicmethod.kotlintestingutils.assertTrueWithMessage
import com.publicmethod.practicebow.LeftItemRepo
import com.publicmethod.practicebow.MockThreader
import com.publicmethod.practicebow.RightItemRepo
import com.publicmethod.practicebow.ui.main.MainCommand.GetItemsCommand
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @Rule()
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun given_initializationCommand_return_initializationState() {
        // Arrange
        val viewModel = MainViewModel()
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
        val viewModel = MainViewModel()
        val input = MainCommand.GetItemCommand(GetItemScope(
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
        val viewModel = MainViewModel()
        val input = MainCommand.GetItemCommand(GetItemScope(
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
        val viewModel = MainViewModel()
        val input = GetItemsCommand(
                GetItemsScope(RightItemRepo),
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
        val viewModel = MainViewModel()
        val input = GetItemsCommand(
                GetItemsScope(LeftItemRepo),
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
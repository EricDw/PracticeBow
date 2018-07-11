package com.publicmethod.practicebow.ui.main

import android.util.Log
import com.publicmethod.practicebow.MVC.Interpreter
import com.publicmethod.practicebow.MVC.Processor
import com.publicmethod.practicebow.MVC.Reducer
import com.publicmethod.practicebow.MVC.Renderer
import com.publicmethod.practicebow.ModelViewCommand

object MainMVCM : ModelViewCommand<
        MainCommand,
        MainAction,
        MainResult,
        MainState>,
        Interpreter<MainCommand, MainAction> by MainInterpreter,
        Processor<MainAction, MainResult> by MainProcessor,
        Reducer<MainResult, MainState> by MainReducer,
        Renderer<MainState> by MainViewModel {

    override fun issueCommand(command: MainCommand) {
        Log.d("MAIN_MVCM", command.toString())
        super.issueCommand(command)
    }
}

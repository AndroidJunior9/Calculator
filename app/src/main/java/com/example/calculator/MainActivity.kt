package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.DarkGray
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val viewModel = CalculatorViewModel()
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = DarkGray,
                        darkIcons = false
                    )

                }

                CalculatorUI(
                    viewModel = viewModel,
                )
            }
        }
    }
}


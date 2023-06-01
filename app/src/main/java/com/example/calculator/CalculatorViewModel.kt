package com.example.calculator

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class CalculatorViewModel: ViewModel() {
    val expression = mutableStateOf("")
    fun clear() {
        expression.value = ""
    }

    fun append(char: String) {
        Log.d("append", "$char Expression Value:${expression.value}")
        if (char in "0123456789") {
            expression.value += char
        }else if(char in "+-×÷") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()

                // if last char is an operator, replace it with the new operator
                if (lastChar in "+-×÷") {
                    expression.value = expression.value.dropLast(1)
                }
            }
            expression.value += char
        }else if(char == ".") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()
                if (lastChar!='.') {
                    // if last char is an operator, and the current char is a dot, add a zero before the dot
                    if (lastChar in "+-×÷") {
                        expression.value += "0"
                    }
                    expression.value += char
                }
            }

        }else if(char =="("){
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()
                // if last char is not a operator, add a multiplication operator before the parenthesis
                if (lastChar !in "+-×÷") {
                    expression.value += "×"
                }
            }
            expression.value += char
        }else if(char ==")"){
            expression.value += char
        }
    }

    fun delete() {
        if (expression.value.isNotEmpty()) {
            expression.value = expression.value.dropLast(1)
        }
    }

    fun evaluate() {
        expression.value = try {
            val result = evaluate(expression.value)
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }
}
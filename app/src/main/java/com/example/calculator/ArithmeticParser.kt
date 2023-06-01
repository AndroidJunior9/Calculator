package com.example.calculator

//Class for Tokenizer
class Tokenizer(val input: String) {
    // for storing the position of the current character
    var pos = 0

    // for storing the current token
    var token: String? = null

    //Advancing to the next token in the input(tokens are like integers,operators,parenthesis etc)
    fun nextToken() {

        while (pos < input.length && input[pos].isWhitespace()) {
            pos++
        }

        if (pos == input.length) {
            token = null
            return
        }

        // if the current character is a digit or a decimal point, then we parse a number
        if (input[pos].isDigit() || input[pos] == '.') {

            val sb = StringBuilder()
            while (pos < input.length && (input[pos].isDigit() || input[pos] == '.')) {
                sb.append(input[pos])
                pos++
            }
            token = sb.toString()
            return
        }

        if ("+-×÷()".contains(input[pos])) {

            token = input[pos].toString()
            pos++
            return
        }

        throw IllegalArgumentException("Invalid character: ${input[pos]}")
    }
}


fun evaluate(expression: String): Double {

    val tokenizer = Tokenizer(expression)

    tokenizer.nextToken()

    return parseExpression(tokenizer)
}

// function to parse the expression and carry out addition and subtraction operations
fun parseExpression(tokenizer: Tokenizer): Double {

    var result = parseTerm(tokenizer)

    while (tokenizer.token in listOf("+", "-")) {

        val op = tokenizer.token!!

        tokenizer.nextToken()

        val term = parseTerm(tokenizer)

        result = when (op) {
            "+" -> result + term
            "-" -> result - term
            else -> throw IllegalStateException("Invalid operator: $op")
        }
    }

    return result
}

// function to parse the term and carry out multiplication and division operations
fun parseTerm(tokenizer: Tokenizer): Double {

    var result = parseFactor(tokenizer)
    while (tokenizer.token in listOf("×", "÷")) {

        val op = tokenizer.token!!
        tokenizer.nextToken()
        val factor = parseFactor(tokenizer)

        result = when (op) {
            "×" -> result * factor
            "÷" -> result / factor
            else -> throw IllegalStateException("Invalid operator: $op")
        }
    }
    return result

}

// To get the value of the factor and carry out unary operations and also to handle parenthesis
fun parseFactor(tokenizer: Tokenizer): Double {

    if (tokenizer.token == null) {
        throw IllegalArgumentException("Missing factor")
    }
    //Checking if the token is a number
    if (tokenizer.token!!.toDoubleOrNull() != null) {

        val value = tokenizer.token!!.toDouble()

        tokenizer.nextToken()

        return value
    }

    //Checking if the token is a unary operator
    if (tokenizer.token in listOf("+", "-")) {

        val op = tokenizer.token!!

        tokenizer.nextToken()

        val factor = parseFactor(tokenizer)

        return when (op) {
            "+" -> +factor
            "-" -> -factor
            else -> throw IllegalStateException("Invalid operator: $op")
        }
    }

    //Handling parenthesis
    if (tokenizer.token == "(" && tokenizer.input.indexOf(")", tokenizer.pos) != -1) { // Added this condition

        tokenizer.nextToken()

        // Parsing the expression inside the parenthesis like (2-6+4) etc
        val value = parseExpression(tokenizer)

        if (tokenizer.token == ")") {

            tokenizer.nextToken()

            return value
        } else {
            throw IllegalArgumentException("Missing closing parenthesis")
        }
    }
    else {
        throw IllegalArgumentException("Invalid factor: ${tokenizer.token}")
    }
}



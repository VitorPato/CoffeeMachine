package machine

import java.beans.beancontext.BeanContext

enum class CoffeeTypes (val value: Int){
    ESPRESSO(1),
    LATTE(2),
    CAPPUCCINO(3)
}


class CoffeeMachine(
             var money: Int = 0,
             var water: Int = 0,
             var milk: Int = 0,
             var beans: Int = 0,
             var disposableCups: Int = 0) {

    fun printStatus() {
        println()
        println("The coffee machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$beans g of coffee beans")
        println("$disposableCups disposable cups")
        println("$$money of money")
    }

    fun fill() {
        water += getUserInput("Write how many ml of water do you want to add: ")
        milk += getUserInput("Write how many ml of milk do you want to add: ")
        beans += getUserInput("Write how many grams of coffee beans do you want to add: ")
        disposableCups += getUserInput("Write how many disposable cups of coffee do you want to add: ")
    }

    fun take() {
        println("I gave you $$money")
        money = 0
    }

    fun buy() {
        println()
        print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ")
        val userInput = readln()
        if (userInput ==  "back") {
            return
        }

        val coffeeType = userInput.toInt()

        if (coffeeType !in 1..3) {
            println("Coffee type unavailable")
            println()
            printStatus()
            return
        }

        var waterPerCup = 0
        var milkPerCup = 0
        var beansPerCup = 0
        var costPerCup = 0

        if (coffeeType == CoffeeTypes.ESPRESSO.value) {
            waterPerCup = 250
            milkPerCup = 0
            beansPerCup = 16 // 16g
            costPerCup = 4
        } else if (coffeeType == CoffeeTypes.LATTE.value) {
            waterPerCup = 350
            milkPerCup = 75
            beansPerCup = 20
            costPerCup = 7
        } else if (coffeeType == CoffeeTypes.CAPPUCCINO.value) {
            waterPerCup = 200
            milkPerCup = 100
            beansPerCup = 12
            costPerCup = 6
        }

        if (water - waterPerCup < 0) {
            println("Sorry, not enough water!")
            return
        } else if (milk - milkPerCup < 0) {
            println("Sorry, not enough milk!")
            return
        } else if (beans - beansPerCup < 0) {
            println("Sorry, not enough coffe beans!")
            return
        } else if (disposableCups - 1 < 0) {
            println("Sorry, not enough cups!")
            return
        } else {
            println("I have enough resources, making you a coffee!")
        }

        water -= waterPerCup
        milk -= milkPerCup
        beans -= beansPerCup
        disposableCups -= 1
        money += costPerCup
    }

    fun nextUserCommand() {
        val allowedInputs = listOf("buy", "fill", "take", "exit", "remaining")
        var userInput = ""
        do {
            println()
            print("Write action (buy, fill, take, remaining, exit): ")
            userInput = readln()
        } while (userInput !in allowedInputs)

        when (userInput) {
            "buy" -> buy()
            "take" -> take()
            "fill" -> fill()
            "exit" -> System.exit(0)
            "remaining" -> printStatus()
        }
    }

}

fun main() {
    coffeeMachine()
}

fun coffeeMachine() {
    // Default parameters
    val coffeMachine = CoffeeMachine(money = 550, water = 400, milk = 540, beans = 120, disposableCups = 9)
    while (true) {
        coffeMachine.nextUserCommand()
    }
}

fun getUserInput(msg: String): Int {
    var userInput = 0
    do {
        print(msg)
        userInput = readln().toInt()
    } while (userInput < 0)

    return userInput
}

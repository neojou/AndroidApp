interface Soundable {
    fun sound()
}

abstract class Animal(open val name: String,
                      weight_param: Float) : Soundable {
    open var weight = weight_param
        set(value) {
            if (value >= 0.001f) field = value
        }
}

open class Dog(name: String,
          weight_param: Float) : Animal(name, weight_param){

    override fun sound() {
        println("$name Wan!")
    }

    open fun catch_ball() {
        println("$name catch the ball and run back!")
    }
}

open class Cat(name: String, weight_param: Float) : Animal(name, weight_param) {
    override fun sound() {
        println("$name Miaow!")
    }
}

class ChiHuaHua(name: String, weight_param: Float) : Dog(name, weight_param)
{
    override fun sound() {
        println("$name Wu!")
    }
}

class Husky(name: String, weight_param: Float) : Dog(name, weight_param)
{
    override open fun sound() {
        println("$name Woof!")
    }
}

fun do_catch_ball(pet: Animal) {
    if (pet is Dog) {
        val dog = pet as Dog
        dog.catch_ball()
    } else {
        val name = pet.name
        println("$name cannot do catch ball")
    }
}

fun main() {
    val pet1 : Animal = ChiHuaHua("Snow", 2.5f)
    val pet2 : Animal = Husky("Dragon", 7.5f)
    val pet3 : Animal = Cat("DaMaGo", 6.3f)
    val my_pets = arrayOf(pet1, pet2, pet3)
    for (pet in my_pets) {
        pet.sound()
        do_catch_ball(pet)
    }
}
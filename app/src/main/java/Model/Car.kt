package Model

class Car(var id: Int, var name: String, var price: String) {

    constructor() : this(id = 0, name = "", price = "")

    constructor(name: String, price: String) : this(id = 0, name, price)

}
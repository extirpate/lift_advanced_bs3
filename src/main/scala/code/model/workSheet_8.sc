case class Person2(first: String, last: String, age: Int, middle: String = "")

class Person(private var _first: String, private var _last: String) {
  var age = 0
  def first = { //getter
    println(s"First name is ${this._first}")
   _first
  }
  def first_= (value:String): Unit = { //setter
    println(s"First name was set to $value")
    _first = value
  }
  def last = {//getter
    println(s"Last name is ${this._last}")
    _last
  }
  def last_= (value:String): Unit = {//setter
    println(
      s"""Last name was set to $value
         |Full name is $first $value
       """.stripMargin)
    _last = value
  }
}

trait Person3 {
  def first: String
  def last: String
}

case class Student(first: String, last: String) extends Person3
case class Teacher (first: String, last: String) extends Person3
case class SuperHero(first: String, last: String, superPower: SuperPower) extends Person3
case class SuperPower(ability: String, powerLevel:Int)

object TJug {
  println("Welcome to the Toronto JUG")
  val p = new Person("John", "Smith")
  p.first
  p.last
  p.first = "Christian"
  p.first = ("Christian")
  p.first_=("Christian")
  p.last = "Sadilek"
  p.first
  p.last
  val p2 = Person2("John", "Smith", 60)

  def printSuperPower(p:Person3) = {
    p match {
      case SuperHero(_, _, supperPower) => println(p.first + " has " + supperPower.ability)
      case _ => println(p.getClass.getName + "s have no supper powers" )
    }
  }
}

TJug.printSuperPower(SuperHero("John", "Smith", SuperPower("flying ability", 10)))
//find anagrams
object TJugCol {
  val words = List("kitchen", "thicken", "the", "ababab", "testing", "bababa")
  val intermediateResult = words.filter(_.length > 4).map(_.sorted).distinct
  val result = intermediateResult.map(iw => {
    words.filter(_.sorted == iw)
  })

  val result2 = words.filter(_.length > 4).groupBy(_.sorted).values
  println(result)
  println(result2)
}
TJugCol





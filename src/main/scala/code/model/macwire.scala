package code.model

import com.softwaremill.macwire.Macwire
object SF1 extends App with Macwire {
  case class Field()
  case class Digger()
  case class PotatoFarm(field:Field, digger: Digger)
  case class CowPasture()
  case class Meatery(cowPasture: CowPasture)
  case class Restaurant(potatoFarm: PotatoFarm, meatery: Meatery){
    def orderSteakWithPotatoes(): Unit = {
      println(s"Welcome to $this. Here's your order")
    }
  }
  val field = new Field()
  val digger = new Digger()
  val potatoFarm = new PotatoFarm(field, digger)
  val cowPasture = new CowPasture()
  val meatery = new Meatery(cowPasture)
  val restaurant = new Restaurant(potatoFarm, meatery)

  restaurant.orderSteakWithPotatoes()
}

object SF2 extends App with Macwire {
  case class Field()
  case class Digger()
  case class PotatoFarm(field:Field, digger: Digger)
  case class CowPasture()
  case class Meatery(cowPasture: CowPasture)
  case class Restaurant(potatoFarm: PotatoFarm, meatery: Meatery){
    def orderSteakWithPotatoes(): Unit = {
      println(s"Welcome to $this. Here's your order")
    }
  }
  lazy val field = new Field()
  lazy val potatoFarm = new PotatoFarm(field, digger)
  lazy val digger = new Digger()


  lazy val cowPasture = new CowPasture()
  lazy val meatery = new Meatery(cowPasture)

  lazy val restaurant = new Restaurant(potatoFarm, meatery)

  restaurant.orderSteakWithPotatoes()
}

object SF3 extends App with Macwire {
  case class Field()
  case class Digger()
  case class PotatoFarm(field:Field, digger: Digger)

  case class CowPasture()
  case class Meatery(cowPasture: CowPasture)

  case class Restaurant(potatoFarm: PotatoFarm, meatery: Meatery){
    def orderSteakWithPotatoes(): Unit = {
      println(s"Welcome to $this. Here's your order")
    }
  }

  lazy val field = wire[Field]
  lazy val potatoFarm = wire[PotatoFarm]
  lazy val digger = wire[Digger]


  lazy val cowPasture = wire[CowPasture]
  lazy val meatery = wire[Meatery]

  lazy val restaurant = wire[Restaurant]

  restaurant.orderSteakWithPotatoes()
}


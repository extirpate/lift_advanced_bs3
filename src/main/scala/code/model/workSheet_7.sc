val x = Map("a" -> 1, "b" -> 2)

x.values

case class Vehicle(vType: String, action: String )

val t1: (Int, String, Vehicle) = (2, "a", Vehicle("boat", "float"))

val t2: (Int, String) => String = (x: Int, y: String) => x + y

t2(11,"2")

val t3 = new Function1[Int,Int]()  {
  def apply(x: Int): Int = x + 1
}

val t5x = 2

val t5 = (x: Int, y: () => Int) =>  y.apply() + x

val t4 = t3(1)

val t6 = t5(2, () => t5x + 1)
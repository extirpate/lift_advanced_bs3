class Class1 {
  final protected def proMethod = "protected"
  private def priMethod = "private"
 //protected[Class1] def pMethod = "protected"
  //final override def toString = priMethod
}
class Class2{
  val a = new Class1
  override def toString = "test"
}
final class Class3 extends Class1{
  //override def proMethod = "class3 protected"
  val a = proMethod
  //val b = priMethod
  override def toString = a
}


val t1 = new Class2
val t2 = new Class3

for(a <- 1 to 10;
   d = new Class3)
  yield println(d.a)
/*object Timer{
  def oncePerSecond(callBack: () => Unit) = {
    while (true) {
      callBack()
      Thread.sleep(100)
    }
  }
  def timeFlies()= {
    println("time flies like an arrow...")
  }
  //def main(args:Array[String]) = {
   val c = oncePerSecond(timeFlies)
  //}

}

val t3 = Timer.c*/

//partially applied functions
def pApp(x: Int, y: Int) = x + y
val t4 = pApp(10, _: Int)
val t5 = t4(10)

//function call-by-name
def time() = {
  println("Getting time in nano seconds")
  System.nanoTime
}
def delayed( t:  => Long ) = {
  println("In delayed method")
  println("Param: " + t)
  t
}
val t6 = delayed(time())

//higher order function
def layout[A](x: A): String = "[" + x.toString + "]"
def hof(f: Double => String, v: Double) = f(v)
val t7 = hof(layout, 10.12)

//Anonymous functions
var inc = (x:Int) => x+1
var t8 = inc(7) - 1
var mul = (x:Int, y:Int) => x*y
var userDir = () => { System.getProperty("user.dir") }
println(userDir())

//currying functions
def strcat(s1: String)(s2: String) = s1 + s2
//def strcat(s1: String) = (s2: String) => s1 + s2
val t9 = strcat("foo")("bar")

//closure
var factor = 3
val multiplier = (i:Int) => i * factor
val t10 = multiplier(10)
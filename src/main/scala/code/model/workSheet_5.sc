val a = List(1,2)

val b = (a :\ 5)((x,y) => x + y)

val c = (a :\ 5)(_ + _)

val l1 = List(1)
val l2 = List(2)
val l3 = (l1 :\ 1)(_+_)

def append[T](xs:List[T], ys:List[T]): List[T] = {
  xs match {
    case List() => xs
    case x :: xs1 => x :: append(xs1,ys)
  }
}

val t1 = append(List(1), List(4,5))


object Example {
  var x = 0

  def foo(y: Int): Int =
    if(x < 0)
      y - x
    else if(x == 0)
      y / 2
    else
      y + x
}

Example.x = 0

val t2 = Example.foo(1)

val joe = <employee
  name="Joe"
  rank="code monkey"
  serial="123"/>

joe.attribute("name")
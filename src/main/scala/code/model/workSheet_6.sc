val n = 45
s"We have $n apples"
val a = Array(11, 9, 6)

def sumOfSquares(x: Int, y: () => Int): Int = {
  val x2 = x * x
  val y2 = y()
  x2 + y2
}

val b = sumOfSquares(10, () => 10 * 10)

object EMail {

  // The injection method (optional)
  def apply(user: String, domain: String) = user +"@"+ domain

  // The extraction method (mandatory)
  def unapply(str: String): Option[(String, String)] = {
    val parts = str split "@"
    if (parts.length == 2) Some(parts(0), parts(1)) else None
  }
}

val e = EMail("dhanuka","hotmail.com")

val eu = EMail.unapply(e)

trait MyService {
  def foo = 42
}
object MyService extends MyService

class MyClient {
  val service: MyService = MyService
}

/*
https://hhimanshu.github.io/2015/07/10/Partially-Applied-Functions-in-Scala.html
http://stackoverflow.com/questions/9737352/what-is-the-apply-function-in-scala/9738862#9738862
 */

/*
Mathematicians have their own little funny ways, so instead of saying
 "then we call function f passing it x as a parameter" as we programmers
 would say, they talk about "applying function f to its argument x".
 */

/*
In mathematics and computer science, Apply is a function that applies functions to arguments.
Wikipedia
 */

/*
apply serves the purpose of closing the gap between
Object-Oriented and Functional paradigms in Scala.
Every function in Scala can be represented as an object.
Every function also has an OO type: for instance, a function
that takes an Int parameter and returns an Int will
have OO type of Function1[Int,Int].
 */

/*
 // define a function in scala
 (x:Int) => x + 1

 // assign an object representing the function to a variable
 val f = (x:Int) => x + 1
 */

/*
Since everything is an object in Scala f can now be
treated as a reference to Function1[Int,Int] object.
For example, we can call toString method inherited from Any,
that would have been impossible for a pure function, because
functions don't have methods:

f.toString
 */

/*
Or we could define another  Function1[Int,Int] object by
calling compose method on f and chaining two different functions together:

 val f2 = f.compose((x:Int) => x - 1)

Now if we want to actually execute the function, or as
mathematician say "apply a function to its arguments" we
would call the apply method on the Function1[Int,Int] object:

 f2.apply(2)
 */

/*
Writing f.apply(args) every time you want to execute a function represented
as an object is the Object-Oriented way, but would add a lot of clutter to
the code without adding much additional information and it would be nice
to be able to use more standard notation, such as f(args). That's
where Scala compiler steps in and whenever we have a reference f
to a function object and write f (args) to apply arguments to the
represented function the compiler silently expands f (args) to
the object method call f.apply (args).
 */

/*
Every function in Scala can be treated as an object and
it works the other way too - every object can be treated
as a function, provided it has the apply method. Such
objects can be used in the function notation:
 */

// we will be able to use this object as a function, as well as an object
object Foo {
  var y = 5
  def apply (x: Int) = x + y
}


Foo (1) // using Foo object in function notation

/*
There are many usage cases when we would want to treat
an object as a function. The most common scenario is
a factory pattern. Instead of adding clutter to the code
using a factory method we can apply object to a set of
arguments to create a new instance of an associated class:
 */

List(1,2,3) // same as List.apply(1,2,3) but less clutter, functional notation

// the way the factory method invocation would have looked
// in other languages with OO notation - needless clutter
//List.instanceOf(1,2,3)

//So apply method is just a handy way of closing the gap between
// functions and objects in Scala.

//******************************************************

/*
It comes from the idea that you often want to apply something to an object.
The more accurate example is the one of factories. When you have a factory,
you want to apply parameter to it to create an object.

Scala guys thought that, as it occurs in many situation,
it could be nice to have a shortcut to call apply. Thus,
a syntactic sugar is that, when you give parameters directly to an object,
it's desugared as if you pass these parameters to the apply function of that
object:
 */

class MyAdder(x: Int) {
  def apply(y: Int) = y + y
}

val x = new MyAdder(2)
val y = x(4) // equivalent to x.apply(4)

/*
It's often use in companion object,
to provide a nice factory method for a class or a trait, here is an example:
 */

trait A {
  val x: Int
  def myComplexStrategy: Int
}

object A {
  def apply(x: Int): A = new MyA(x)

  private class MyA(val x: Int) extends A {
    val myComplexStrategy = 42
  }
}

A (1).myComplexStrategy

/*
From the scala standard library,
you might look at how scala.collection.Seq is implemented:
Seq is a trait, thus new Seq(1, 2) won't compile but thanks to
companion object and apply, you can call Seq(1, 2) and
the implementation is chosen by the companion object.
 */
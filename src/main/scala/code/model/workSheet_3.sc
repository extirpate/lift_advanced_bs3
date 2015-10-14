val someNumbers = List(-11, -10, -5, 0, 5, 10)
someNumbers.foreach((x : Int) => println(x))

def apply[A](f: (A) => String, v: A) = f(v).toString :: v.toString :: Nil

def layout[A](x: A) = "[" + x.toString + "]"

val c =  apply( (x: Int) => "[" + x.toString + "]", 10)
val d = apply(layout, 10)

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

object FileMatcher {
  private def filesHere = new java.io.File(".").listFiles

  private def filesMatching(matcher: String => Boolean) =
    for (file <- filesHere; if matcher(file.getName))
      yield file

  def filesMatching(query: String, matcher: (String, String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName, query))
      yield file
  }

  def filesEnding(query: String) =
    filesMatching(_.endsWith(query))

  def filesContaining(query: String) =
    filesMatching(query => query.contains(query))
    //_.contains(query)

  def filesContaining2(query: String) =
    filesMatching(query, (fileName: String, query: String) => fileName.contains(query))
    //(_, _) => query.contains(query)

  def filesRegex(query: String) =
    filesMatching(_.matches(query))

  override def toString = filesHere.toString

}

val fl = FileMatcher.filesContaining2(".exe")
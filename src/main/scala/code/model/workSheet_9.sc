val data = 1 to 100

def calculation(number: Int) = {
  println("Calculating " + number)
  Thread.sleep(500)
  number * 2
}

val result = data.par.map(calculation)

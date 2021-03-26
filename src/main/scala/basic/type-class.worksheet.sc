trait Show[A]:
  def show(a: A): String


class ShowInt extends Show[Int]:
  def show(a: Int) = s"The number is ${a}!"

def toHtml[A](a: A)(using show: Show[A]): String = "<p>" + show.show(a) + "</p>"
toHtml(42)(using ShowInt())


trait Showable[A]:
  extension (a: A) 
    def show: String

case class Person(firstName: String, lastName: String)
given Showable[Person] with
  extension (p: Person) 
    def show: String = s"${p.firstName} ${p.lastName}"

val p = Person("Zhenglai", "Zhang")
println(p.show)

def showAll[S: Showable](xs: List[S]): Unit = xs.foreach(x => println(x.show))
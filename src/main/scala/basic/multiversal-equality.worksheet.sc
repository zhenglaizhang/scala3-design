// [1] add this import, or this command line flag: -language:strictEquality
import scala.language.strictEquality

case class Dog(name: String) derives CanEqual
case class Cat(name: String) 
given CanEqual[Cat, Cat] = CanEqual.derived
val d = Dog("F")
val c = Cat("M")
d == Dog("F")
// d == c
// This is how multiversal equality catches illegal type comparisons at compile time.

trait Book:
    def author: String
    def title: String
    def year: Int

case class PrintedBook(
    author: String,
    title: String,
    year: Int,
    pages: Int
) extends Book:
 override def equals(that: Any): Boolean = 
  that match
    case a: AudioBook =>
      if this.author == a.author
        && this.title == a.title
      then true else false
    case p: PrintedBook =>
      if this.author == p.author && this.title == p.title
      then true else false
    case _ =>
        false

case class AudioBook(
    author: String,
    title: String,
    year: Int,
    lengthInMinutes: Int
) extends Book:
 override def equals(that: Any): Boolean = 
  that match
    case a: AudioBook =>
      if this.author == a.author
      && this.title == a.title
      && this.year == a.year
      && this.lengthInMinutes == a.lengthInMinutes
          then true else false
    case p: PrintedBook =>
      if this.author == p.author && this.title == p.title
          then true else false
    case _ =>
        false

given CanEqual[PrintedBook, PrintedBook] = CanEqual.derived
given CanEqual[AudioBook, AudioBook] = CanEqual.derived

given CanEqual[PrintedBook, AudioBook] = CanEqual.derived
given CanEqual[AudioBook, PrintedBook] = CanEqual.derived

AudioBook("ab", "t", 2012, 10) == PrintedBook("ab", "t", 2012, 1000)
PrintedBook("ab", "t", 2012, 1000) == AudioBook("ab", "t", 2012, 10)
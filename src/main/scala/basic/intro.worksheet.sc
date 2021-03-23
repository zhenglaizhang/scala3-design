package basic

// imperative
def double(ints: List[Int]): List[Int] = {
  val buffer = new ListBuffer[Int]()
  for (i <- ints) {
    buffer += i * 2
  }
  buffer.toList
}

val oldNumbers = List(1, 2, 3)

val newNumbers = double(oldNumbers)

// declarative
val newNumbers2 = oldNumbers.map(_ * 2)


trait Animal:
  def speak(): Unit 

trait HasTail:
  def wagTail(): Unit 

class Dog extends Animal with HasTail:
  def speak() = println("Woof")
  def wagTail() = println("Wagging")

val d = new Dog()
d.speak()


enum Color:
  case Red, Green, Blue 
// === 
enum Color2:
  case Red extends Color2
  case Green extends Color2
  case Blue extends Color2

enum Color(val rgb: Int):
  private val x = 0.5
  case Red   extends Color(0xFF0000)
  case Green extends Color(0x00FF00)
  case Blue  extends Color(0x0000FF)
  case Mix(mix: Int) extends Color(mix)

  def reduce = rgb * x

println(Color.Red.rgb)

object Color:
  def fromRgb(rgb: Int): Option[Color] = Try(Color.fromOrdinal(rgb)).toOption

enum Option[+A]
  case Some(x: A)
  case None

enum Option[+T]:
  case Some(x: T) extends Option[T]
  case None       extends Option[Nothing]
  
  def isDefined: Boolean = this match 
    case None => false 
    case Some(_) => true

object Option:
  def apply[T >: Null](x: T): Option[T] = if (x == null) None else Some(x)

Option.Some(12)
Option.None

// Enumerations can also be recursive,
enum Nat:
  case Zero
  case Succ(n: Nat)

Succ(Succ(Zero)) // 2

enum List[+A]:
  case Nil
  case Cons(head: A, tail: List[A])


// Generalized Algebraic Datatypes (GADTs)
enum Box[A](contents: A)
  case IntBox(n: Int) extends Box[Int](n)
  case BoolBox(b: Boolean) extends Box[Boolean](b)

// Pattern matching on the particular constructor (IntBox or BoolBox) recovers the type information:
def extract[A](b: Box[A]): A = b match 
  case IntBox(n) => n + 1
  case BoolBox(b) => !b

// Desugaring Enumerations
// Conceptually, enums can be thought of as defining a sealed class together with an companion object.
sealed abstract class Color(val rgb: Int) extends scala.reflect.Enum
object Color:
  case object Red extends Color(0xFF0000) { def ordinal = 0 }
  case object Green extends Color(0x00FF00) { def ordinal = 1 }
  case object Blue extends Color(0x0000FF) { def ordinal = 2 }
  case class Mix(mix: Int) extends Color(mix) { def ordinal = 3 }

  def fromOrdinal(ordinal: Int): Color = ordinal match
    case 0 => Red
    case 1 => Green
    case 2 => Blue
    case _ => throw new NoSuchElementException(ordinal.toString)
// https://dotty.epfl.ch/docs/reference/enums/desugarEnums.html
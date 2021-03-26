object wrapper {
  given Conversion[String, Int] with 
    def apply(s: String): Int = Integer.parseInt(s)
}
given Conversion[String, Int] = Integer.parseInt(_)

import scala.language.implicitConversions
def plus1(i: Int) = i + 1
plus1("1")
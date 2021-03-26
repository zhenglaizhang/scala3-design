// An enumeration is used to define a type consisting of a set of named values.

enum Color:
  case Red, Green, Blue
// This defines a new sealed class, Color, with three values, Color.Red, Color.Green, Color.Blue. The color values are members of Colors companion object.

// - Enums can be parameterized.
// - The values of an enum correspond to unique integers. The integer associated with an enum value is returned by its ordinal method
enum Planet(mass: Double, radius: Double):
   private final val G = 6.67300E-11
   def surfaceGravity = G * mass / (radius * radius)
   def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

   case Mercury extends Planet(3.303e+23, 2.4397e6)
   case Venus   extends Planet(4.869e+24, 6.0518e6)
   case Earth   extends Planet(5.976e+24, 6.37814e6)
   case Mars    extends Planet(6.421e+23, 3.3972e6)
   case Jupiter extends Planet(1.9e+27,   7.1492e7)
   case Saturn  extends Planet(5.688e+26, 6.0268e7)
   case Uranus  extends Planet(8.686e+25, 2.5559e7)
   case Neptune extends Planet(1.024e+26, 2.4746e7)
end Planet

object Planet:
  def main(args: Array[String]): Unit =
     val earthWeight = args(0).toDouble
      val mass = earthWeight / Earth.surfaceGravity
      for p <- values do
         println(s"Your weight on $p is ${p.surfaceWeight(mass)}")
end Planet

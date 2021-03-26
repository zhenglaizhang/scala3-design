case class Circle(x: Double, y: Double, radius: Double)

object CircleHelpers:
  def circumference(c: Circle): Double = c.radius * math.Pi * 2

extension (c: Circle)
  def circumference: Double = c.radius * math.Pi * 2
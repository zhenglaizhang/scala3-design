// Some use cases, such as modeling database access, are more awkward in statically typed languages than in dynamically typed languages. With dynamically typed languages, it’s natural to model a row as a record or object, and to select entries with simple dot notation, e.g. row.columnName
// This requires a large amount of boilerplate, which leads developers to trade the advantages of static typing for simpler schemes where column names are represented as strings and passed to other operators, e.g. row.select("columnName")
// Structural types help in situations where you’d like to support simple dot notation in dynamic contexts without losing the advantages of static typing. They allow developers to use dot notation and configure how fields and methods should be resolved.
class Record(elems: (String, Any)*) extends Selectable:
  private val fields = elems.toMap
  def selectDynamic(name: String): Any = fields(name)

type Person = Record {
  val name: String
  val age: Int
}
// The Person type adds a refinement to its parent type Record that defines name and age fields. 
// We say the refinement is structural since name and age are not defined in the parent type. 
// But they exist nevertheless as members of class Perso

val p = Record(
  "name" -> "Emma",
  "age" -> 12
).asInstanceOf[Person]
println(s"${p.name} is ${p.age} years old")
// The parent type Record in this example is a generic class that can represent arbitrary records in its elems argument. This argument is a sequence of pairs of labels of type String and values of type Any. When you create a Person as a Record you have to assert with a typecast that the record defines the right fields of the right types. Record itself is too weakly typed, so the compiler cannot know this without help from the user. In practice, the connection between a structural type and its underlying generic representation would most likely be done by a database layer, and therefore would not be a concern of the end user.
// Record extends the marker trait scala.Selectable and defines a method selectDynamic, which maps a field name to its value. Selecting a structural type member is done by calling this method. The person.name and person.age selections are translated by the Scala compiler to:
p.selectDynamic("name").asInstanceOf[String]
p.selectDynamic("age").asInstanceOf[Int]

type Book = Record {
  val title: String
  val author: String
  val year: Int
  val rating: Double
}

val book = Record(
  "title" -> "The Cather in the Rye",
  "author" -> "J. D. Salinger",
  "year" -> 1951,
  "rating" -> 4.5
).asInstanceOf[Book]

// todo
// Selectable class
// Selectable class sometimes also defines a method applyDynamic. This can then be used to translate function calls of structural members. So, if a is an instance of Selectable, a structural call like a.f(b, c) translates to:
// a.applyDynamic("f")(b, c)
// todo https://dotty.epfl.ch/docs/reference/new-types/dependent-function-types.html
// DEPENDENT FUNCTION TYPES
// A dependent function type describes function types, where the result type may depend on the function’s parameter values. 

// DEPENDANT METHOD TYPES
// We can read this path-dependent type as: “depending on the concrete type of the argument k, we return a matching value”.
trait Key { type Value }
trait DB {
  def get(k: Key): Option[k.Value] // a dependant method
}

object Name extends Key { type Value = String }
object Age extends Key { type Value = Int }
val db: DB = ???
val n: Option[Name.Value] = db.get(Name)
val a: Option[Age.Value] = db.get(Age)
// The return type depends on the concrete type of the argument passed to get—hence the name dependent type.

// Dependent Function Types
// As seen above, Scala 2 already had support for dependent method types. However, creating values of type DB is quite cumbersome:
// a user of a DB

trait Entry {
  type Key;
  val key: Key
}
def extractKey(e: Entry): e.Key = e.key
val extractor: (e: Entry) => e.Key = extractKey // only fine in Scala 3

def user(db: DB): Unit =
  db.get(Name) ??? db.get(Age)

// creating an instance of the DB and passing it to `user`
user(new DB {
  def get(k: Key): Option[k.Value] = ??? // implementation of DB
})

// The trait DB only has a single abstract method get. Wouldn’t it be nice, if we could use lambda syntax instead?
// this is now possible in Scala 3! We can define DB as a dependent function type:
type DB1 = (k: Key) => Option[k.Value]
user { k => 
  ... // implementation of DB
}

// Numerical Expressions
trait Nums:
  type Num // the type of numbers is left abstract

  def lit(d: Double): Num
  def add(l: Num, r: Num): Num
  def mul(l: Num, r: Num): Num

type Prog = (n: Nums) => n.Num => n.Num
val ex: Prog = nums => x => nums.add(nums.lit(0.8), x)

// todo https://docs.scala-lang.org/scala3/book/types-dependent-function.html
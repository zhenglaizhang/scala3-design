case class Config(baseUrl: String, port: Int)

def renderWidget(items: List[String])(using Config): String = "fakelogic"
def renderWebsite(path: String)(using c: Config): String = 
  "<html>" + renderWidget(List("Cart")) + "</html>"

val config = Config("www.github.com", 80)
renderWebsite("/zhenglaizhang")(using config)

given Config = config
renderWebsite("/goodidea")


object A:
  class TC 
  given tc as TC 
  def f(using TC) = ???

object B:
  import A.* // import all non-given members
  import A.given // import the given interface
  import A.{given, *} // merged

trait Ord[A] 

def max[A](using Ord[A]) = ???

def maximum[A](xs: List[A])(using ord: Ord[A]): A = 
  xs.reduceLeft(max(using ord))

def maximum2[A](xs: List[A])(using ord: Ord[A]): A = 
  xs.reduceLeft(max)

def maximum3[A: Ord](xs: List[A]) = xs.reduceLeft(max)
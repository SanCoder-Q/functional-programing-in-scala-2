package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    k <- arbitrary[Int]
    m <- oneOf(const(empty), genHeap)
  } yield insert(k, m)
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("gen2") = forAll { (h: H) =>
    findMin(meld(h, empty)) == findMin(h)
  }

  property("gen3") = forAll { (h: H, i: Int) => BooleanOperators(!isEmpty(h)) ==> {
    val m = findMin(h)
    if(i < m)
      findMin(deleteMin(insert(i, h))) == m
    else true
  }}

  property("gen4") = forAll { (is: List[Int]) => BooleanOperators(is.length > 1) ==> {
    val h1 = is.sorted.foldLeft(empty) { (h, i) => insert(i, h) }
    val h2 = is.sorted.reverse.foldLeft(empty) { (h, i) => insert(i, h) }
    findMin(deleteMin(h1)) == findMin(deleteMin(h2))
  }}
//  property("gen3") = forAll { (h: H) => BooleanOperators(!isEmpty(h)) ==> {
//    val m = findMin(h)
//    findMin(deleteMin(insert(m, h))) == m
//  }}
}

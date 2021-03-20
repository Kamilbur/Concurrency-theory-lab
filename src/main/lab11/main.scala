package lab11

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Stash}
import akka.event.LoggingReceive

import scala.concurrent.Await
import scala.concurrent.duration._

// object PC contains messages for all actors -- add new if you need them
object PC {
  case class Init()
  case class Put(x: Long)
  case class Get()
  case class ProduceDone()
  case class ConsumeDone(x: Long)
}

class Producer(name: String, buf: ActorRef) extends Actor {
  import PC._
  private val r = scala.util.Random

  def receive: Receive = {
    case _ =>
      val x = r.nextLong(1000) + 1
      Thread.sleep(x)
      buf ! Put(x)
      print("Producer " + name + " produced unit\n")
  }

}

class Consumer(name: String, buf: ActorRef) extends Actor {
  import PC._
  private val r = scala.util.Random

  def receive: Receive = {
    case Init =>
      buf ! Get
    case ConsumeDone(x) =>
      // consume x
      Thread.sleep(x)

      buf ! Get
      print("Consumer " + name + " consumed unit\n")
  }

}


class Buffer(n: Int) extends Actor with Stash {
  import PC._

  val buf = new Array[Long](n)
  var count = 0

  def receive: Receive = LoggingReceive {
    case Put(x) if count < n =>
      unstashAll()
      buf(count) = x
      count += 1
      sender() ! ProduceDone
    case Get if count > 0 =>
      unstashAll()
      sender ! ConsumeDone(buf(count))
      count -= 1
    case _ =>
      stash()
  }
}


object ProdConsMain extends App {
  import PC._

  val system = ActorSystem("ProdCons")

  val size = 10
  val numOfProducers = 3
  val numOfConsumers = 3

  val buffer = system.actorOf(Props(classOf[Buffer], size), "buffer")

  for (i <- 1 to numOfProducers) {
    val p = system.actorOf(Props(classOf[Producer], "p" + i, buffer), "producer" + i)
    p ! Init
  }

  for (i <- 1 to numOfConsumers) {
    val c = system.actorOf(Props(classOf[Consumer], "c" + i, buffer), "consumer" + i)
    c ! Init
  }

  Await.result(system.whenTerminated, Duration.Inf)
}




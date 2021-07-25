package dev.insideyou
package zioplayground

import zio.*

/*
The gateway to functional programmnig in eagerly evalauating language is to wrap statements in thunks.
Thunks are functions without parameters
type Thunk[+A] = () => A
Statements breaks substitution model for procefural languages, in FP we wrap them into thunks, the trick is that we never run those thunks.
We simply compose or chain them together into one giangintic thunk using monads and in the main method, which is dramatically called
End of The World we evaluate this giangitic thunk.
It is common to wrap thunk in a data structure a case class in scala, which we call IO
you can also have a factory method in companion object that takes by name parameter that would create IO for you.

case class IO[+A](thunk: Thunk[A])
object IO:
  def make[A](a: => A): IO[A] = 
    IO(() => a)

*/

// exitCode method just checks if arrow was produced
// All zio applications are descriptions of applications which need Runtime to evaluate this description
// zio provides Runtime.default
// debug and repeadN run asynchronousely
object MainZio extends App:
  val trace = s"[${Console.BLUE}]TRACE${Console.RESET}"
  val program =
    for
      _ <- console.putStrLn("─" * 100)

      _ <- console.putStrLn("hello zio world").debug(trace)
      _ <- console.putStrLn("What is your name?").debug(trace)
      name <- ZIO.succeed("Vlad").debug(trace)
      _<- console.putStrLn(s"Hello $name").debug(trace)
      _ <- console.putStrLn("─" * 100)
    yield ()
  
  override def run(args: List[String]) =
    program.exitCode
 
object Main extends scala.App:
  val program =
    for
      _ <- console.putStrLn("─" * 100)

      //_ <- console.putStrLn("hello world with scala + zio").repeatN(2)
      _ <- console.putStrLn("What is your name?")
      name <- console.getStrLn
      _ <- console.putStrLn(s"Hello $name")
      _ <- console.putStrLn("─" * 100)
    yield ()
  // there is a threadpool that runs this program
  Runtime.default.unsafeRunSync(program)
  // each subprogram can be repeatedly run as shown in hello expression
  //Runtime.default.unsafeRunToFuture(program.repeatN(1))
  // there is some issue with unsafeRunToFuture it runs multiple times even though repeatN has 1
  

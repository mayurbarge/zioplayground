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
object Main extends App:
  val program =
    for
      _ <- console.putStrLn("─" * 100)

      _ <- console.putStrLn("hello world")

      _ <- console.putStrLn("─" * 100)
    yield ()
  override def run(args: List[String]) =
    program.exitCode


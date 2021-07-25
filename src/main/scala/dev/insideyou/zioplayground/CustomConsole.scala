package dev.insideyou
package zioplayground
import zio.*

object myConsole:
    def putStrLn(line: => String) =
        ZIO.succeed(println(line))
    val getStrLn =
        ZIO.succeed(scala.io.StdIn.readLine())

/*
Scala is eagerly evaluated language
In Zio we have by name parameters, in Cats effect you have a different factory defined on IO
which is called Delay which has a by name parameter
*/
object CustomConsole extends scala.App:
    val program =
        for
            _ <- myConsole.putStrLn("_" * 100)
            _ <- myConsole.putStrLn("Hello, What is your name?")
            //name <- myConsole.getStrLn
            name <- ZIO.succeed("Vlad")
            _ <- myConsole.putStrLn(s"Hello, $name")
            //_ <- ZIO.fail(sys.error("boom")) // explodes non-effectul way
            _ <- ZIO.effect(throw java.lang.RuntimeException("boom")) // effect has a by name parameter 
            _ <- myConsole.putStrLn("_" * 100)
        yield ()
    
    //override def run(args: List[String]) =
     //   program.exitCode
     // do not work as expected

    Runtime.default.unsafeRunSync(program)
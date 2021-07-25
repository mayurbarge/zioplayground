package dev.insideyou
package zioplayground

/*
Traditionally in FP monads are outside IO, IO has a monad.
ZIO did not go this way instead flatMap is defined inside ZIO class
*/
object ourzio:
    type Thunk[A] = () => A

    final case class ZIO[A](thunk: Thunk[A]):
        def flatMap[B](azb: A => ZIO[B]): ZIO[B] =
            ZIO.succeed {
               val zb = azb(thunk())
               zb.thunk()
            }

        def map[B](ab: A => B): ZIO[B] =
            ZIO.succeed {
                ab(thunk())
            }
    end ZIO

    object ZIO:
        def succeed[A](a: => A): ZIO[A] =
            ZIO(() => a)

    // In scala3 classes are final by default
    object console:
        def putStrLn(line: => String) =
            ZIO.succeed(println(line))
        val getStrLn =
            ZIO.succeed(scala.io.StdIn.readLine())

    /*
    In category theory (recursion schemes) this is called as a catamorphism, you have a structure an ADT which you collapse down
    to a single value
    */
    // we are not dealing with concurrency here
    // ZIO uses initial encoding/embedding, ZIO is an ADT, every method is a case class
    // interpreter is a gigantic pattern match
    object Runtime:
        object default:
            def unsafeRunSync[A](zio: => ZIO[A]): A =
                zio.thunk()


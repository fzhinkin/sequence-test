package benchmark

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
class SequenceBenchmark {
    val left =  generateSequence(0) { it + 2 }.take(10_000)
    val right = generateSequence(1) { it + 2 }.take(10_000)

    @Benchmark
    fun libraryPlusCount() : Int {
        return left.plus(right).count()
    }

    @Benchmark
    fun libraryPlusFirst() : Int {
        return left.plus(right).first()
    }

    @Benchmark
    fun libraryPlusLast() : Int {
        return left.plus(right).last()
    }

    @Benchmark
    fun standardPlusCount() : Int {
        return sequenceOf(left, right).flatten().count()
    }

    @Benchmark
    fun standardPlusFirst() : Int {
        return sequenceOf(left, right).flatten().first()
    }

    @Benchmark
    fun standardPlusLast() : Int {
        return sequenceOf(left, right).flatten().last()
    }

    @Benchmark
    fun builderPlusCount() : Int {
        return sequence {
            yieldAll(left)
            yieldAll(right)
        }.count()
    }

    @Benchmark
    fun builderPlusFirst() : Int {
        return sequence {
            yieldAll(left)
            yieldAll(right)
        }.first()
    }

    @Benchmark
    fun builderPlusLast() : Int {
        return sequence {
            yieldAll(left)
            yieldAll(right)
        }.last()
    }
}

fun main() {
    // to visually check that all implementations produce similar results
    // and not part of the benchmark
    with (SequenceBenchmark()) {
        println("Library:  " + libraryPlusCount())
        println("standard: " + standardPlusCount())
        println("custom:   " + builderPlusCount())

        println("Library:  " + libraryPlusFirst())
        println("standard: " + standardPlusFirst())
        println("custom:   " + builderPlusFirst())

        println("Library:  " + libraryPlusLast())
        println("standard: " + standardPlusLast())
        println("custom:   " + builderPlusLast())
    }
}

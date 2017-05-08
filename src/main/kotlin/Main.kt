import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking


fun main(args: Array<String>) = spawnManyJobs()

private fun runBlockingExample() {
    runBlocking {
        // start main coroutine
        val job = launch(CommonPool) { printWorld() }
        println("Hello,") // main coroutine continues while child is delayed
        job.join()
    }
}

private suspend fun printWorld() {
    // create new coroutine in common thread pool
    delay(1000L)
    println("World!")
}

private fun spawnManyJobs() {
    runBlocking {
        val jobs = List(100_000) {
            // create a lot of coroutines and list their jobs
            launch(CommonPool) {
                delay(1000L)
                print("$it\n")
            }
        }
        jobs.forEach { it.join() } // wait for all jobs to complete
    }
}
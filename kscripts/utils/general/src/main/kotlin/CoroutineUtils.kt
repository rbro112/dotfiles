/**
 * This file provides the coroutine dependency as well as some common functions for working with coroutines.
 */

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KProperty

/**
 * Helper to synchronously wait for all deferred items to complete.
 */
fun <T> Sequence<Deferred<T>>.awaitAllBlocking(): List<T> {
    return runBlocking { toList().awaitAll() }
}

/**
 * Helper to synchronously wait for all deferred items to complete.
 */
fun <T> List<Deferred<T>>.awaitAllBlocking(): List<T> {
    return runBlocking { awaitAll() }
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async], and a list of transformed items
 * will be returned once all transformations complete.
 *
 * Similar to [mapAsync], but the transformations are automatically awaited and returned as a normal list,
 * with no intermediary [Deferred] objects.
 */
fun <T, R> Sequence<T>.mapInParallel(transform: suspend (T) -> R): List<R> {
    return mapAsync(transform).awaitAllBlocking()
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async], and a list of transformed items
 * will be returned once all transformations complete.
 *
 * Similar to [mapAsync], but the transformations are automatically awaited and returned as a normal list,
 * with no intermediary [Deferred] objects.
 */
fun <T, R> Iterable<T>.mapInParallel(transform: suspend (T) -> R): List<R> {
    return mapAsync(transform).awaitAllBlocking()
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async], and a list of transformed items
 * will be returned once all transformations complete.
 *
 * Similar to [mapAsync], but the transformations are automatically awaited and returned as a normal list,
 * with no intermediary [Deferred] objects.
 */
fun <T, V, R> Map<T, V>.mapInParallel(transform: suspend (T, V) -> R): List<R> {
    return mapAsync(transform).awaitAllBlocking()
}

/**
 * Helper to run a mapIndexed transform with coroutines.
 * Each item will be transformed in its own coroutine via [async], and a list of transformed items
 * will be returned once all transformations complete.
 *
 * Similar to [mapAsync], but the transformations are automatically awaited and returned as a normal list,
 * with no intermediary [Deferred] objects.
 */
fun <T, R> Iterable<T>.mapIndexedInParallel(transform: suspend (index: Int, T) -> R): List<R> {
    return mapIndexedAsync(transform).awaitAllBlocking()
}

/**
 * Similar to the normal [Iterable.filter], but runs the predicate in parallel coroutines for each item.
 * This is useful if the predicate does expensive operations that are thread safe.
 */
fun <T> Iterable<T>.filterInParallel(predicate: suspend (T) -> Boolean): List<T> {
    data class Result<T>(val item: T, val passedPredicate: Boolean)

    return mapAsync { Result(it, predicate(it)) }
        .awaitAllBlocking()
        .filter { it.passedPredicate }
        .map { it.item }
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async].
 *
 * See [awaitAllBlocking] to easily await the results.
 */
fun <T, R> Iterable<T>.mapIndexedAsync(transform: suspend (index: Int, T) -> R): List<Deferred<R>> {
    return mapIndexed { index, t ->
        GlobalScope.async { transform(index, t) }
    }.toList()
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async].
 *
 * See [awaitAllBlocking] to easily await the results.
 */
fun <T, R> Sequence<T>.mapAsync(transform: suspend (T) -> R): List<Deferred<R>> {
    return map {
        GlobalScope.async { transform(it) }
    }.toList()
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async].
 *
 * See [awaitAllBlocking] to easily await the results.
 */
fun <T, R> Iterable<T>.mapAsync(transform: suspend (T) -> R): List<Deferred<R>> {
    return map {
        GlobalScope.async { transform(it) }
    }
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async].
 *
 * See [awaitAllBlocking] to easily await the results.
 */
fun <T, V, R> Map<T, V>.mapAsync(transform: suspend (T, V) -> R): List<Deferred<R>> {
    return map {
        GlobalScope.async { transform(it.key, it.value) }
    }
}

// /**
// * Helper to run indexed actions in parallel with coroutines.
// * Each item will be run in its own coroutine via [async].
// */
// fun <T> Iterable<T>.mapIndexedAsync(action: (index: Int, T) -> Unit): List<Deferred<Unit>> {
//    var index = AtomicInteger(0)
//    return map { GlobalScope.async { action(checkIndexOverflow(index.getAndIncrement()), it) } }
// }

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async], and a list of transformed items
 * will be returned once all transformations complete.
 *
 * Similar to [mapAsync], but the transformations are automatically awaited and returned as a normal list,
 * with no intermediary [Deferred] objects.
 */
fun <T> Sequence<T>.forEachInParallel(transform: suspend (T) -> Unit) {
    mapAsync(transform).awaitAllBlocking()
}

/**
 * Helper to run a map transform with coroutines.
 * Each item will be transformed in its own coroutine via [async], and a list of transformed items
 * will be returned once all transformations complete.
 *
 * Similar to [mapAsync], but the transformations are automatically awaited and returned as a normal list,
 * with no intermediary [Deferred] objects.
 */
fun <T> Iterable<T>.forEachInParallel(transform: suspend (T) -> Unit) {
    mapAsync(transform).awaitAllBlocking()
}

/**
 * A read only properly delegate to easily initialize a value that is computed asynchronously on this coroutine scope.
 * When the property is read however, this will block until the value is ready.
 *
 * This isn't a perfect pattern, since runBlocking is used to await on read, which loses control over the scope that
 * this is accessed and doesn't follow ideal coroutine best practices. However, it is very convenient for scripting,
 * where we don't have to worry about UI or a thread block on read, and where this helps us to write simpler async
 * code that is more readable.
 */
fun <T> CoroutineScope.asyncProp(initializer: suspend () -> T): AsyncProperty<T> = AsyncProperty(this, initializer)

class AsyncProperty<T>(coroutineScope: CoroutineScope, initializer: suspend () -> T) {
    // Kick off the background work asap
    private val async = coroutineScope.async { initializer() }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return runBlocking { async.await() }
    }
}

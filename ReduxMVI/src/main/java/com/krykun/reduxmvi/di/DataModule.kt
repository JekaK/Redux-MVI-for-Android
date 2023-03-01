package com.krykun.reduxmvi.di

import com.krykun.reduxmvi.log.LogConfig
import com.krykun.reduxmvi.log.LogLevel
import com.krykun.reduxmvi.log.destination.ApiLogDestination
import com.krykun.reduxmvi.log.destination.ConsoleLogDestination
import com.krykun.reduxmvi.log.destination.FileLogDestination
import com.krykun.reduxmvi.log.destination.ServerLogDestination
import com.krykun.reduxmvi.utils.JsonStateDiffLogger
import com.krykun.reduxmvi.utils.StateDiffLogger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.Executors

const val NEW_SINGLE_THREAD_DISPATCHER = "NEW_SINGLE_THREAD_DISPATCHER"
const val ANDROID_MAIN_DISPATCHER = "ANDROID_MAIN_DISPATCHER"
const val DEFAULT_DISPATCHER = "DEFAULT_DISPATCHER"
const val IO_DISPATCHER = "IO_DISPATCHER"
const val BINDING_DISPATCHER = "BINDING_DISPATCHER"

private val dispatchersModule = module {
    factory<CoroutineDispatcher>(named(NEW_SINGLE_THREAD_DISPATCHER)) {
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    }
    single<CoroutineDispatcher>(named(ANDROID_MAIN_DISPATCHER)) { Dispatchers.Main }
    single<CoroutineDispatcher>(named(DEFAULT_DISPATCHER)) { Dispatchers.Default }
    single<CoroutineDispatcher>(named(IO_DISPATCHER)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(BINDING_DISPATCHER)) { get(named(NEW_SINGLE_THREAD_DISPATCHER)) }
}

private val apiModule = module {
    single {
        ApiLogDestination(
            minLogLevel = LogConfig.getApiLevel(LogLevel.DEBUG),
            logSendingIntervalSeconds = LogConfig.API_LOG_SENDING_INTERVAL_SECONDS,
        )
    }
    single<ServerLogDestination> { get<ApiLogDestination>() }
}

private val localDataModule = module {

    single {
        val minLevel = LogLevel.DEBUG
        ConsoleLogDestination(minLevel)
    }
    single {
        FileLogDestination(
            dispatcher = get(named(IO_DISPATCHER)),
            context = androidContext(),
            minLogLevel = LogConfig.getFileLevel(LogLevel.DEBUG),
            logBufferSize = LogConfig.FILE_OUTPUT_BUFFER_SIZE,
            maxLogsFileAmount = LogConfig.FILE_OUTPUT_MAX_LOG_FILES_COUNT
        )
    }
    single<StateDiffLogger> {
        JsonStateDiffLogger(
            LogConfig.getOverallMinLogLevel(LogLevel.DEBUG)
        )
    }
}

val dataModules = arrayOf(dispatchersModule, localDataModule, apiModule)

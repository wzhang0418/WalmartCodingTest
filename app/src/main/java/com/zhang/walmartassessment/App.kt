package com.zhang.walmartassessment
import android.app.Application
import com.zhang.walmartassessment.data.util.NetworkMonitor

/**
 * Application class for initializing global app components.
 *
 * Specifically used to initialize and monitor network availability
 * throughout the application lifecycle.
 *
 * @property networkMonitor A utility class responsible for monitoring the network state.
 */
class App : Application() {

    /**
     * Instance of [NetworkMonitor] used to observe network connectivity changes.
     * Initialized in [onCreate].
     */
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate() {
        super.onCreate()
        // Initialize the connectivity checker with application context
        networkMonitor = NetworkMonitor(applicationContext)

        // Start checking for network connectivity
        networkMonitor.checkConnectivity()
    }
}

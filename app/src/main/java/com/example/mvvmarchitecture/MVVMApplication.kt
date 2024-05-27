package com.example.mvvmarchitecture

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mvvmarchitecture.utils.AppConstant
import com.example.mvvmarchitecture.utils.TimeUtil
import com.example.mvvmarchitecture.worker.NewsWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@HiltAndroidApp
class MVVMApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        initWorkManager()
    }

    private fun initWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequest.Builder(
            NewsWorker::class.java,
            24,
            TimeUnit.HOURS
        ).setConstraints(constraints)
            .setInitialDelay(
                TimeUtil.getInitialDelay(
                    AppConstant.Worker.DAILY_NEWS_UPDATE_HOURS,
                    Random.nextInt(0, 59)
                ),
                TimeUnit.MILLISECONDS
            )
            .addTag(AppConstant.Worker.DAILY_NEWS_UPDATE)
            .build()
        workManager.enqueueUniquePeriodicWork(
            AppConstant.Worker.DAILY_NEWS_UPDATE,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, workRequest
        )
    }


}
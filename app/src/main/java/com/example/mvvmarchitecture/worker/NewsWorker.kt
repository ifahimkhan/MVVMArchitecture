package com.example.mvvmarchitecture.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.local.DatabaseService
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.data.model.toArticleEntity
import com.example.mvvmarchitecture.notifications.NotificationHelper
import com.example.mvvmarchitecture.utils.AppConstant
import com.example.mvvmarchitecture.utils.logger.AppLogger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NewsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val parameters: WorkerParameters,
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, parameters) {
    override suspend fun doWork(): Result {
        lateinit var result: Result

        kotlin.runCatching {
            AppLogger().d(NewsWorker::class.java, "Worker for daily news called")
            val topheadlineResponse = networkService.getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
            val articles = topheadlineResponse.articles.map { article: Article ->
                article.toArticleEntity()
            }
            databaseService.deleteAndInsertNewsArticles(articles)
        }.onSuccess {
            notificationHelper.createNotificationChannel()
            notificationHelper.showNotification(
                AppConstant.Notification.Content.TITLE,
                AppConstant.Notification.Content.DESCRIPTION
            )
            result = Result.success()
        }.onFailure {
            result = Result.retry()
        }
        return result

    }

}
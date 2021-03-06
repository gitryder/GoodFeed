package com.mpaani.goodfeed.core.injection

import com.mpaani.goodfeed.core.app.GoodFeedApp
import com.mpaani.goodfeed.core.data.ApiProxy
import com.mpaani.goodfeed.core.data.RetroFit
import com.mpaani.goodfeed.core.db.DataProxy
import com.mpaani.goodfeed.core.db.GoodFeedRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DependencyProvider {

    @Provides
    @Singleton
    fun apiProxy(): ApiProxy = RetroFit

    @Provides
    @Singleton
    fun dataProxy(): DataProxy = GoodFeedRepo

    @Provides
    @Singleton
    fun appContext() = GoodFeedApp.appContext
}

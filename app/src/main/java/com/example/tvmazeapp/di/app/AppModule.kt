package com.example.tvmazeapp.di.app

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.tvmazeapp.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        fun getGlideRequestOptions(): RequestOptions {
            return RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        }

        @Singleton
        @Provides
        @JvmStatic
        fun getRequestManager(
            application: Application,
            requestOptions: RequestOptions
        ): RequestManager {
            return Glide.with(application).setDefaultRequestOptions(requestOptions)
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideDrawable(application: Application): Drawable? {
            return ContextCompat.getDrawable(application, R.mipmap.ic_tvmaze_round)
        }

    }
}
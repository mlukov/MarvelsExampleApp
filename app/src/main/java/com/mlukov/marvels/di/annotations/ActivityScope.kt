package com.mlukov.marvels.di.annotations

import java.lang.annotation.RetentionPolicy
import javax.inject.Named

@Named
@Retention( AnnotationRetention.RUNTIME)
annotation class ActivityScope {
}
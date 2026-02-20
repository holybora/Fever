# Gson reflection models — Gson instantiates these via reflection
-keep class com.sls.handbook.core.network.model.** { *; }

# kotlinx.serialization — keep @Serializable classes and their generated serializers
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep @Serializable classes in :core:model
-keep,includedescriptorclasses class com.sls.handbook.core.model.** { *; }

# Keep @Serializable navigation destinations
-keep,includedescriptorclasses class com.sls.handbook.navigation.** { *; }

# Keep kotlinx.serialization serializer companions
-keepclassmembers class com.sls.handbook.core.model.** {
    *** Companion;
}
-keepclassmembers class com.sls.handbook.navigation.** {
    *** Companion;
}
-keepclasseswithmembers class com.sls.handbook.core.model.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclasseswithmembers class com.sls.handbook.navigation.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Retrofit service interfaces — Retrofit creates dynamic proxies
-keep,allowobfuscation interface com.sls.handbook.core.network.api.WeatherApi
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Preserve source file/line info for crash reporting
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

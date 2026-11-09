# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# Google Places SDK / gRPC
-dontwarn io.grpc.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn com.google.appengine.**
-dontwarn com.google.errorprone.annotations.**

# Keep Gson / Retrofit DTO models and network responses from obfuscation
-keep class com.hathway.ramadankareem2026.core.network.** { *; }
-keep class com.hathway.ramadankareem2026.ui.**.model.** { *; }
-keep class com.hathway.ramadankareem2026.ui.**.data.remote.model.** { *; }
-keep class com.hathway.ramadankareem2026.ui.**.data.local.database.** { *; }
-keep class com.hathway.ramadankareem2026.data.local.** { *; }

# Keep Gson annotations and serialized names
-keepattributes *Annotation*,Signature,EnclosingMethod

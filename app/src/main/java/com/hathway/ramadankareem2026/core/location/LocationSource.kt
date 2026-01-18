package com.hathway.ramadankareem2026.core.location

enum class LocationSource {
    GPS,        // Device GPS
    NETWORK,    // Network-based
    DEMO,      // Offline fallback
    AUTO_DETECTED,   // App chose location automatically
    USER_SELECTED,  // User manually selected location
    NONE

}

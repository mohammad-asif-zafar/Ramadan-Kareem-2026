# 🌙 Ramadan Kareem 2026

A comprehensive Islamic app for Ramadan 2026 featuring prayer times, Quran reading, Duas, Zakat calculator, and more.

## 📱 Features

### 🕌 **Prayer Times System**
- **📍 Location-Based Detection**: Automatic timezone detection based on GPS coordinates
- **🌍 Global Coverage**: Supports timezones worldwide with smart fallbacks
- **⏰ Live Countdown**: Real-time countdown to next prayer
- **🔔 Prayer Notifications**: Customizable prayer time reminders
- **📅 Historical Data**: Prayer times for any date
- **� API Integration**: Aladhan.com API with graceful fallbacks

### �📖 **Quran Reader**
- **📚 Complete Quran**: All 114 Surahs with translations
- **🎵 Audio Recitations**: Multiple Qari recitations available
- **🌍 Multi-Language**: English, Hindi, Urdu, Malay translations
- **🔖 Reading Modes**: Verse by verse or continuous reading
- **📑 Bookmarks**: Save favorite verses and Surahs
- **🌙 Arabic Script**: Proper Quranic text display

### 🤲 **Islamic Duas**
- **📚 Comprehensive Collection**: Duas for various occasions
- **🏷️ Categorized**: Faith, Family, Health, Protection, Daily
- **🌍 Multilingual Support**: LocalizedDuaText for all languages
- **🔖 Search Function**: Find duas by keywords
- **📌 Bookmarks**: Save and organize favorite duas
- **� Notifications**: Dua reminders and notifications

### �💰 **Zakat Calculator**
- **💎 Multiple Assets**: Gold, Silver, Cash, Savings calculations
- **📊 Nisab Threshold**: Automatic threshold calculations
- **📈 Historical Rates**: Up-to-date market values
- **📋 Detailed Breakdown**: Clear calculation methodology
- **📤 Export Results**: Share calculation results

### 🧭 **Qibla Compass**
- **🧭 Precise Direction**: Accurate Kaaba direction
- **📍 Location-Based**: Automatic direction calculation
- **📱 Calibration**: Simple compass calibration
- **🌍 Distance Display**: Distance to Kaaba in km

### 📅 **Ramadan Calendar**
- **🌙 Hijri Calendar**: Accurate Islamic calendar integration
- **⏰ Fasting Times**: Suhoor and Iftar times
- **📊 Progress Tracking**: Daily Ramadan progress
- **🎯 Important Dates**: Laylat al-Qadr, Eid dates
- **📱 Countdown**: Days remaining in Ramadan

### 🗺️ **Mosque Finder**
- **🗺️ Nearby Mosques**: Find mosques using GPS
- **📍 Location Services**: GPS and network location
- **📞 Contact Info**: Mosque contact details
- **🗺️ Directions**: Navigate to mosques
- **📝 Reviews**: Mosque ratings and reviews

### 🌍 **Multi-Language System**
- **🌐 Language Support**: English, Hindi, Urdu, Malay
- **🔄 Real-Time Switching**: Instant language changes
- **📱 RTL Support**: Right-to-left layout for Urdu
- **🔤 Localization Manager**: Centralized language management
- **📝 String Resources**: 30+ extracted and translated

## 🛠️ Technical Architecture

### 🏗 **Project Structure**
```
com.hathway.ramadankareem2026/
├── MainActivity.kt                 # App entry point
├── RamadanKareemApp.kt           # Application class
├── core/                         # Core utilities & services
│   ├── constants/                 # App constants
│   ├── currency/                  # Currency utilities
│   ├── di/                        # Dependency injection
│   ├── localization/              # 🌍 Multi-language support
│   ├── location/                 # 📍 Location services
│   ├── network/                  # 🌐 Network utilities
│   ├── service/                  # Background services
│   ├── time/                     # Time utilities
│   ├── tts/                      # Text-to-speech
│   └── util/                     # 🛠️ General utilities
├── data/                         # Data layer
├── ui/                           # UI layer
│   ├── allahnames/               # Allah Names feature
│   ├── bookmarks/                 # Bookmarks feature
│   ├── components/                # Reusable UI components
│   ├── dua/                      # 🕌 Duas feature (multilingual)
│   ├── home/                     # Home screen
│   ├── mosques/                  # Mosque finder
│   ├── navigation/                # Navigation
│   ├── prayer/                   # 🕌 Prayer times (location-based API)
│   ├── qibla/                   # Qibla direction
│   ├── quran/                    # Quran reader
│   ├── ramadan/                  # Ramadan calendar
│   ├── settings/                  # App settings
│   ├── theme/                     # App theming
│   ├── tips/                      # Islamic tips
│   ├── widget/                    # App widgets
│   └── zakat/                    # Zakat calculator
```

### 🔧 **Technology Stack**
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM + Clean Architecture
- **Database:** Room with DataStore
- **Networking:** Retrofit2 + OkHttp + Gson
- **Dependency Injection:** Hilt
- **Async:** Coroutines + Flow
- **Location:** Google Play Services Location
- **Maps:** Google Maps Platform
- **Images:** Coil Image Loading

### 📊 **Data Flow**
```
UI Layer (Compose)
    ↓
ViewModel (MVVM)
    ↓
Repository (Clean Architecture)
    ↓
Data Sources (API + Local + Cache)
    ↓
External APIs (Aladhan + AlQuran Cloud)
```

## 📋 Requirements

### 📱 **System Requirements**
- **Android:** API 24 (Android 7.0) and above
- **Memory:** 2GB RAM minimum
- **Storage:** 100MB free space
- **Network:** Internet connection for prayer times

### 🔐 **Permissions Required**
- **📍 Location:** GPS and network location
- **🌐 Internet:** For prayer times and Quran content
- **🔔 Notifications:** Prayer and dua reminders
- **💾 Storage:** For bookmarks and settings

## 🔗 APIs & Data Sources

### � **Prayer Times API**
- **Provider:** Aladhan.com API
- **Endpoint:** `https://api.aladhan.com/v1/timings/{date}`
- **Method:** Muslim World League (Method 3)
- **Timezone:** Automatic detection based on coordinates
- **Fallback:** Local calculations using Adhan library

### �📖 **Quran Content API**
- **Provider:** AlQuran Cloud API
- **URL:** `https://api.alquran.cloud`
- **Content:** Quran text, translations, audio recitations
- **Languages:** English, Hindi, Urdu, Malay

### 🗺️ **Maps & Location**
- **Services:** Google Maps Platform, Google Places API
- **Usage:** Location detection and nearby mosques
- **Features:** Geocoding, reverse geocoding

## 🌍 Localization Details

### 📝 **Translation Coverage**
- **Search Interfaces:** Surah, Allah Names, City search
- **Navigation:** All navigation elements
- **Prayer Times:** All prayer-related UI text
- **Settings:** Complete settings interface
- **Common Elements:** Actions, buttons, dialogs
- **Accessibility:** Screen reader support

### � **UI Adaptations**
- **Urdu RTL:** Right-to-left layout support
- **Text Scaling:** Dynamic font sizing
- **Theme Support:** Light/Dark mode compatibility
- **Responsive Design:** Tablet and phone layouts

## 📦 Installation

### 🔧 **Development Setup**
```bash
# Clone the repository
git clone https://github.com/mohammad-asif-zafar/Ramadan-Kareem-2026.git
cd Ramadan-Kareem-2026

# Open in Android Studio
open -a Android Studio .

# Sync Gradle files
./gradlew build

# Run debug build
./gradlew assembleDebug

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 🏗️ **Build Variants**
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Install release APK
adb install app/build/outputs/apk/release/app-release.apk
```

## 📱 Screenshots & Features

### 🏠 **Home Screen**
- Current prayer times widget
- Quick access to main features
- Location-based prayer times
- Ramadan countdown timer

### 🕌 **Prayer Times Detail**
- Complete prayer schedule for the day
- Location-based timezone detection
- Live countdown to next prayer
- Prayer notifications setup

### 📖 **Quran Reader**
- Surah and verse navigation
- Multiple translation support
- Audio recitation playback
- Bookmark management

### 🤲 **Duas Collection**
- Categorized duas for different occasions
- Search and filter functionality
- Multilingual dua content
- Bookmark favorite duas

### 💰 **Zakat Calculator**
- Multiple asset types support
- Real-time market rates
- Detailed calculation breakdown
- Share and export results

### 🧭 **Qibla Compass**
- Precise Kaaba direction
- Distance to Kaaba display
- Location-based calculations
- Simple calibration interface

### 📅 **Ramadan Calendar**
- Hijri calendar integration
- Fasting times display
- Progress tracking
- Important dates highlighting

## 🤝 Contributing

We welcome contributions! Please follow our guidelines:

### 📋 **Development Guidelines**
1. **Code Style:** Follow existing Kotlin conventions
2. **Architecture:** Maintain clean architecture principles
3. **Testing:** Add unit tests for new features
4. **Documentation:** Update README for new features
5. **Localization:** Add translations for new strings

### 🌟 **How to Contribute**
```bash
# 1. Fork the project
git clone https://github.com/YOUR_USERNAME/Ramadan-Kareem-2026.git

# 2. Create feature branch
git checkout -b feature/YourAmazingFeature

# 3. Make your changes
# Add your amazing feature

# 4. Commit changes
git commit -m "Add: Your amazing feature description"

# 5. Push to your fork
git push origin feature/YourAmazingFeature

# 6. Create Pull Request
# Submit PR with detailed description
```

### 🎯 **Areas for Contribution**
- **🌍 Translations:** Help translate to more languages
- **🕌 Prayer Methods:** Add new calculation methods
- **📖 Quran Content:** Improve translations and recitations
- **🗺️ Mosque Data:** Add mosque information
- **💡 Islamic Content:** Add more duas and tips
- **🐛 Bug Reports:** Report issues with detailed steps

## 🙏 Acknowledgments

### 📚 **Third-Party Libraries**
- **AlQuran Cloud** for providing Quran API services
- **Batoul Apps** for Adhan prayer calculation library
- **Google Maps Platform** for maps and places services
- **Retrofit** for networking and API communication
- **Jetpack Compose** for modern UI framework
- **Room** for local database operations

### 🎓 **Islamic Content Sources**
- **Islamic scholars** for guidance and content verification
- **Trusted sources** for authentic Islamic content
- **Community feedback** for continuous improvement
- **Open source contributions** from Muslim developers

## 📞 Support

For support, questions, or feedback:

### 📧 **Technical Support**
- **📧 Email:** mohammadasifzafar000@gmail.com
- **🌐 Privacy Policy:** https://mohammad-asif-zafar.github.io/Ramadan-Kareem-2026/privacy-policy.html
- **📱 Play Store:** [Link to Play Store listing]

### 🐛 **Bug Reports**
- **📝 Description:** Detailed steps to reproduce
- **📱 Device Info:** Android version and device model
- **📍 Location:** Country and timezone information
- **📸 Screenshots:** Screenshots of the issue

### 💡 **Feature Requests**
- **🎯 Use Case:** Clear description of feature purpose
- **👥 Target Users:** Who would benefit from this feature
- **🌍 Impact:** How it improves user experience

---

**Made with ❤️ for the Muslim community worldwide**

*Ramadan Kareem 2026 - Your comprehensive companion for a blessed Ramadan*

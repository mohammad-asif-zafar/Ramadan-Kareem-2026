# 🌙 Noor Muslim Companion

A premium, comprehensive Islamic companion app designed to strengthen your Imaan through beautiful design and essential spiritual tools. Originally built for Ramadan, now evolved into a year-round spiritual partner with full support for **Ramadan 2027**.

## 📱 Features

### 🕌 **Prayer Times & Qibla**
- **📍 Precise Location Detection**: Automatic calculation of prayer times based on your current GPS coordinates.
- **🧭 Enhanced Qibla Compass**: A modern, high-precision compass with a visual alignment pulse and 3D needle.
- **⏰ Live Prayer Countdown**: Real-time updates for the next prayer with "Time Now" highlighting.
- **🔔 Smart Notifications**: Customizable Azan alerts and prayer reminders.

### 📖 **Interactive Quran Reader**
- **📚 Complete 114 Surahs**: High-quality Arabic script with multiple language translations (English, Hindi, Urdu, Malay).
- **🎵 Seamless Audio Playback**: Background audio support with real-time verse highlighting and auto-scroll.
- **🔖 Advanced Bookmarking**: Quick-save Surahs and verses with a modern, animated interface.
- **🌗 Reading Modes**: Optimized for both Light and Dark themes for comfortable reading day or night.

### ✨ **99 Names of Allah (Asma-ul-Husna)**
- **🎨 Beautiful Visuals**: Each name is presented in an elegant ornamental ring.
- **📝 Meaning & Benefits**: Detailed explanations and transliterations for every name.
- **🔖 Personal Bookmarks**: Save specific names for daily reflection.

### 🤲 **Essential Duas & Daily Tips**
- **📚 Categorized Collection**: Comprehensive Duas for daily protection, faith, and family.
- **💡 Daily Wisdom**: Inspirational Islamic tips that change daily to guide your spiritual journey.
- **🔊 Audio Support**: Listen to Duas with clear Arabic pronunciation.

### 💰 **Zakat Calculator**
- **💎 Multiple Asset Tracking**: Easily calculate Zakat for Gold, Silver, Cash, and Savings.
- **📊 Automatic Nisab**: Real-time threshold calculation based on market values.
- **📋 Detailed History**: Keep track of your previous spiritual obligations.

### 📅 **Ramadan 2027 Tools**
- **🌙 Dynamic Calendar**: Full 30-day schedule for Ramadan 2027 (starting Feb 2027).
- **📈 Progress Tracking**: Visual progress bar and countdown for the Holy Month.
- **⏰ Suhoor & Iftar**: Accurate timings with dedicated status cards.

## 🛠️ Technical Architecture

### 🏗 **Project Structure**
```
com.hathway.ramadankareem2026/
├── core/                         # Core utilities (Location, Localization, Time)
├── data/                         # Data layer (DataStore, Repositories)
├── ui/                           # UI layer (Jetpack Compose)
│   ├── allahnames/               # Allah Names feature
│   ├── quran/                    # Enhanced Quran reader with audio
│   ├── prayer/                   # Prayer times logic
│   ├── qibla/                    # Compass and direction
│   ├── home/                     # Dynamic dashboard
│   ├── onboarding/               # Theme-aware onboarding flow
│   └── theme/                    # Emerald & Gold design system
```

### 🔧 **Technology Stack**
- **Language:** 100% Kotlin
- **UI Framework:** Jetpack Compose (Modern Declarative UI)
- **Architecture:** MVVM + Clean Architecture
- **Local Storage:** Room Database & Proto DataStore
- **Networking:** Retrofit2 + OkHttp (Aladhan & AlQuran APIs)
- **Animation:** Compose Animation API for smooth transitions

## 📋 Requirements
- **Android:** API 24 (Android 7.0) and above
- **Permissions:** Location (for Prayer/Qibla), Internet, Notifications

## 📦 Installation & Setup

```bash
# Clone the repository
git clone https://github.com/mohammad-asif-zafar/Ramadan-Kareem-2026.git
cd Ramadan-Kareem-2026

# Sync and Build
./gradlew build
```

## 🤝 Acknowledgments
- **AlQuran Cloud** for Quranic APIs.
- **Batoul Apps** for the Adhan calculation library.
- **Google Maps Platform** for location-based mosque discovery.

---

**Technical Support & Privacy**
- **📧 Email:** mohammadasifzafar000@gmail.com
- **🌐 Privacy Policy:** [View Policy](https://mohammad-asif-zafar.github.io/Ramadan-Kareem-2026/privacy-policy.html)
- **🏠 Website:** [Official Landing Page](https://mohammad-asif-zafar.github.io/Ramadan-Kareem-2026/)

**Made with ❤️ for the Muslim community worldwide**

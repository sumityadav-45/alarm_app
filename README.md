# Custom Alarm Manager Application 🔔

A lightweight and efficient Android application built natively using **Kotlin** and **XML** that allows users to set exact alarms using Android's `AlarmManager` API. It includes feature flags for Android 13+ runtime permissions and handles modern background restrictions gracefully.

## 🚀 Features

*   **Custom Time Selection:** Integrated `TimePicker` to choose any specific hour and minute for the alarm.
*   **Exact Alarm Scheduling:** Uses `setExactAndAllowWhileIdle()` to ensure the alarm triggers precisely, even when the device enters Doze/Power-saving mode.
*   **Quick Test Mode:** A dedicated button to set a quick alarm that triggers exactly after 10 seconds (perfect for debugging and testing receivers).
*   **Modern Android Compliance:** Seamlessly handles dynamic runtime permissions (`POST_NOTIFICATIONS`) required for Android 13 (API level 33) and above.
*   **Immutable Intents:** Implements secure `PendingIntent.FLAG_IMMUTABLE` flags aligning with modern Android security standards.

---

## 🛠️ Tech Stack & Components

*   **Language:** Kotlin
*   **UI Layout:** XML (`LinearLayout`, `TimePicker`, `Material Components`)
*   **Core APIs:** `AlarmManager`, `PendingIntent`, `BroadcastReceiver` (via `MyReceiver`)
*   **Architecture Pattern:** Clean Imperative Event Handling

---

##  UI Layout Preview

The interface features a structured layout designed for utility:
*   **Header Section:** Displays student identification details clearly.
*   **Time Display Banner:** A prominent status section showing the scheduled active time.
*   **Control Panel:** Easy-to-use toggle switches to activate or cancel scheduled events instantly.

---

##  How it Works under the Hood

1. **Permission Check:** On launch, if running on Android 13+, it requests notification permissions dynamically.
2. **Setting Alarm:** When you hit **Alarm ON**, it fetches hours and minutes from the picker, configures a `Calendar` instance, and if the time has already passed for today, it automatically schedules it for tomorrow.
3. **Trigger mechanism:** It broadcasts an implicit `Intent` to `MyReceiver` using `RTC_WAKEUP` which wakes up the CPU to execute the task when the clock hits the target timeline.
4. **Cancellation:** The **Alarm OFF** button instantly accesses the existing `PendingIntent` context and safely tears down the scheduled register.

---

##  Academic Project Details
*   **Developer:** SUMIT KUMAR
*   **Roll No:** 2305561

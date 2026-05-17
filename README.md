# Channapatna Namma Pride

A native Android app built with Kotlin and Jetpack Compose to promote authentic Channapatna wooden toys, certified artisans, verified sellers, and batch/artisan verification.

## What Is Included

- Native Kotlin Android project for Android Studio.
- Jetpack Compose Material 3 interface with catalog, verification, maker profiles, and seller map screens.
- Firebase Authentication login/signup/logout.
- Firebase Firestore user profiles, cart items, and orders.
- English/Kannada language toggle inside the app.
- Seeded verified sample data for products, artisans, and authorized locations.
- Google Maps navigation through Android geo intents, so it works without embedding an API key.

## Firebase Setup

The app is configured for the Firebase Android client package:

```text
com.example.channapatna
```

The Firebase config file must be here:

```text
app/google-services.json
```

In Firebase Console, enable:

- Authentication -> Sign-in method -> Email/Password
- Firestore Database

Use `firestore.rules` for beginner-friendly demo rules. They allow signed-in users to read and write only their own profile, cart, and orders.

## Run

1. Open this folder in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an emulator or device.

## Next Production Steps

- Add Play Integrity or App Check before public write operations.
- Add embedded Google Maps SDK only if an in-app map view is required. The current version intentionally uses Maps intents for reliable one-tap navigation.

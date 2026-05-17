# Firebase Fix

The current APK is connected to this Firebase Android app:

- Firebase project ID: `channapatnaapp-8b10a`
- Android package name: `com.example.channapatna`
- Config file: `app/google-services.json`

Your Firebase Console screenshot shows a different project:

- `ChannapatnaPride`
- `channapatnapride-8dfcf`

That mismatch causes the login/signup error. Choose one solution.

## Option 1: Use The Current App Config

Open Firebase Console project `channapatnaapp-8b10a`, then enable:

1. Build -> Authentication -> Get started
2. Sign-in method -> Email/Password -> Enable -> Save
3. Build -> Firestore Database -> Create database
4. Firestore Rules -> paste `firestore.rules` -> Publish

## Option 2: Use Your Visible `ChannapatnaPride` Project

In Firebase Console project `ChannapatnaPride`:

1. Project settings -> Your apps
2. Add app -> Android
3. Android package name: `com.example.channapatna`
4. Download the new `google-services.json`
5. Replace `app/google-services.json` in this project
6. Rebuild the APK
7. Enable Authentication Email/Password and Firestore in that same project

Do not edit API keys manually. Always download `google-services.json` from the Firebase project you want the APK to use.

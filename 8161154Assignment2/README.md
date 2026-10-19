# 8161154Assignment2

NIT3213 Android assignment for student 8161154, Brisbane.

## Open and run

1. Extract this ZIP and open the `8161154Assignment2` folder in Android Studio.
2. Use JDK 17 for Gradle. Install Android SDK Platform 34 if Android Studio requests it.
3. Let Gradle sync download dependencies. Internet is required for the first sync.
4. Start an Android emulator (API 24 or newer), or connect an Android device with USB debugging enabled.
5. Select the app configuration and press Run.
6. Enter your student ID without `s` and your exact, case-sensitive first name. The app uses the Brisbane `/br/auth` endpoint.

The supplied credentials were rejected by the live API with HTTP 400 during preparation. Verify the spelling/capitalization registered for your first name with your lecturer if needed. No password is hardcoded or included here.

## Features

- Login validates empty fields and digits-only student IDs, calls POST `/br/auth`, and displays connection/server errors.
- Login returns a keypass used by GET `/dashboard/{keypass}`.
- Dashboard displays all entity summary properties except description in a RecyclerView, with loading, empty, and retry states.
- Tapping an entity opens Details, showing every property including description. Back returns to the existing dashboard.
- Successful login removes Login from the navigation back stack.

## Structure and course examples

- `data`: request/response data classes, Retrofit interface, repository, Parcelable entity.
- `di`: Hilt module supplying the Retrofit service.
- `ui`: Fragments, ViewModels with StateFlow, RecyclerView adapter.
- XML layouts and resources under `app/src/main/res`.
- Navigation graph, Safe Args and Parcelize follow L4.
- Retrofit 2.11.0 with Moshi 1.14.0, repository, coroutines and StateFlow follow L5.
- RecyclerView follows L6. Hilt 2.51.1 follows L7.
- JUnit 4.13.2, MockK 1.13.12, coroutine dispatchers and runTest follow L8.
- Dynamic JSON properties use Map as described in L5, so no topic-specific field names are invented.
- Android/Gradle build configuration supplies the standard project scaffolding needed to open the app.
- The Spring Boot example is reference material; this assignment connects to the university API and does not require a local backend.

## Tests

Run from Android Studio by right-clicking `app/src/test/java` and choosing Run Tests, or use:

Windows:
```
gradlew.bat testDebugUnitTest
```
macOS/Linux:
```
./gradlew testDebugUnitTest
```

Twelve unit tests cover ViewModel validation, keypass handling, failures/retries, duplicate requests, repository forwarding/mapping, and summary/details content.

Build an APK with `gradlew.bat assembleDebug` (Windows) or `./gradlew assembleDebug` (macOS/Linux).

## Verification status

Source/XML structure checks completed during preparation. An attempted `testDebugUnitTest assembleDebug` run could not start because this environment could not resolve services.gradle.org to download Gradle. The preparation environment has no Android SDK, so Android compilation, the unit test suite, and emulator behavior have not been verified. Run the commands above before submission. The API rejected the supplied login, so successful authentication and the real topic data remain unverified.

## Git and submission

The ZIP includes a local Git repository with commits made while preparing this project. Continue making meaningful commits as you test and improve it. Do not fabricate development history.

Create a GitHub repository, then use GitHub Desktop to add this local repository and publish it. Alternatively, from this folder:

```
git remote add origin YOUR_GITHUB_REPOSITORY_URL
git push -u origin main
```

Replace the placeholder with your real repository URL. Submit that repository link, as the specification requires. Review the code, run the tests and app, and ensure you can explain it before submitting.

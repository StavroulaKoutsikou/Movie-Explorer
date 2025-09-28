# Movie Explorer — Jetpack Compose (TMDb)

Μικρή Android εφαρμογή σε **Kotlin / Jetpack Compose** που:
- εμφανίζει **δημοφιλείς ταινίες** TMDb,
- κάνει **αναζήτηση** τίτλων,
- ανοίγει **οθόνη λεπτομερειών** (poster, βαθμολογία, διάρκεια, είδη, overview).


https://github.com/user-attachments/assets/22e40c28-58e6-4548-97a3-6960948a9d4f

---

## Features
- **Popular list** με lazy λίστα & loading/error states  
- **Search** με query  
- **Details screen**: poster, τίτλος, ⭐ rating, runtime, genres, overview  
- **Theming**: Material 3 (μοβ παλέτα), **κάρτες πορτοκαλί**, τίτλοι μωβ

---

## Τεχνολογίες
- Kotlin, Coroutines/Flow  
- Jetpack Compose (Material 3, Navigation Compose)  
- Hilt (Dagger) για Dependency Injection  
- Retrofit + OkHttp (logging-interceptor), Gson  
- Coil (φόρτωση εικόνων)  
- TMDb API (popular, search, details)


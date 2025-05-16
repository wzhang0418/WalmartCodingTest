# Country List App 

The **Country List App** is a Kotlin-based application that fetches a list of countries from an API and displays them using a `RecyclerView`. Built with the principles of **Clean Architecture**, the app ensures easy maintainability and scalability, perfect for testing and future feature additions.

---

### Features

- **Country List Display**: Fetch and show country details like name, region, and capital.
- **Clean Architecture**: Clear separation of concerns, making it easy to maintain and extend.
- **Unit Testing**: Robust unit tests to ensure correctness and reliability.
- **Smooth UI**: Displays country data in a list with clear UI elements using `RecyclerView`.

---

### Architecture Breakdown

The app is structured around **Clean Architecture**, split into 4 key layers:

- **Presentation**: Contains the UI elements like `RecyclerView` and ViewModel for handling UI-related data.
- **Domain**: Contains the business logic (UseCases, Repository, DTO).
- **Data**: Where all the network calls, data parsing, and data repositories live.
- **App**: Entry point for setting up dependencies, including the app class.

---

### Tech Stack

- **Kotlin**: The modern language for Android development.
- **MVVM**: Model-View-ViewModel architecture for clean separation of concerns.
- **Retrofit**: Handles all network requests to fetch country data.
- **Coroutines Flow**: For efficient and seamless handling of asynchronous operations.
- **Unit Testing**: Ensures that the app's features work as expected.
- **RecyclerView**: Displays country data in a scrollable list.

---

## Demo
<table>
  <tr>
    <th>Screenshot 1</th>
    <th>Screenshot 2</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/c3421009-d639-470f-9bbd-11c16c5b75bc" alt="Screenshot" width="400" /></td>
    <td><img src="https://github.com/user-attachments/assets/13b314e5-538f-4087-80be-1f5beeeb56b9" alt="Demo.gif" width="400" /></td>
  </tr>
</table>

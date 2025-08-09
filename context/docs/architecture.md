# Smart Expense Tracker - Architecture Documentation

## 🏗️ Overview

Smart Expense Tracker follows **Clean Architecture** principles with **multi-module** structure, designed for small business owners to track daily expenses efficiently.

## 📦 Module Structure

```
SmartExpensetracker/
├── app/                    # Presentation Layer
├── domain/                # Business Logic Layer  
├── data/                  # Data Layer
└── common/                # Shared Utilities
```

### **Module Dependencies**
```
app → domain, common
data → domain, common  
domain → common
```

## 🎯 Core Architecture

### **Clean Architecture Layers**

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │     Domain      │    │      Data       │
│                 │    │                 │    │                 │
│  ┌───────────┐  │    │  ┌───────────┐  │    │  ┌───────────┐  │
│  │ViewModel  │  │───▶│  │ Use Cases │  │───▶│  │Repository │  │
│  └───────────┘  │    │  └───────────┘  │    │  └───────────┘  │
│  ┌───────────┐  │    │  ┌───────────┐  │    │  ┌───────────┐  │
│  │ Compose   │  │    │  │  Models   │  │    │  │DataSource │  │
│  │    UI     │  │    │  │ Business  │  │    │  │   Room    │  │
│  └───────────┘  │    │  │   Logic   │  │    │  └───────────┘  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 📱 Features & Screens

### **1. Expense Entry Screen**
- **Purpose**: Add new expenses with real-time validation
- **Key Features**: 
  - Title, Amount, Category, Notes input
  - Receipt image upload (optional)
  - Real-time "Total Spent Today" display
  - Submit with animation and Toast

### **2. Expense List Screen**  
- **Purpose**: View and filter expenses
- **Key Features**:
  - Today's expenses (default)
  - Date filtering and calendar selection
  - Group by category or time
  - Empty states and loading indicators

### **3. Expense Report Screen**
- **Purpose**: Analytics and insights
- **Key Features**:
  - 7-day expense summary
  - Category-wise breakdown
  - Bar/line charts (mocked)
  - Export functionality (PDF/CSV)

## 🗂️ Detailed Module Breakdown

### **App Module** (`:app`)
```
app/src/main/java/com/shankarkakumani/smartexpensetracker/
├── feature/
│   ├── expense_entry/
│   │   ├── ExpenseEntryScreen.kt
│   │   ├── ExpenseEntryViewModel.kt
│   │   └── ExpenseEntryUiState.kt
│   ├── expense_list/
│   │   ├── ExpenseListScreen.kt
│   │   ├── ExpenseListViewModel.kt
│   │   └── ExpenseListUiState.kt
│   └── expense_report/
│       ├── ExpenseReportScreen.kt
│       ├── ExpenseReportViewModel.kt
│       └── ExpenseReportUiState.kt
├── navigation/
│   └── Navigation.kt
├── ui/
│   ├── components/
│   └── theme/
└── di/
    └── AppModule.kt
```

### **Domain Module** (`:domain`)
```
domain/src/main/java/com/shankarkakumani/domain/
├── model/
│   ├── Expense.kt
│   ├── ExpenseCategory.kt
│   └── WeeklyReport.kt
├── repository/
│   └── ExpenseRepository.kt
├── usecase/
│   ├── AddExpenseUseCase.kt
│   ├── GetExpensesUseCase.kt
│   ├── GetTotalSpentTodayUseCase.kt
│   └── GetWeeklyReportUseCase.kt
└── util/
    └── DateUtils.kt
```

### **Data Module** (`:data`)
```
data/src/main/java/com/shankarkakumani/data/
├── repository/
│   └── ExpenseRepositoryImpl.kt
├── datasource/
│   ├── ExpenseLocalDataSource.kt
│   └── ExpenseInMemoryDataSource.kt
├── dto/
│   └── ExpenseDto.kt
├── mapper/
│   └── ExpenseMapper.kt
└── di/
    └── DataModule.kt
```

### **Common Module** (`:common`)
```
common/src/main/java/com/shankarkakumani/common/
├── base/
│   ├── BaseViewModel.kt
│   └── BaseScreen.kt
├── ext/
│   ├── ComposeExtensions.kt
│   └── DateExtensions.kt
├── result/
│   └── Result.kt
├── util/
│   └── ValidationUtils.kt
└── constants/
    └── AppConstants.kt
```

## 🔄 Data Flow

### **Expense Entry Flow**
```
User Input → ViewModel → UseCase → Repository → DataSource → Success/Error → UI Update
```

### **Expense List Flow**
```
Screen Load → ViewModel → UseCase → Repository → DataSource → Data → UI State → Compose UI
```

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| **UI Framework** | Jetpack Compose |
| **Architecture** | Clean Architecture + MVVM |
| **State Management** | StateFlow |
| **Navigation** | Compose Navigation |
| **Dependency Injection** | Hilt |
| **Database** | Room (optional) |
| **Charts** | MPAndroidChart |
| **Image Loading** | Coil |
| **Build System** | Gradle (Kotlin DSL) |

## 📊 Data Models

### **Core Models**
```kotlin
data class Expense(
    val id: String,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val notes: String?,
    val receiptImageUrl: String?,
    val timestamp: Long
)

enum class ExpenseCategory {
    STAFF, TRAVEL, FOOD, UTILITY
}

data class WeeklyReport(
    val dailyTotals: Map<LocalDate, Double>,
    val categoryTotals: Map<ExpenseCategory, Double>,
    val totalSpent: Double
)
```

## 🎨 UI/UX Guidelines

### **Design Principles**
- **Material 3** design system
- **Responsive layouts** for different screen sizes
- **Accessibility** support
- **Dark/Light theme** support (bonus)

### **Animation Strategy**
- **Entry animations** for new expenses
- **Smooth transitions** between screens
- **Loading states** with skeleton screens
- **Micro-interactions** for better UX

## 🔧 Development Phases

### **Phase 1: Foundation** (Week 1)
- [ ] Multi-module setup
- [ ] Domain models and interfaces
- [ ] Basic navigation structure
- [ ] DI setup with Hilt

### **Phase 2: Core Features** (Week 2)
- [ ] Expense Entry screen
- [ ] Expense List screen
- [ ] Basic CRUD operations
- [ ] Real-time total calculation

### **Phase 3: Advanced Features** (Week 3)
- [ ] Expense Report screen
- [ ] Charts and analytics
- [ ] Filtering and grouping
- [ ] Export functionality

### **Phase 4: Polish** (Week 4)
- [ ] Animations and transitions
- [ ] Theme switching
- [ ] Room database integration
- [ ] Testing and optimization

## 🤖 AI Integration Strategy

### **AI Usage Areas**
- **UI Layout Generation**: Compose UI components
- **Code Generation**: ViewModels, UseCases, Repositories
- **Architecture Guidance**: Clean Architecture patterns
- **Optimization**: Performance and UX improvements

### **Documentation Requirements**
- **AI Usage Summary**: 3-5 sentences
- **Prompt Logs**: Key prompts and iterations
- **Code Comments**: AI-generated documentation

## 🧪 Testing Strategy

### **Unit Tests**
- Use Cases
- Repository implementations
- ViewModels
- Data mappers

### **UI Tests**
- Screen navigation
- User interactions
- State changes

### **Integration Tests**
- End-to-end workflows
- Data persistence

## 📈 Performance Considerations

- **Lazy loading** for expense lists
- **Pagination** for large datasets
- **Image optimization** for receipts
- **Memory management** for charts
- **Background processing** for reports

---

*This architecture ensures scalability, maintainability, and follows modern Android development best practices.*

# Smart Expense Tracker - Development Prompts & Verification

## 🎯 How to Use These Prompts

Each phase contains:
1. **Implementation Prompt** - Detailed instructions for AI-assisted development
2. **Verification Checklist** - Requirements validation
3. **Progress Update** - Commands to update tasks.md

## 📋 Phase 1: Foundation Setup

### 🤖 Implementation Prompt
```
I'm building a Smart Expense Tracker Android app following Clean Architecture with multi-module structure. 

Please help me set up the foundation:

1. **Multi-Module Structure**: Create gradle files for app, domain, data, and common modules
2. **Dependencies**: Set up version catalog with Jetpack Compose, Hilt, Room, Navigation, MPAndroidChart
3. **Hilt Setup**: Configure dependency injection across modules
4. **Package Structure**: Create proper package hierarchy following Clean Architecture
5. **Base Classes**: Create BaseViewModel, BaseScreen, Result wrapper

Requirements:
- Use Kotlin DSL for Gradle
- Follow Clean Architecture module dependencies (app→domain,common; data→domain,common; domain→common)
- Set up proper ProGuard rules
- Configure build variants

Generate the necessary gradle files, manifest configurations, and base package structure.
```

### ✅ Verification Checklist
- [ ] All 4 modules created with proper gradle files
- [ ] Version catalog (libs.versions.toml) configured
- [ ] Hilt dependencies added and configured
- [ ] Package structure follows Clean Architecture
- [ ] Base classes created in common module
- [ ] Build variants configured
- [ ] ProGuard rules set up
- [ ] Project compiles without errors

### 📊 Progress Update Command
```
Update tasks.md Phase 1 progress: Mark tasks 1.1-1.10 as completed. Update progress to show "Phase 1: ✅ Foundation (10/10 tasks)"
```

---

## 🏗️ Phase 2: Domain Layer

### 🤖 Implementation Prompt
```
Now let's implement the Domain layer for the Smart Expense Tracker.

Create the following in the domain module:

1. **Core Models**:
   - Expense data class (id, title, amount, category, notes, receiptImageUrl, timestamp)
   - ExpenseCategory enum (STAFF, TRAVEL, FOOD, UTILITY)
   - WeeklyReport data class for analytics

2. **Repository Interface**:
   - ExpenseRepository with methods for CRUD operations
   - Methods: addExpense, getExpenses, getTotalSpentToday, getWeeklyReport

3. **Use Cases**:
   - AddExpenseUseCase with validation
   - GetExpensesUseCase with filtering by date/category
   - GetTotalSpentTodayUseCase
   - GetWeeklyReportUseCase

4. **Utilities**:
   - DateUtils for date operations
   - Input validation logic

Ensure all classes follow Clean Architecture principles with proper error handling using Result wrapper.
```

### ✅ Verification Checklist
- [ ] Expense model matches requirements (all fields present)
- [ ] ExpenseCategory enum has all 4 categories
- [ ] WeeklyReport model supports analytics needs
- [ ] ExpenseRepository interface defines all CRUD operations
- [ ] Use cases implement proper business logic
- [ ] Input validation covers amount > 0, title non-empty
- [ ] DateUtils handles date filtering correctly
- [ ] All classes use Result wrapper for error handling
- [ ] No Android dependencies in domain layer

### 📊 Progress Update Command
```
Update tasks.md Phase 2 progress: Mark tasks 2.1-2.11 as completed. Update progress to show "Phase 2: ✅ Domain Layer (11/11 tasks)"
```

---

## 🗃️ Phase 3: Data Layer

### 🤖 Implementation Prompt
```
Implement the Data layer for expense management:

1. **Data Sources**:
   - ExpenseInMemoryDataSource with mock data
   - ExpenseDto for data transfer
   - ExpenseMapper for domain/dto conversion
   - Optional: Room database setup

2. **Repository Implementation**:
   - ExpenseRepositoryImpl implementing domain interface
   - Error handling and data mapping
   - Thread-safe operations

3. **Dependency Injection**:
   - DataModule for Hilt configuration
   - Provide data sources and repository

4. **Mock Data**:
   - Generate sample expenses across different categories
   - Include expenses from last 7 days for testing

Ensure the data layer is completely isolated and testable.
```

### ✅ Verification Checklist
- [ ] ExpenseInMemoryDataSource stores and retrieves data correctly
- [ ] ExpenseDto mirrors domain model structure
- [ ] ExpenseMapper converts between dto and domain models
- [ ] ExpenseRepositoryImpl implements all interface methods
- [ ] DataModule provides all dependencies correctly
- [ ] Mock data includes all categories and date ranges
- [ ] Thread safety implemented for concurrent access
- [ ] Error scenarios handled appropriately
- [ ] Data persistence works (in-memory or Room)

### 📊 Progress Update Command
```
Update tasks.md Phase 3 progress: Mark tasks 3.1-3.8 as completed. Update progress to show "Phase 3: ✅ Data Layer (8/8 tasks)"
```

---

## 📱 Phase 4: Expense Entry Screen

### 🤖 Implementation Prompt
```
Create the Expense Entry screen with Jetpack Compose:

1. **UI Components**:
   - ExpenseEntryScreen with modern Material 3 design
   - Input fields: Title, Amount (₹), Category dropdown, Notes
   - Optional receipt image upload placeholder
   - Real-time "Total Spent Today" display at top
   - Submit button with loading states

2. **ViewModel & State**:
   - ExpenseEntryViewModel with StateFlow
   - ExpenseEntryUiState for form state management
   - Real-time validation (amount > 0, title required)
   - Success/error handling with Toast messages

3. **UX Features**:
   - Form validation with error messages
   - Loading states during submission
   - Success animation after adding expense
   - Keyboard handling and focus management

Follow Material 3 design principles and ensure accessibility.
```

### ✅ Verification Checklist
- [ ] All required input fields present and functional
- [ ] Category dropdown shows all 4 categories
- [ ] Real-time total spent today updates correctly
- [ ] Form validation works (amount > 0, title required)
- [ ] Submit button shows loading state during operation
- [ ] Success toast appears after successful submission
- [ ] Error handling displays appropriate messages
- [ ] UI follows Material 3 design guidelines
- [ ] Screen is accessible (content descriptions, etc.)
- [ ] Navigation works correctly
- [ ] ViewModel properly manages state
- [ ] Memory leaks avoided (proper lifecycle handling)

### 📊 Progress Update Command
```
Update tasks.md Phase 4 progress: Mark tasks 4.1-4.13 as completed. Update progress to show "Phase 4: ✅ Expense Entry (13/13 tasks)"
```

---

## 📋 Phase 5: Expense List Screen

### 🤖 Implementation Prompt
```
Build the Expense List screen with filtering and grouping:

1. **UI Components**:
   - ExpenseListScreen with LazyColumn for expense items
   - Expense item cards showing title, amount, category, date
   - Date filter with calendar picker
   - Category/time grouping toggle
   - Empty state and loading indicators

2. **ViewModel & State**:
   - ExpenseListViewModel managing list state
   - ExpenseListUiState with filtering options
   - Real-time filtering by date and category
   - Grouping logic (by category or time)

3. **Advanced Features**:
   - Search functionality
   - Pull-to-refresh
   - Smooth scrolling animations
   - Total count and amount display

Ensure good performance with lazy loading and proper state management.
```

### ✅ Verification Checklist
- [ ] Expense list displays all expenses correctly
- [ ] Date filtering works with calendar picker
- [ ] Category filtering shows correct results
- [ ] Grouping toggle works (by category/time)
- [ ] Search functionality filters expenses
- [ ] Empty state shows when no expenses found
- [ ] Loading indicators appear during data fetch
- [ ] Pull-to-refresh updates the list
- [ ] Total count and amount calculated correctly
- [ ] Smooth scrolling performance
- [ ] Memory efficient (lazy loading)
- [ ] Navigation to other screens works

### 📊 Progress Update Command
```
Update tasks.md Phase 5 progress: Mark tasks 5.1-5.13 as completed. Update progress to show "Phase 5: ✅ Expense List (13/13 tasks)"
```

---

## 📊 Phase 6: Expense Reports Screen

### 🤖 Implementation Prompt
```
Create the Expense Reports screen with analytics:

1. **UI Components**:
   - ExpenseReportScreen with 7-day overview
   - Daily totals cards
   - Category-wise breakdown
   - Bar/line charts using MPAndroidChart
   - Export functionality UI (PDF/CSV simulation)

2. **ViewModel & State**:
   - ExpenseReportViewModel for analytics
   - ExpenseReportUiState managing report data
   - Chart data processing and formatting
   - Export operation handling

3. **Analytics Features**:
   - Weekly spending trends
   - Category comparison
   - Spending insights and patterns
   - Mock export to demonstrate functionality

Focus on data visualization and user insights.
```

### ✅ Verification Checklist
- [ ] 7-day summary displays correctly
- [ ] Daily totals show accurate amounts
- [ ] Category breakdown shows all categories
- [ ] Charts render properly with real data
- [ ] Chart interactions work (tap, zoom if applicable)
- [ ] Export simulation triggers successfully
- [ ] Share intent works for export
- [ ] Analytics calculations are accurate
- [ ] Loading states during report generation
- [ ] Error handling for no data scenarios
- [ ] Performance optimized for chart rendering
- [ ] Responsive design on different screen sizes

### 📊 Progress Update Command
```
Update tasks.md Phase 6 progress: Mark tasks 6.1-6.13 as completed. Update progress to show "Phase 6: ✅ Reports (13/13 tasks)"
```

---

## 🧭 Phase 7: Navigation & Integration

### 🤖 Implementation Prompt
```
Implement navigation and screen integration:

1. **Navigation Setup**:
   - Compose Navigation with proper routes
   - Navigation destinations enum/sealed class
   - Bottom navigation or navigation drawer
   - Deep linking support

2. **Screen Integration**:
   - Proper data flow between screens
   - Shared ViewModels where appropriate
   - Navigation arguments passing
   - Back stack management

3. **Testing Integration**:
   - End-to-end workflow testing
   - Navigation scenario testing
   - Data consistency across screens

Ensure smooth user experience and proper app architecture.
```

### ✅ Verification Checklist
- [ ] All screens accessible through navigation
- [ ] Navigation arguments pass correctly
- [ ] Back navigation works properly
- [ ] Deep linking functional (if implemented)
- [ ] Bottom navigation/drawer works smoothly
- [ ] Data updates reflect across all screens
- [ ] Navigation state preserved during configuration changes
- [ ] No memory leaks during navigation
- [ ] Smooth transitions between screens
- [ ] Navigation follows Android design guidelines

### 📊 Progress Update Command
```
Update tasks.md Phase 7 progress: Mark tasks 7.1-7.8 as completed. Update progress to show "Phase 7: ✅ Navigation (8/8 tasks)"
```

---

## 🎨 Phase 8: UI/UX Polish

### 🤖 Implementation Prompt
```
Polish the UI/UX with theming and animations:

1. **Material 3 Theming**:
   - Custom color schemes
   - Typography system
   - Shape theming
   - Dark/light theme support

2. **Animations**:
   - Screen transition animations
   - Entry animations for new expenses
   - Loading skeleton screens
   - Micro-interactions

3. **Responsive Design**:
   - Different screen size support
   - Orientation handling
   - Accessibility improvements

4. **Theme Switching**:
   - Dynamic theme toggle
   - Theme persistence
   - Smooth theme transitions

Focus on creating a delightful user experience with smooth animations and beautiful design.
```

### ✅ Verification Checklist
- [ ] Material 3 design system implemented
- [ ] Custom color schemes applied
- [ ] Typography follows design guidelines
- [ ] Dark theme works correctly
- [ ] Theme switching is smooth and persistent
- [ ] Screen transitions are fluid
- [ ] Loading animations provide good feedback
- [ ] Micro-interactions enhance UX
- [ ] Responsive on different screen sizes
- [ ] Accessibility features implemented
- [ ] Consistent spacing and layout
- [ ] Performance remains smooth with animations

### 📊 Progress Update Command
```
Update tasks.md Phase 8 progress: Mark tasks 8.1-8.12 as completed. Update progress to show "Phase 8: ✅ UI Polish (12/12 tasks)"
```

---

## 🧪 Phase 9: Testing & Quality

### 🤖 Implementation Prompt
```
Implement comprehensive testing strategy:

1. **Unit Tests**:
   - Use case testing with mock data
   - Repository implementation tests
   - ViewModel testing with test coroutines
   - Utility function tests

2. **UI Tests**:
   - Compose UI testing
   - Navigation testing
   - User interaction scenarios
   - Form validation testing

3. **Integration Tests**:
   - End-to-end workflow testing
   - Data persistence testing
   - Multi-screen data flow testing

4. **Quality Assurance**:
   - Code coverage analysis
   - Performance testing
   - Memory leak detection
   - Edge case handling

Aim for >80% test coverage and robust error handling.
```

### ✅ Verification Checklist
- [ ] All use cases have unit tests
- [ ] Repository tests cover all operations
- [ ] ViewModel tests include state changes
- [ ] UI tests cover user interactions
- [ ] Navigation tests verify screen flows
- [ ] Form validation tests cover all scenarios
- [ ] Integration tests verify end-to-end workflows
- [ ] Test coverage >80%
- [ ] Performance tests show acceptable results
- [ ] Memory leak tests pass
- [ ] Error scenarios properly tested
- [ ] Edge cases handled correctly

### 📊 Progress Update Command
```
Update tasks.md Phase 9 progress: Mark tasks 9.1-9.12 as completed. Update progress to show "Phase 9: ✅ Testing (12/12 tasks)"
```

---

## 🚀 Phase 10: Deployment & Documentation

### 🤖 Implementation Prompt
```
Prepare final deployment and documentation:

1. **App Preparation**:
   - Generate signed APK/AAB
   - Optimize app performance
   - Test on multiple devices
   - Handle edge cases and error scenarios

2. **AI Usage Documentation**:
   - Document 3-5 sentence AI usage summary
   - Create prompt logs with key iterations
   - Add code comments explaining AI-generated parts
   - Update README with setup instructions

3. **Final Deliverables**:
   - Screenshots of all screens
   - Demo video (optional)
   - Source code repository
   - APK download link

4. **Submission Package**:
   - Organized project structure
   - Complete documentation
   - All requirements verified

Ensure all assignment requirements are met and documented.
```

### ✅ Verification Checklist
- [ ] Signed APK/AAB generated successfully
- [ ] App tested on physical devices
- [ ] Performance optimized
- [ ] All edge cases handled
- [ ] AI usage documented (3-5 sentences)
- [ ] Prompt logs created
- [ ] Code comments added
- [ ] README updated with setup instructions
- [ ] Screenshots taken for all screens
- [ ] Source code repository organized
- [ ] All assignment requirements verified
- [ ] Submission package prepared

### 📊 Progress Update Command
```
Update tasks.md Phase 10 progress: Mark tasks 10.1-10.13 as completed. Update progress to show "Phase 10: ✅ Deployment (13/13 tasks)"
```

---

## 🤖 AI Integration Verification

### 📋 AI Usage Checklist
Throughout all phases, verify:
- [ ] AI tools used for code generation
- [ ] AI feedback incorporated for UI design
- [ ] AI optimization suggestions implemented
- [ ] Prompt iterations documented
- [ ] AI-generated code properly commented
- [ ] AI usage summary written
- [ ] Key prompts and retries logged

### 📊 Final Progress Update Command
```
Update tasks.md with final completion status:
- All phases marked as ✅ completed
- Total progress: 125/125 tasks completed
- AI integration tasks: 12/12 completed
- Project status: ✅ COMPLETED
```

---

## 🎯 Using These Prompts

1. **Copy the implementation prompt** for the current phase
2. **Use with your preferred AI tool** (ChatGPT, Cursor, Copilot, etc.)
3. **Verify implementation** using the checklist
4. **Update progress** in tasks.md using the provided command
5. **Move to next phase** once all criteria are met

Each prompt is designed to be comprehensive yet specific enough to generate high-quality, requirement-compliant code.

# Smart Expense Tracker - Implementation Guide

## 📍 For New Chat Sessions

This document helps AI assistants understand the project structure and continue development efficiently across multiple chat sessions.

## 📁 Project Documentation Structure

```
context/
├── requirements/               # What to build
│   ├── original_requirements.md   # Assignment specifications
│   └── Smart Expense Tracker Assignment.pdf
├── docs/
│   └── architecture.md           # System design & module structure
└── execution/                    # How to build
    ├── tasks.md                  # Task breakdown & progress tracking
    ├── prompts.md               # AI development prompts per phase
    └── implementation.md        # This file
```

## 🎯 How to Use This Project

### 1. **Check Current Progress**
```bash
# Read task status
@context/execution/tasks.md
```
- Look for phase completion status (⏳ Pending, 🔄 In Progress, ✅ Completed)
- Find uncompleted tasks in current phase
- Update progress as you complete tasks

### 2. **Understand Requirements**
```bash
# Read project specifications
@context/requirements/original_requirements.md
@context/docs/architecture.md
```

### 3. **Follow Development Process**
```bash
# Get AI prompts for current phase
@context/execution/prompts.md
```
- Find your current phase prompt
- Copy implementation prompt to AI tool
- Use verification checklist to validate completion
- Update tasks.md progress using provided commands

### 4. **Follow Code Standards**
```bash
# Project rules auto-apply in Cursor
.cursor/rules/
```
- Rules automatically guide AI code generation
- Enforce Clean Architecture principles
- Maintain consistent naming and structure

## 🔄 Multi-Chat Workflow

### **Starting New Chat Session:**
1. Read `tasks.md` to see current progress
2. Identify next incomplete phase
3. Read corresponding prompt from `prompts.md`
4. Check `.cursor/rules/` are active (if using Cursor)
5. Begin implementation

### **Before Ending Chat Session:**
1. Update `tasks.md` with completed items
2. Mark current phase status
3. Document any blockers or decisions
4. Commit progress to git

### **Context for AI:**
- **Project**: Android expense tracker using Clean Architecture
- **Tech Stack**: Kotlin, Jetpack Compose, Hilt, Room, MVVM
- **Modules**: app (UI) → domain (business) → data (persistence) ← common (shared)
- **Screens**: Expense Entry, Expense List, Expense Reports

## 📊 Current Status Check

**Always start by reading:**
1. `tasks.md` - What's completed vs pending
2. Current module/phase being worked on
3. Any blockers or incomplete verification items

**Example Status Check:**
```
Phase 1: ✅ Foundation (10/10 tasks) 
Phase 2: 🔄 Domain Layer (3/11 tasks)
Current: Working on Use Cases implementation
```

## 🤖 AI Integration Rules

- **Document AI usage**: Add `// AI-generated:` comments
- **Verify output**: Use prompts.md verification checklists  
- **Follow architecture**: Respect module dependencies
- **Test coverage**: Aim for >80% on critical paths

## 🚀 Quick Start Commands

```bash
# Check what to do next
grep -A 5 "🔄 In Progress\|⏳ Pending" context/execution/tasks.md

# Get current phase prompt
# Look for first incomplete phase in prompts.md

# Update progress after completion
# Use progress update commands from prompts.md
```

## 📝 Important Notes

- **Never skip verification** - Use checklists before marking tasks complete
- **Update progress frequently** - Keep tasks.md current for next session
- **Follow Clean Architecture** - Rules enforce this automatically
- **AI assistance required** - This is an AI-first development project
- **Test as you go** - Don't defer testing to the end

---

**Ready to continue? Check tasks.md for your next phase!**

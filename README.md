# 🖥️ JavaHelperApp

A simple yet powerful **Java CLI application** that answers **Java-related questions instantly**.  
It uses a structured `knowledge.json` file as its knowledge base and provides:

✅ Instant Q&A  
✅ History of asked questions  
✅ Export to **CSV/JSON**  
✅ Expandable knowledge base (just add more questions!)  

---

## 📂 Project Structure
```
JavaHelperApp/
├─ pom.xml
├─ README.md
├─ src/
│   ├─ main/
│   │    ├─ java/
│   │    │    └─ com/javahelper/
│   │    │    ├─ Main.java
│   │    │    ├─ qa/
│   │    │    │ ├─ QAEngine.java
│   │    │    │ └─ KnowledgeBase.java
│   │    │    └─ utils/
│   │    │    └─ ConsoleUtils.java
│ │ └─ resources/
│ │ └─ knowledge.json
└─ src/test/
     └─ java/
         └─ com/javahelper/
         ├─ QAEngineTest.java
         └─ ConsoleUtilsTest.java
```

---

## 🚀 Getting Started

### 1️⃣ Clone the repository
```bash
git clone https://github.com/yourusername/JavaHelperApp.git
cd JavaHelperApp
```
### 2️⃣ Build with Maven
```
mvn clean compile
```
3️⃣ Run the app
```
mvn exec:java -Dexec.mainClass="com.javahelper.Main"

```
## 📖 Usage

- Once running, you’ll see a menu:
```
=== Java Helper CLI ===

Menu:
1. Ask a Java Question
2. Show Q&A History
3. Export History CSV/JSON
0. Exit
```
### Example Usage
```
Choose option: 1
Enter your question: What is Java?
Answer: Java is a high-level, object-oriented programming language developed by Sun Microsystems (now Oracle).
```

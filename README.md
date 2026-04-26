# Compiler Design PBL (Modules 1–6) — Java Mini-Compiler

This repository contains a semester PBL implementation of a **mini compiler pipeline** built in Java, organized module-by-module.

## Modules

- **Module 1: Lexical Analyzer** — Tokenizes a small input program (file-based or built-in demo).
- **Modules 2 & 3: Syntax Parser (Recursive Descent)** — Demonstrates expression parsing using recursive descent.
- **Modules 4 & 5: Optimizer**
  - **Module 4 (TAC Generator)** — Generates Three-Address Code (TAC) via strict post-order traversal on manually constructed ASTs.
  - **Module 5 (DAG Optimizer Prototype)** — Demonstrates Common Subexpression Elimination (CSE) on a sample basic block.
- **Module 6: Final Integration** — Simulates the full compiler pipeline and emits dummy x86-like assembly.

## Project Structure

- `Module_1_Lexical_Analyzer/`
- `Module_2_and_3_Syntax_Parser/`
- `Module_4_and_5_Optimizer/`
- `Module_6_FinalCompiler/`

## Requirements

- Java JDK 8+ (recommended: 17+)

## How to Compile (from repo root)

```bash
javac Module_1_Lexical_Analyzer\Lexer.java Module_2_and_3_Syntax_Parser\RecursiveDescentParser.java Module_4_and_5_Optimizer\ASTNode.java Module_4_and_5_Optimizer\TACGenerator.java Module_4_and_5_Optimizer\DAGOptimizer.java Module_6_FinalCompiler\AssemblyGenerator.java Module_6_FinalCompiler\MainCompiler.java
```

Note: compilation creates `*.class` files locally; they are ignored by git.

## How to Run

Run from the repo root after compiling:

### Module 1 — Lexer

Built-in demo input:

```bash
java -cp Module_1_Lexical_Analyzer Lexer
```

Tokenize a specific file:

```bash
java -cp Module_1_Lexical_Analyzer Lexer path\to\your_input.txt
```

Alternatively (inside the module folder):

```bash
cd Module_1_Lexical_Analyzer
javac Lexer.java
java Lexer
```

### Modules 2 & 3 — Recursive Descent Parser

```bash
java -cp Module_2_and_3_Syntax_Parser RecursiveDescentParser
```

### Module 4 — TAC Generator

```bash
java Module_4_and_5_Optimizer.TACGenerator
```

### Module 5 — DAG Optimizer

```bash
java Module_4_and_5_Optimizer.DAGOptimizer
```

### Module 6 — Full Pipeline

```bash
java Module_6_FinalCompiler.MainCompiler
```

## License

MIT License — see `LICENSE`.

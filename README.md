#🏸 Badminton Tournament System 
### A Java Console System Demonstrating Object-Oriented Programming (OOP)
![BADMINTON TOURNAMENT SYSTEM](https://github.com/ellemrsgn27/BadmintonTournamentSystem/blob/main/TriByteBTS.png)

## 📝 Description / Overview
<p align="justify">
The Badminton Tournament System is a Java console-based application designed to efficiently organize and manage badminton competitions. Users can choose the tournament type between Single Elimination or Double Elimination and navigate a clear, interactive menu that offers the following options:
  
📝 Register Players

👀 View Players and Brackets

🏸 Start the Tournament

✏️ Update Player or Bracket Information

🗑️ Delete Players

The system demonstrates the four key Object-Oriented Programming (OOP) principles—encapsulation, inheritance, abstraction, and polymorphism—through its well-structured class design and method implementation. By integrating exception handling, arrays, and user-driven console interactions, the project provides a practical and engaging simulation of real-world tournament management, highlighting both functionality and good programming practice. 

## 🧠 OOP Concepts Applied
### 🔒 Encapsulation
Class fields such as `name` in Player, `teamName/player1/player2` in Team, and `participants` in Tournament are private. Access is controlled through getters and setters, ensuring data safety and validity.
### 🎭 Abstraction
Abstract classes like Participant and Tournament hide complex logic.
Only essential methods are exposed, such as:
- `getDisplayName()` in Participant
- `createParticipantFromInput()` in Tournament
The system can work with different types of participants without needing to know their internal details.

  

---
title: "Control Flow: Conditionals and Loops"
track: "coding"
order: 2
summary: "Make decisions and repeat code with if/else and loops"
---

# Control Flow: Conditionals and Loops

Control flow lets your program make decisions and repeat actions based on conditions.

## Conditionals (if/elif/else)

```python
age = 18

if age >= 18:
    print("You are an adult")
elif age >= 13:
    print("You are a teenager")
else:
    print("You are a child")
```

### Comparison Operators

| Operator | Meaning |
|----------|---------|
| `==` | Equal to |
| `!=` | Not equal to |
| `>` | Greater than |
| `<` | Less than |
| `>=` | Greater than or equal |
| `<=` | Less than or equal |

### Logical Operators

```python
has_ticket = True
is_vip = False

if has_ticket and is_vip:
    print("VIP access granted")
elif has_ticket:
    print("Standard access granted")
else:
    print("Access denied")
```

## Loops

### For Loops

```python
# Loop over a range
for i in range(5):
    print(f"Count: {i}")

# Loop over a list
fruits = ["apple", "banana", "cherry"]
for fruit in fruits:
    print(f"I like {fruit}")
```

### While Loops

```python
count = 0
while count < 3:
    print(f"Count is {count}")
    count += 1  # Increment
```

## Practice Exercise

```python
# Print even numbers from 1 to 10
for n in range(1, 11):
    if n % 2 == 0:
        print(f"{n} is even")

# Simple guessing game
secret = 7
guess = 0
while guess != secret:
    guess = int(input("Guess a number 1-10: "))
    if guess < secret:
        print("Too low!")
    elif guess > secret:
        print("Too high!")
print("You got it!")
```

## Summary

Conditionals let your code make decisions. Loops let you repeat code efficiently. These are the building blocks of all programs. Next, we'll learn about functions.

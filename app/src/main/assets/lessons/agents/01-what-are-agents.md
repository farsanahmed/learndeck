---
title: "What Are AI Agents?"
track: "agents"
order: 1
summary: "Introduction to AI agents, how they differ from chatbots, and core concepts"
---

# What Are AI Agents?

An **AI agent** is an autonomous system that can perceive its environment, make decisions, and take actions to achieve specific goals. Unlike simple chatbots, agents can use tools, plan multi-step tasks, and operate independently.

## Key Characteristics

| Feature | Chatbot | AI Agent |
|---------|---------|----------|
| Autonomy | Reactive only | Proactive & goal-driven |
| Tools | None | Can use APIs, search, code |
| Memory | Short-term | Long-term + working memory |
| Planning | Single response | Multi-step reasoning |

## Core Components

```python
class Agent:
    def __init__(self, goal, tools, memory):
        self.goal = goal
        self.tools = tools      # Functions it can call
        self.memory = memory    # Past interactions
    
    def run(self):
        while not self.goal_achieved():
            action = self.plan()
            result = self.execute(action)
            self.memory.store(result)
```

## Example: Research Agent

1. **Goal**: "Summarize the latest Python 3.12 features"
2. **Plan**: Search web → Read docs → Extract key points → Write summary
3. **Tools**: Web search, HTTP requests, text extraction
4. **Output**: Structured summary with citations

## Why Agents Matter

- **Automation**: Handle complex workflows without human supervision
- **Reasoning**: Break down problems into manageable steps
- **Tool Use**: Extend capabilities beyond training data
- **Adaptability**: Learn from feedback and improve

## Summary

AI agents combine LLMs with tools, memory, and planning to accomplish complex tasks autonomously. Next, we'll explore prompt engineering and tool integration.

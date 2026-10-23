---
title: "Prompt Engineering & Tool Use"
track: "agents"
order: 2
summary: "Write effective prompts and integrate tools for capable agents"
---

# Prompt Engineering & Tool Use

Effective agents need well-crafted prompts and reliable tool integration. This lesson covers both.

## Prompt Engineering Principles

### 1. Be Specific
```python
# ❌ Vague
"Write a summary"

# ✅ Specific
"Summarize the key changes in Python 3.12 in 3 bullet points, 
 focusing on performance improvements and new syntax features"
```

### 2. Provide Context
```python
system_prompt = """
You are a senior Python developer helping a beginner.
Explain concepts clearly with code examples.
Always include type hints.
"""
```

### 3. Use Structured Output
```python
prompt = """
Extract the following as JSON:
{
  "title": "string",
  "features": ["string"],
  "release_date": "YYYY-MM-DD"
}
"""
```

## Tool Integration

Agents use tools via function calling. Define tools clearly:

```python
tools = [
    {
        "name": "web_search",
        "description": "Search the web for current information",
        "parameters": {
            "type": "object",
            "properties": {
                "query": {"type": "string"},
                "max_results": {"type": "integer", "default": 5}
            },
            "required": ["query"]
        }
    },
    {
        "name": "run_python",
        "description": "Execute Python code and return output",
        "parameters": {
            "type": "object",
            "properties": {
                "code": {"type": "string"}
            },
            "required": ["code"]
        }
    }
]
```

## Agent Loop Pattern

```python
def agent_loop(goal, tools, max_steps=10):
    messages = [{"role": "system", "content": system_prompt}]
    messages.append({"role": "user", "content": goal})
    
    for step in range(max_steps):
        response = llm(messages, tools=tools)
        
        if response.tool_calls:
            for call in response.tool_calls:
                result = execute_tool(call)
                messages.append({"role": "tool", "content": result})
        else:
            return response.content  # Final answer
    
    return "Max steps reached"
```

## Best Practices

1. **Validate tool outputs** — Check for errors before proceeding
2. **Limit steps** — Prevent infinite loops with `max_steps`
3. **Log everything** — Debug by inspecting the message history
4. **Handle failures** — Retry or fallback when tools fail

## Summary

Good prompts are specific, contextual, and request structured output. Tools extend agent capabilities—define them clearly and handle errors gracefully. Next: building multi-agent systems.

# Macros

- Macros are supported for string, ref, array values, and named array values. Named array keys are not supported.
- Macro syntax is `${macro-name(value)}`
- Macros are not support mixed with literal values.
- Errors evaluating macros will result in RPC request failure with `500` status code.
- Unknown macros will result in an error and a `400` status code.

Good!

```
"parameters": {
  { "string": "${local-fileman-date(2005-01-21T07:57:00Z)}"
}

"parameters": {
  { "array": [ "true", ${local-fileman-date(2005-01-21T07:57:00Z)}", "", "patient" ]
}

"parameters": {
  {
    "namedArray": {
      "patient":"${local-fileman-date(2005-01-21T07:57:00Z)}",
      "domain":"vitals"
    }
  }
}
```

Not Good

```
"parameters": {
  { "string": "Today is ${local-fileman-date(2005-01-21T07:57:00Z)}"
}

"parameters": {
  {
    "namedArray": {
      "${local-fileman-date(2005-01-21T07:57:00Z)}":"patient",
      "domain":"vitals"
    }
  }
}
```

## Supported Macros

### `${local-fileman-date(ISO-8601)}`

Replace a ISO-8601 date value with a localized fileman date for a targeted Vista site

## What's under the hood?

- `MacroProcessorFactory` is autowired with `Macro`instances
- `VistalinkRpcInvokerFactory` is autowired with a `MacroProcessorFactory`. It will pass this factory along as it creates `VistalinkRpcInvokers`
- `VistalinkRpcInvoker` will use the factory to create a `MacroProcessor` based on a `MacroExecutionContext` that is specific to itself.
- `VistalinkRpcInvoker` will invoke the `MacroProcessor` to evaluate each value.
  - If the value is a macro it is processed and a new value is returned.
  - If the value is not a macro, it is returned unchanged.

#### The `MacroProcessor`

- Looks for macro syntax in evaluated values.
- Maps macros to their `Macro` implementation.
- Invokes the `Macro.evaluate` and returns the results.

#### The `MacroExecutionContext`

- Provides a macro-free place to execute RPCs. This allows Macros to use RPCs during the evaluation process.

### The `Macro`

- `Macro` implementations provide a `name` that is used in for the macro in RPC requests, e.g. `local-fileman-date`
- Implementations may interact with RPCs through a provided execution context which allows low-level invocation.
  - Connection, authentication, and error handling are performed by the `MacroExecutionContext` itself.

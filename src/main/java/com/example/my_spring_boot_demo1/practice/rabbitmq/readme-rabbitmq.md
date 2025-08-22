## Test Rabbit MQ Exchange behavior


1. DirectExchange（精準匹配）
```java
@Bean
public DirectExchange exchange() {
    return new DirectExchange(EXCHANGE_NAME);
}
```
- bindingA -> `route.A`
- bindingB -> `route.B`
- `GET /send/route.A/helloA` → 只有 Queue A 收到

2. FanoutExchange（廣播）
```java
@Bean
public FanoutExchange exchange() {
    return new FanoutExchange(EXCHANGE_NAME);
}

@Bean
public Binding bindingA(Queue queueA, FanoutExchange exchange) {
    return BindingBuilder.bind(queueA).to(exchange);
}

@Bean
public Binding bindingB(Queue queueB, FanoutExchange exchange) {
    return BindingBuilder.bind(queueB).to(exchange);
}
```
- 不管 `routingKey` 塞什麼，A 跟 B 都會收到

3. TopicExchange（模糊匹配）
- pattern:
  - `#` 表示任何字 (0或多)
  - `*` 表示任何"一個"字

4. HeadersExchange（依 header 條件）
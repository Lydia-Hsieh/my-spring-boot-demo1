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
```java
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingTopicA(Queue queueA, TopicExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange).with("route.*");
    }

    @Bean
    public Binding bindingTopicB(Queue queueB, TopicExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).with("route.#");
    }
```
- pattern:
  - `#` 表示任何字 (0或多)
  - `*` 表示任何"一個"字
- `GET /send/route.one/hello` → Queue A、B 都收到
- `GET /send/route.one.two/hello` → 只有 Queue B 收到

4. HeadersExchange（依 header 條件）
- config
```java
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingHeadersA(Queue queueA, HeadersExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange).where("type").matches("A");
    }

    @Bean
    public Binding bindingHeadersB(Queue queueB, HeadersExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).where("type").matches("B");
    }
```
- producer
```java
  MessageProperties props = new MessageProperties();
  props.setHeader("type", routingKey);
  Message msg = new Message(message.getBytes(), props);
  rabbitTemplate.convertAndSend(
          RabbitConfig.EXCHANGE_NAME,
          "",
          msg
  );
```
- Producer 送訊息時要帶 headers (範例把path的key當作header放進去了)
- `GET /send/A/helloA` → 只有 Queue A 收到
- `GET /send/B/helloB` → 只有 Queue B 收到
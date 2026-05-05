# Java & Spring Boot — Field Reference
> Written from real questions asked while building the Uniwallet Integration API at ITC, Accra.
> Every concept here was touched, broken, fixed, or questioned during that build.

---

## Table of Contents

1. [Java Fundamentals](#1-java-fundamentals)
   - Variables & Types
   - Conditionals
   - Loops
   - Methods (Functions)
   - Classes & Objects
   - Interfaces
   - Inheritance
   - Access Modifiers
   - Generics & Collections
   - Exception Handling
   - Enums
   - Casting & Type Conversion
   - Null Handling & Optional
   - Lambdas & Method References
   - Static vs Instance
   - Annotations
2. [Spring Boot Core](#2-spring-boot-core)
   - What is Spring Boot?
   - Project Structure
   - Entry Point
   - Dependency Injection
   - Beans & Configuration
   - Controllers
   - Services
   - application.yml & Environment Variables
   - ResponseEntity
3. [Lombok](#3-lombok)
4. [HTTP in Spring Boot](#4-http-in-spring-boot)
   - RestTemplate
   - HttpHeaders
   - HttpEntity
   - Making API Calls
5. [DTOs](#5-dtos)
6. [Cross-Cutting Concerns](#6-cross-cutting-concerns)
   - CORS
   - Security Headers
   - Rate Limiting (Bucket4j)
   - Global Exception Handling
7. [Environments & Profiles](#7-environments--profiles)
8. [Frequently Asked Questions](#8-frequently-asked-questions)

---

## 1. Java Fundamentals

### Variables & Types

Java is **statically typed** — you must declare the type of every variable.

```java
int age = 21;
double height = 6.2;
String name = "Kingsley";
boolean isActive = true;
char grade = 'A';

// Type inference (Java 10+)
var score = 100; // compiler infers int
var label = "hello"; // compiler infers String
```

**Primitive types** (lowercase): `int`, `double`, `float`, `long`, `boolean`, `char`, `byte`, `short`
**Reference types** (uppercase): `String`, `Integer`, `Double`, `Boolean` — these are objects and can be `null`

---

### Conditionals

```java
// if / else if / else
if (age > 18) {
    System.out.println("Adult");
} else if (age == 18) {
    System.out.println("Just 18");
} else {
    System.out.println("Minor");
}

// Ternary
String status = age > 18 ? "Adult" : "Minor";

// Switch (modern Java 14+)
switch (network) {
    case "MTN" -> System.out.println("MTN Ghana");
    case "VODAFONE" -> System.out.println("Vodafone");
    default -> System.out.println("Unknown network");
}

// Switch expression (returns a value)
String label = switch (responseCode) {
    case "01" -> "Success";
    case "100" -> "Failed";
    default -> "Unknown";
};
```

---

### Loops

```java
// Standard for loop
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}

// For-each (use this when you don't need the index)
List<String> networks = List.of("MTN", "VODAFONE", "AIRTELTIGO");
for (String network : networks) {
    System.out.println(network);
}

// While
int i = 0;
while (i < 5) {
    System.out.println(i);
    i++;
}

// Do-while (runs at least once)
do {
    System.out.println(i);
    i++;
} while (i < 5);
```

---

### Methods (Functions)

In Java, functions live inside classes and are called **methods**.

```java
// With return type
public int add(int a, int b) {
    return a + b;
}

// Void (no return)
public void greet(String name) {
    System.out.println("Hello " + name);
}

// Static method (no object needed to call it)
public static String generateRef() {
    return UUID.randomUUID().toString();
}

// Calling methods
int result = add(3, 5);           // instance method
String ref = generateRef();        // static method (MyClass.generateRef())
```

**Method anatomy:**
```
[access modifier] [static?] [return type] [method name]([parameters]) { body }
     public          static      String       generateRef       ()       { ... }
```

---

### Classes & Objects

A class is a blueprint. An object is an instance of that blueprint.

```java
public class Transaction {
    // Fields (state)
    private String refNo;
    private double amount;
    private String status;

    // Constructor (called when you do `new Transaction(...)`)
    public Transaction(String refNo, double amount) {
        this.refNo = refNo;
        this.amount = amount;
        this.status = "PENDING";
    }

    // Methods (behaviour)
    public void complete() {
        this.status = "SUCCESS";
    }

    // Getter
    public String getRefNo() { return refNo; }

    // Setter
    public void setAmount(double amount) { this.amount = amount; }
}

// Creating an object
Transaction tx = new Transaction("REF001", 10.00);
tx.complete();
System.out.println(tx.getRefNo()); // REF001
```

**Why `this`?** — It refers to the current object. Used to distinguish between a field and a parameter with the same name.

---

### Interfaces

An interface is a **contract** — it says "any class that implements me must have these methods."

```java
public interface Payable {
    void pay(double amount);
    String getStatus();
}

public class MobileMoneyPayment implements Payable {
    @Override
    public void pay(double amount) {
        System.out.println("Paying via mobile money: " + amount);
    }

    @Override
    public String getStatus() {
        return "PROCESSED";
    }
}
```

A class can implement **multiple interfaces** but can only extend one class.

---

### Inheritance

```java
public class Animal {
    public void speak() {
        System.out.println("...");
    }
}

public class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("Woof");
    }
}

Dog dog = new Dog();
dog.speak(); // Woof
```

`extends` = inherit from a class
`implements` = fulfill an interface contract

---

### Access Modifiers

| Modifier | Accessible From |
|----------|----------------|
| `public` | Anywhere |
| `private` | Same class only |
| `protected` | Same package + subclasses |
| *(default/none)* | Same package only |

```java
public class BankAccount {
    private double balance;      // only this class can touch it
    public String accountNumber; // anyone can access it

    public double getBalance() { // controlled access via method
        return balance;
    }
}
```

---

### Generics & Collections

Generics let you write type-safe code. `List<String>` means "a list that only holds Strings."

```java
// List — ordered, allows duplicates
List<String> networks = new ArrayList<>();
networks.add("MTN");
networks.add("VODAFONE");
networks.get(0); // MTN

// Map — key-value pairs (like a JS object / Python dict)
Map<String, String> body = new HashMap<>();
body.put("msisdn", "233244300001");
body.put("network", "MTN");
body.get("msisdn"); // 233244300001

// Set — unique values only
Set<String> uniqueCodes = new HashSet<>();
uniqueCodes.add("01");
uniqueCodes.add("01"); // ignored — duplicate
```

**Why `Map` type but `HashMap` instance?**
```java
Map<String, String> body = new HashMap<>();
//  ^interface           ^implementation
```
You program to the interface (`Map`) so you can swap implementations (`LinkedHashMap`, `TreeMap`) without changing code that uses `body`. Same concept as TypeScript:
```typescript
const body: Record<string, string> = {};
```

---

### Exception Handling

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Math error: " + e.getMessage());
} catch (Exception e) {
    System.out.println("General error: " + e.getMessage());
} finally {
    System.out.println("Always runs — good for cleanup");
}

// Throwing exceptions
public void validate(String msisdn) {
    if (msisdn == null || msisdn.isEmpty()) {
        throw new IllegalArgumentException("msisdn is required");
    }
}

// Declaring checked exceptions
public void callApi() throws IOException {
    // ...
}
```

---

### Enums

```java
public enum TransactionStatus {
    PENDING, SUCCESS, FAILED, PROCESSING
}

TransactionStatus status = TransactionStatus.PENDING;

if (status == TransactionStatus.SUCCESS) {
    System.out.println("Done");
}

// With fields
public enum ResponseCode {
    SUCCESS("01", "Payment Successful"),
    FAILED("100", "Payment Failed");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
}
```

---

### Casting & Type Conversion

```java
// Widening (safe, automatic)
int x = 42;
double d = x; // int → double, no cast needed

// Narrowing (explicit cast, may lose data)
double pi = 3.14159;
int truncated = (int) pi; // 3 — decimal dropped

// String conversions
String s = String.valueOf(42);    // int → String: "42"
int n = Integer.parseInt("42");   // String → int: 42
double dbl = Double.parseDouble("3.14"); // String → double
```

---

### Null Handling & Optional

```java
// Classic null check
String name = null;
if (name != null) {
    System.out.println(name.toUpperCase());
}

// Optional — wraps a value that may or may not exist
Optional<String> name = Optional.of("Kingsley");
name.isPresent();        // true
name.get();              // "Kingsley"
name.orElse("Unknown");  // returns value or fallback

Optional<String> empty = Optional.empty();
empty.orElse("Default"); // "Default"

// From nullable source
Optional<String> maybe = Optional.ofNullable(someMethod());
```

---

### Lambdas & Method References

```java
// Lambda — anonymous function
List<String> names = List.of("Kingsley", "Feyi", "Koro");

// Without lambda
for (String n : names) {
    System.out.println(n);
}

// With lambda
names.forEach(n -> System.out.println(n));

// Method reference (even cleaner)
names.forEach(System.out::println);

// Lambda with logic
names.stream()
     .filter(n -> n.startsWith("K"))
     .forEach(System.out::println); // Kingsley, Koro
```

---

### Static vs Instance

```java
public class MathUtils {
    // Static — belongs to the class, no object needed
    public static double square(double n) {
        return n * n;
    }

    // Instance — belongs to an object
    public double multiply(double a, double b) {
        return a * b;
    }
}

// Static call
double sq = MathUtils.square(4); // 16.0 — no new MathUtils() needed

// Instance call
MathUtils utils = new MathUtils();
double product = utils.multiply(3, 4); // 12.0
```

---

### Annotations

Annotations are metadata that tell the compiler or framework how to treat a class/method/field. They start with `@`.

```java
@Override        // tells compiler this method overrides a parent method
@Deprecated      // marks something as outdated
@SuppressWarnings("unchecked") // suppresses compiler warnings

// Spring annotations (covered below)
@SpringBootApplication
@RestController
@Service
@Component
@Autowired
@Bean
@Configuration
```

---

## 2. Spring Boot Core

### What is Spring Boot?

Spring Boot is a framework built on top of the Spring Framework. It removes boilerplate configuration and bundles an embedded web server (Tomcat) so you can run your app as a plain Java process — no WAR file, no external server.

**Core Java has no HTTP server.** Spring Web brings Tomcat in as a dependency, turning your JAR into a runnable API server.

---

### Project Structure

```
src/
  main/
    java/com/itc/uniwallet/
      UniwalletApplication.java   ← entry point
      wallet/
        WalletController.java
        WalletService.java
        dto/
          NameEnquiryRequest.java
          NameEnquiryResponse.java
      config/
        AppConfig.java
        UniwalletConfig.java
        CorsConfig.java
    resources/
      application.yml             ← config
      application-local.yml       ← local secrets (gitignored)
  test/
    java/...
pom.xml                           ← dependencies (like package.json)
```

**Feature-first vs Layer-first:**
```
# Layer-first (traditional)          # Feature-first (NestJS-style)
controller/                           wallet/
  WalletController.java                 WalletController.java
service/                                WalletService.java
  WalletService.java                    dto/
dto/                                      NameEnquiryRequest.java
  NameEnquiryRequest.java
```
Both work. Feature-first scales better.

---

### Entry Point

```java
@SpringBootApplication
public class UniwalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniwalletApplication.class, args);
    }
}
```

`@SpringBootApplication` does three things:
- `@Configuration` — this class can define beans
- `@EnableAutoConfiguration` — auto-configure Spring based on classpath
- `@ComponentScan` — scan all packages under this class for `@Component`, `@Service`, `@RestController`, etc.

This is why you don't need to register anything manually. Spring finds everything automatically.

---

### Dependency Injection

DI means Spring creates and manages your objects (beans) and injects them where needed. You don't call `new WalletService()` — Spring does it.

```java
// WITHOUT DI — manual wiring
public class WalletController {
    private WalletService service = new WalletService(new RestTemplate(), new UniwalletConfig());
}

// WITH DI — Spring handles it
@RestController
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService; // Spring injects this
}
```

**How Spring knows what to inject:**
1. You annotate a class with `@Service`, `@Component`, `@Repository`, etc.
2. Spring registers it as a bean in its application context.
3. When another class needs it (via constructor or `@Autowired`), Spring injects it.

**Constructor injection** (preferred — `@RequiredArgsConstructor` generates the constructor):
```java
@Service
@RequiredArgsConstructor
public class WalletService {
    private final RestTemplate restTemplate; // injected
    private final UniwalletConfig config;    // injected
}
```

**`final` keyword here** means the field must be set at construction and can't be reassigned — enforcing that Spring always provides it.

---

### Beans & Configuration

A **bean** is any object managed by Spring's container.

```java
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // Spring manages this object
    }
}
```

`@Configuration` = this class declares beans
`@Bean` = this method produces a bean Spring will manage and inject wherever `RestTemplate` is needed

---

### Controllers

Controllers handle incoming HTTP requests. Same role as NestJS controllers.

```java
@RestController              // = @Controller + @ResponseBody (auto-serialize to JSON)
@RequestMapping("/api/wallet") // base path
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/name-enquiry")  // POST /api/wallet/name-enquiry
    public ResponseEntity<NameEnquiryResponse> nameEnquiry(
            @RequestBody NameEnquiryRequest request) {
        return ResponseEntity.ok(walletService.nameEnquiry(request));
    }

    @GetMapping("/status/{refNo}")  // GET /api/wallet/status/REF001
    public ResponseEntity<TransactionResponse> checkStatus(
            @PathVariable String refNo) {
        return ResponseEntity.ok(walletService.checkStatus(refNo));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
```

| Annotation | Purpose |
|-----------|---------|
| `@RestController` | Marks class as controller, auto-serializes responses to JSON |
| `@RequestMapping` | Sets base URL path |
| `@GetMapping` | Handles GET requests |
| `@PostMapping` | Handles POST requests |
| `@RequestBody` | Deserializes JSON body into a Java object |
| `@PathVariable` | Extracts `{variable}` from the URL path |
| `@RequestParam` | Extracts `?param=value` from query string |

---

### Services

Services contain business logic. Same role as NestJS services/providers.

```java
@Service                    // registers as a Spring bean
@RequiredArgsConstructor
@Slf4j                      // gives you a `log` object
public class WalletService {

    private final RestTemplate restTemplate;
    private final UniwalletConfig config;

    public NameEnquiryResponse nameEnquiry(NameEnquiryRequest req) {
        log.info("Name enquiry for msisdn: {}", req.getMsisdn());
        // ... logic
    }
}
```

**There is no `@Module` in Spring.** `@SpringBootApplication` scans everything. No wiring needed.

---

### application.yml & Environment Variables

```yaml
# application.yml — committed to git (no secrets)
server:
  port: 8080

spring:
  application:
    name: uniwallet

uniwallet:
  base-url: ${UNIWALLET_BASE_URL}      # reads from env var
  api-key: ${UNIWALLET_API_KEY}
  country: ${UNIWALLET_COUNTRY}
  transflow-id: ${UNIWALLET_TRANSFLOW_ID}
  product-id: ${UNIWALLET_PRODUCT_ID}
```

```yaml
# application-local.yml — gitignored, has real values
uniwallet:
  base-url: https://uniwalletsandbox.transflowitc.com
  api-key: your-real-key
  country: GH
  transflow-id: your-transflow-id
  product-id: your-product-id
```

```bash
# Run with local profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

**Binding yml to a Java class:**
```java
@Data
@Component
@ConfigurationProperties(prefix = "uniwallet")
public class UniwalletConfig {
    private String baseUrl;      // maps to uniwallet.base-url
    private String apiKey;       // maps to uniwallet.api-key
    private String country;
    private String transflowId;
    private String productId;
}
```

Spring automatically maps kebab-case yml keys (`base-url`) to camelCase fields (`baseUrl`).

---

### ResponseEntity

`ResponseEntity` gives you full control over the HTTP response — status code, headers, and body.

```java
// 200 OK with body
return ResponseEntity.ok(data);

// 201 Created
return ResponseEntity.status(HttpStatus.CREATED).body(data);

// 400 Bad Request
return ResponseEntity.badRequest().body("Invalid input");

// 404 Not Found
return ResponseEntity.notFound().build();

// 500 Internal Server Error
return ResponseEntity.internalServerError().body("Something went wrong");

// Custom status
return ResponseEntity.status(429).body("Too many requests");
```

NestJS equivalent: `res.status(200).json(data)`

---

## 3. Lombok

Lombok generates boilerplate Java code at compile time via annotations.

**The problem it solves:**
```java
// Without Lombok — 30+ lines for a simple DTO
public class NameEnquiryRequest {
    private String msisdn;
    private String network;

    public NameEnquiryRequest() {}

    public NameEnquiryRequest(String msisdn, String network) {
        this.msisdn = msisdn;
        this.network = network;
    }

    public String getMsisdn() { return msisdn; }
    public void setMsisdn(String msisdn) { this.msisdn = msisdn; }
    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    @Override
    public String toString() {
        return "NameEnquiryRequest{msisdn='" + msisdn + "', network='" + network + "'}";
    }
}

// With Lombok — 6 lines
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameEnquiryRequest {
    private String msisdn;
    private String network;
}
```

**Why getters/setters matter for Spring:**
Jackson (Spring's JSON library) uses getters and setters to serialize/deserialize JSON. Without them, JSON → object mapping breaks. Lombok generates them so Jackson works.

```java
// Jackson does this internally when JSON hits your endpoint:
NameEnquiryRequest req = new NameEnquiryRequest();
req.setMsisdn("233244300001"); // setter called
req.setNetwork("MTN");

// And when you read in your service:
String msisdn = req.getMsisdn(); // getter called
```

**Key Lombok annotations:**

| Annotation | Generates |
|-----------|-----------|
| `@Data` | Getters + setters + `toString` + `equals` + `hashCode` |
| `@Getter` | Getters only |
| `@Setter` | Setters only |
| `@NoArgsConstructor` | Empty constructor |
| `@AllArgsConstructor` | Constructor with all fields |
| `@RequiredArgsConstructor` | Constructor for all `final` fields |
| `@Builder` | Builder pattern |
| `@Slf4j` | `private static final Logger log = ...` |

**Lombok needs compiler annotation processing.** Add to `pom.xml`:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

---

## 4. HTTP in Spring Boot

### RestTemplate

Spring's synchronous HTTP client. Like `axios` in Node.js.

```java
// Declared as a bean so Spring can inject it
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}

// Injected and used in service
@Autowired
private RestTemplate restTemplate;
```

---

### HttpHeaders

A map wrapper for HTTP request/response headers.

```java
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);  // Content-Type: application/json
headers.set("x-key", "your-api-key");                // custom header
headers.set("x-country", "GH");
```

---

### HttpEntity

Bundles a request body + headers together before sending.

```java
Map<String, String> body = new HashMap<>();
body.put("msisdn", "233244300001");
body.put("network", "MTN");

HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
//         ^type of body
```

---

### Making API Calls

```java
// POST
ResponseEntity<NameEnquiryResponse> response = restTemplate.exchange(
    "https://api.example.com/name-enquiry",  // URL
    HttpMethod.POST,                          // method
    entity,                                   // body + headers
    NameEnquiryResponse.class                 // response type to deserialize into
);

NameEnquiryResponse result = response.getBody();

// GET with path variable
ResponseEntity<TransactionResponse> response = restTemplate.exchange(
    config.getBaseUrl() + "/transaction-status/" + refNo,
    HttpMethod.GET,
    entity,
    TransactionResponse.class
);
```

---

## 5. DTOs

DTOs (Data Transfer Objects) are simple objects that carry data between layers. No business logic — just fields.

**Request DTO** — what your client sends to YOUR API:
```java
@Data
public class TransactionRequest {
    private String msisdn;
    private String amount;
    private String network;
    private String narration;
}
```

**Response DTO** — what YOUR API sends back to your client:
```java
@Data
public class TransactionResponse {
    private String responseCode;
    private String responseMessage;
    private String uniwalletTransactionId;
}
```

**Why not expose the raw Uniwallet response directly?**
Because your API is middleware. You control the contract. If Uniwallet changes their response shape, you only update your service — not your clients.

**What gets server-side generated vs client-provided:**

| Field | Source |
|-------|--------|
| `msisdn` | Client |
| `amount` | Client |
| `network` | Client |
| `narration` | Client |
| `refNo` | Server — `UUID.randomUUID().toString()` |
| `transflowId` | Server — from config |
| `productId` | Server — from config |
| `currency` | Server — from config or hardcoded |

---

## 6. Cross-Cutting Concerns

These are concerns that apply across all routes — not tied to any single feature.

### CORS

```java
@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;
    private final SecurityHeadersInterceptor securityHeadersInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")            // replace with actual origin in prod
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/api/**");
        registry.addInterceptor(securityHeadersInterceptor)
                .addPathPatterns("/api/**");
    }
}
```

**Why `@Override`?** `WebMvcConfigurer` is an interface. `@Override` means you're providing your own implementation of its methods — telling Spring "use my version, not the default." Same as implementing `NestMiddleware` in NestJS.

**Why no `@Bean` here?** When you implement `WebMvcConfigurer` directly on the `@Configuration` class, Spring picks it up automatically. `@Bean` was only needed when we were returning a `WebMvcConfigurer` from a method.

---

### Security Headers

```java
@Component
public class SecurityHeadersInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        response.setHeader("Cache-Control", "no-store");
        return true; // true = continue processing the request
    }
}
```

`HandlerInterceptor` runs before (`preHandle`) and after (`postHandle`, `afterCompletion`) each request. `preHandle` returning `false` stops the request dead.

---

### Rate Limiting (Bucket4j)

Bucket4j uses the **token bucket algorithm** — you start with N tokens, each request consumes one, tokens refill over time.

```xml
<!-- pom.xml -->
<dependency>
    <groupId>com.bucket4j</groupId>
    <artifactId>bucket4j_jdk17-core</artifactId>
    <version>8.10.1</version>
</dependency>
```

```java
// Define the bucket (50 requests per minute)
@Configuration
public class RateLimitConfig {

    @Bean
    public Bucket bucket() {
        Bandwidth limit = Bandwidth.simple(50, Duration.ofMinutes(1));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}

// Enforce it on every request
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Bucket bucket;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (bucket.tryConsume(1)) {
            return true; // token available, proceed
        }
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
        response.getWriter().write("Too many requests");
        return false; // stop the request
    }
}
```

---

### Global Exception Handling

Without this, any uncaught exception returns a raw 500 with a Spring error page. This intercepts all exceptions and returns clean JSON.

```java
@Slf4j
@RestControllerAdvice  // applies to all @RestController classes
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)  // 4xx from RestTemplate
    public ResponseEntity<Map<String, String>> handleClientError(HttpClientErrorException e) {
        log.error("Client error: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(Map.of(
                "responseCode", "400",
                "responseMessage", e.getMessage()
        ));
    }

    @ExceptionHandler(HttpServerErrorException.class)  // 5xx from RestTemplate
    public ResponseEntity<Map<String, String>> handleServerError(HttpServerErrorException e) {
        log.error("Server error: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(Map.of(
                "responseCode", "500",
                "responseMessage", e.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)  // catch-all
    public ResponseEntity<Map<String, String>> handleAll(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(Map.of(
                "responseCode", "500",
                "responseMessage", "An unexpected error occurred"
        ));
    }
}
```

---

## 7. Environments & Profiles

Spring profiles let you have different configs for different environments.

```
application.yml          ← base config (no secrets)
application-local.yml    ← local dev secrets (gitignored)
application-prod.yml     ← prod config (or use real env vars)
```

```bash
# Run with local profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Run with prod profile
java -jar app.jar --spring.profiles.active=prod
```

**.gitignore:**
```
application-local.yml
```

---

## 8. Frequently Asked Questions

**Q: Does Java have an HTTP server built in?**
No. Core Java has no HTTP server. Spring Boot bundles Tomcat via the `Spring Web` dependency, turning your JAR into a runnable web server.

**Q: Why is the package name so long (`com.itc.uniwallet`)?**
Java uses reverse-domain package naming to guarantee global uniqueness across the entire Java ecosystem. For internal projects, shorter names like `wallet.dto` work fine — just be consistent.

**Q: Why `Map<String, String>` but `new HashMap<>()`?**
`Map` is the interface, `HashMap` is the implementation. Programming to the interface means you can swap implementations without changing consuming code. Same as `List` vs `ArrayList`.

**Q: What is `final` on a field?**
The reference can't be reassigned after initialization. On injected dependencies it's best practice — guarantees Spring always provides the value and it's never accidentally nulled out.

**Q: How does Spring find all my classes automatically without module registration?**
`@SpringBootApplication` includes `@ComponentScan`, which scans all packages under the main class and registers any class annotated with `@Component`, `@Service`, `@RestController`, `@Repository`, or `@Configuration`. No manual wiring like NestJS modules.

**Q: Why do getters and setters matter if I'm using Lombok?**
Jackson (Spring's JSON serializer) calls getters/setters internally to map JSON ↔ Java objects. Lombok generates them so Jackson works. Without either, your DTOs would be empty objects.

**Q: What is `@Override`?**
It tells the compiler you're intentionally overriding a method from a parent class or interface. It's also a safety net — if you mistype the method name, the compiler throws an error instead of silently creating a new method.

**Q: What's the difference between UAT, Sandbox, and Production?**

| Environment | Purpose |
|-------------|---------|
| Sandbox | Developer testing — mocked responses, no real money |
| UAT | Stakeholder/client testing — realistic data, formal sign-off |
| Production | Live — real users, real money |

**Q: Why do we build our own API around Uniwallet's API?**
You are middleware. Your client (app/frontend) hits your API, you call Uniwallet, return the result. This decouples your clients from Uniwallet's contract — if Uniwallet changes, only your service layer changes.

**Q: Why generate `refNo` server-side?**
`refNo` is your transaction ID. Letting clients generate it opens the door to duplicate/malicious references. Server-side `UUID.randomUUID()` guarantees uniqueness.

**Q: What is `@Slf4j`?**
A Lombok annotation that generates:
```java
private static final Logger log = LoggerFactory.getLogger(WalletService.class);
```
Giving you `log.info(...)`, `log.error(...)`, `log.warn(...)` for free.

**Q: What is `@RestControllerAdvice`?**
A global interceptor for all `@RestController` exceptions. Instead of try/catch in every controller, you define handlers once here and they apply everywhere.

---

*Built during SIWES @ IT Consortium, Accra — May 2026*
*Stack: Java 21 · Spring Boot 4.0.6 · Maven · Lombok · Bucket4j*

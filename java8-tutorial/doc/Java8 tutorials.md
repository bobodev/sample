# Java8 tutorials
> 简述 [What's New in JDK 8](http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html)
> 
> Java8主要目标
> > * 更高的开发效率
> > * 更高代码可靠性
> > * 更好的利用多核和多处理器系统
> > 
> > > * 代码不再只是串行执行

## 新特性
* **[Lambda 表达式](#lambda_expression_id)** − Lambda允许把函数作为一个方法的参数（函数作为参数传递进方法中。
* **[函数式接口](#function_interface_id)** − `@FunctionalInterface` 是 Java 8 新加入的一种接口，用于指明该接口类型声明是根据 Java 语言规范定义的函数式接口。函数式接口只包含唯一一个抽象方法。
* **[方法引用](#method_references_id)** − 方法引用提供了非常有用的语法，可以直接引用已有Java类或对象（实例）的方法或构造器。与lambda联合使用，方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
* **[默认方法](#default_method_id)** − 默认方法就是一个在接口里面有了一个实现的方法。
* **[Stream API](#stream_id)** − 新添加的Stream API（java.util.stream） 把真正的函数式编程风格引入到Java中。
* **[Optional 类](#optional_id)** − Optional 类已经成为 Java 8 类库的一部分，用来解决空指针异常。
* **Date Time API** − 加强对日期与时间的处理。
* **新工具** − 新的编译工具，如：Nashorn引擎 jjs、 类依赖分析器jdeps。
* **Nashorn, JavaScript 引擎** −  Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用。

## 原理篇
* **[lambda实现原理浅析](#lambda_translation_id)** - 编译器在类中生成一个静态函数；运行时调用该静态函数（以内部类形式调用）。

## 应用篇

* [List转Map](#list_to_map_id)

### <a name="lambda_expression_id"></a> Lambda 表达式

```
someObject.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {

        //Event listener implementation goes here...

    }
});
```

#### 概述
Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。

#### 表达式
Java 中的 Lambda 表达式通常使用 `(argument) -> expression` 或者 `(argument) -> {body}` 语法书写。

**lambda表达式的重要特征**

   * **可选类型声明**：不需要声明参数类型，编译器可以统一识别参数值。
   * **可选的参数圆括号**：一个参数无需定义圆括号，但多个参数需要定义圆括号。
   * **可选的大括号**：如果主体包含了一个语句，就不需要使用大括号。
   * **可选的返回关键字**：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。

```
(arg1, arg2...) -> { body }

(type1 arg1, type2 arg2...) -> { body }
```

以下是一些 Lambda 表达式的例子：

```
(int a, int b) -> {  return a + b; }

() -> System.out.println("Hello World");

(String s) -> { System.out.println(s); }

() -> 42

() -> { return 3.1415 };
```

*lambda 表达式的语法由参数列表、箭头符号 -> 和函数体组成。函数体既可以是一个表达式，也可以是一个语句块.*

   1. 表达式：表达式会被执行然后返回执行结果。
   2. 语句块：语句块中的语句会被依次执行，就像方法中的语句一样
       * `return` 语句会把控制权交给匿名方法的调用者
       * `break` 和 `continue` 只能在循环中使用
       * 如果函数体有返回值，那么函数体内部的每一条路径都必须返回值

表达式函数体适合小型 lambda 表达式，它消除了 `return` 关键字，使得语法更加简洁。

**Lambda 表达式的结构**

* 一个 Lambda 表达式可以有零个或多个参数
* 参数的类型既可以明确声明，也可以根据上下文来推断。例如：`(int a)` 与 `(a)` 效果相同
* 所有参数需包含在圆括号内，参数之间用逗号相隔。例如：`(a, b)` 或 `(int a, int b)` 或 `(String a, int b, float c)`
* 空圆括号代表参数集为空。例如：`() -> 42`
* 当只有一个参数，且其类型可推导时，圆括号（）可省略。例如：`a -> return a*a`
* Lambda 表达式的主体可包含零条或多条语句
* 如果 Lambda 表达式的主体只有一条语句，花括号{}可省略。匿名函数的返回类型与该主体表达式一致
* 如果 Lambda 表达式的主体包含一条以上语句，则表达式必须包含在花括号{}中（形成代码块）。匿名函数的返回类型与代码块的返回类型一致，若没有返回则为空

**变量作用域**

* lambda 表达式只能引用标记了 `final` 的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
* lambda 表达式的局部变量可以不用声明为 `final`，但是必须不可被后面的代码修改（即隐性的具有 `final` 的语义）。
* 在 lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量。

### <a name="function_interface_id"></a> 函数式接口

### 概述
函数式接口(Functional Interface)就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。

函数式接口可以被隐式转换为lambda表达式。

函数式接口可以现有的函数友好地支持 lambda。

**JDK 1.8之前已有的函数式接口**:

* java.lang.Runnable
* java.util.concurrent.Callable
* java.security.PrivilegedAction
* java.util.Comparator
* java.io.FileFilter
* java.nio.file.PathMatcher
* java.lang.reflect.InvocationHandler
* java.beans.PropertyChangeListener
* java.awt.event.ActionListener
* javax.swing.event.ChangeListener

**JDK 1.8 新增加的函数接口**：

* java.util.function

java.util.function 它包含了很多类，用来支持 Java的 函数式编程，该包中的函数式接口有：

接口                     |描述
------------------------|----------------------------------------------
BiConsumer<T,U>         | 代表了一个接受两个输入参数的操作，并且不返回任何结果
BiFunction<T,U,R>       | 代表了一个接受两个输入参数的方法，并且返回一个结果
BinaryOperator<T>       | 代表了一个作用于两个同类型操作符的操作，并且返回了操作
BiPredicate<T,U>        | 代表了一个两个参数的boolean值方法
BooleanSupplier         | 代表了boolean值结果的提供方
Consumer<T>             | 代表了接受一个输入参数并且无返回的操作
DoubleBinaryOperator    | 代表了作用于两个double值操作符的操作，并且返回了一
DoubleConsumer          | 代表一个接受double值参数的操作，并且不返回结果。
DoubleFunction<R>       | 代表接受一个double值参数的方法，并且返回结果
DoublePredicate         | 代表一个拥有double值参数的boolean值方法
DoubleSupplier          | 代表一个double值结构的提供方
DoubleToIntFunction     | 接受一个double类型输入，返回一个int类型结果。
DoubleToLongFunction    | 接受一个double类型输入，返回一个long类型结果
DoubleUnaryOperator     | 接受一个参数同为类型double,返回值类型也为double 。
Function<T,R>           | 接受一个输入参数，返回一个结果。
IntBinaryOperator       | 接受两个参数同为类型int,返回值类型也为int 。
IntConsumer             | 接受一个int类型的输入参数，无返回值 。
IntFunction<R>          | 接受一个int类型输入参数，返回一个结果 。
IntPredicate            | 接受一个int输入参数，返回一个布尔值的结果。
IntSupplier             | 无参数，返回一个int类型结果。
IntToDoubleFunction     | 接受一个int类型输入，返回一个double类型结果 。
IntToLongFunction       | 接受一个int类型输入，返回一个long类型结果。
IntUnaryOperator        | 接受一个参数同为类型int,返回值类型也为int 。
LongBinaryOperator      | 接受两个参数同为类型long,返回值类型也为long。
LongConsumer            | 接受一个long类型的输入参数，无返回值。
LongFunction<R>         | 接受一个long类型输入参数，返回一个结果。
LongPredicate           | R接受一个long输入参数，返回一个布尔值类型结果。
LongSupplier            | 无参数，返回一个结果long类型的值。
LongToDoubleFunction    | 接受一个long类型输入，返回一个double类型结果。
LongToIntFunction       | 接受一个long类型输入，返回一个int类型结果。
LongUnaryOperator       | 接受一个参数同为类型long,返回值类型也为long。
ObjDoubleConsumer<T>    | 接受一个object类型和一个double类型的输入参数，无返
ObjIntConsumer<T>       | 接受一个object类型和一个int类型的输入参数，无返回值。
ObjLongConsumer<T>      | 接受一个object类型和一个long类型的输入参数，无返回值
Predicate<T>            | 接受一个输入参数，返回一个布尔值结果。
Supplier<T>             | 无参数，返回一个结果。
ToDoubleBiFunction<T,U> | 接受两个输入参数，返回一个double类型结果
ToDoubleFunction<T>     | 接受一个输入参数，返回一个double类型结果
ToIntBiFunction<T,U>    | 接受两个输入参数，返回一个int类型结果。
ToIntFunction<T>        | 接受一个输入参数，返回一个int类型结果。
ToLongBiFunction<T,U>   | 接受两个输入参数，返回一个long类型结果。
ToLongFunction<T>       | 接受一个输入参数，返回一个long类型结果。
UnaryOperator<T>        | 接受一个参数为类型T,返回值类型也为T。

常用的函数式接口：

* `Predicate<T>` —— 接收 `T` 并返回 `boolean`
* `Consumer<T>` —— 接收 `T`，不返回值
* `Function<T, R>`—— 接收 `T`，返回 `R`
* `Supplier<T>` —— 提供 `T` 对象（例如工厂），不接收值
* `UnaryOperator<T>` —— 接收 `T` 对象，返回 `T`
* `BinaryOperator<T>` —— 接收两个 `T`，返回 `T`

#### 函数式接口特点

![FunctionInterface](img/function-interface.png)

**通过JDK8源码javadoc，可以知道这个注解有以下特点**：

1. 该注解只能标记在"有且仅有一个抽象方法"的接口上。
2. JDK8接口中的静态方法和默认方法，都不算是抽象方法。
3. 接口默认继承 `java.lang.Object` ，所以如果接口显示声明覆盖了 `Object` 中方法，那么也不算抽象方法。
4. 该注解不是必须的，如果一个接口符合"函数式接口"定义，那么加不加该注解都没有影响。加上该注解能够更好地让编译器进行检查。如果编写的不是函数式接口，但是加上了 `@FunctionInterface`，那么编译器会报错。

```
// 正确的函数式接口  
@FunctionalInterface  
public interface TestInterface {           
    // 抽象方法  
    public void sub();  
  
    // java.lang.Object中的方法不是抽象方法  
    public boolean equals(Object var1);  
  
    // default不是抽象方法  
    public default void defaultMethod(){  
  
    }  
  
    // static不是抽象方法  
    public static void staticMethod(){  
  
    }  
}
```

**QA:** 函数式接口Comparator的equals()方法是干什么的，有什么作用？

### <a name="method_references_id"></a> 方法引用

#### 概述

+ 方法引用通过方法的名字来指向一个方法。
+ 方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
+ 方法引用使用一对冒号 :: 。

方法引用是用来直接访问类或者实例的已经存在的方法或者构造方法。方法引用提供了一种引用而不执行方法的方式，它需要由兼容的函数式接口构成的目标类型上下文。计算时，方法引用会创建函数式接口的一个实例。

当Lambda表达式中只是执行一个方法调用时，不用Lambda表达式，直接通过方法引用的形式可读性更高一些。方法引用是一种更简洁易懂的Lambda表达式。

#### 方法引用的形式

方法引用的标准形式是:类名::方法名。**（注意：只需要写方法名，不需要写括号）**

有以下四种形式的方法引用:

类型 	                        | 示例
------------------------------ | ---------------------------------
引用静态方法 	                  | ContainingClass::staticMethodName
引用某个对象的实例方法 	           |containingObject::instanceMethodName
引用某个类型的任意对象的实例方法 	 |ContainingType::methodName
引用构造方法 	                  |ClassName::new

**DEMO**

```
public class Person {
    public enum Sex{
        MALE,FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public Sex getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public static int compareByAge(Person a,Person b){
        return a.birthday.compareTo(b.birthday);
    }

}
```

**引用静态方法**

```
Person [] persons = new Person[10];

//使用匿名类
Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.birthday.compareTo(o2.birthday);
            }
 });

//使用lambda表达式
Arrays.sort(persons, (o1, o2) -> o1.birthday.compareTo(o2.birthday));

//使用lambda表达式和类的静态方法
Arrays.sort(persons, (o1, o2) -> Person.compareByAge(o1,o2));

//使用方法引用
//引用的是类的静态方法
Arrays.sort(persons, Person::compareByAge);
```

**引用对象的实例方法**

```
class ComparisonProvider{
            public int compareByName(Person a,Person b){
                return a.getName().compareTo(b.getName());
            }

            public int compareByAge(Person a,Person b){
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }

ComparisonProvider provider = new ComparisonProvider();

//使用lambda表达式
//对象的实例方法
Arrays.sort(persons,(a,b)->provider.compareByAge(a,b));

//使用方法引用
//引用的是对象的实例方法
Arrays.sort(persons, provider::compareByAge);
```

**引用类型对象的实例方法**

```
String[] stringsArray = {"Hello","World"};

//使用lambda表达式和类型对象的实例方法
Arrays.sort(stringsArray,(s1,s2)->s1.compareToIgnoreCase(s2));

//使用方法引用
//引用的是类型对象的实例方法
Arrays.sort(stringsArray, String::compareToIgnoreCase);
```

**引用构造方法**

```
public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>>
    DEST transferElements(SOURCE sourceColletions, Supplier<DEST> colltionFactory) {
        DEST result = colltionFactory.get();
        for (T t : sourceColletions) {
            result.add(t);
        }
        return result;
    }
...
 
final List<Person> personList = Arrays.asList(persons);

//使用lambda表达式
Set<Person> personSet = transferElements(personList,()-> new HashSet<>());

//使用方法引用
//引用的是构造方法
Set<Person> personSet2 = transferElements(personList, HashSet::new); 
```

### <a name="default_method_id"></a> 默认方法

#### 概述

默认方法就是接口可以有实现方法，而且不需要实现类去实现其方法。只需在方法名前面加个default关键字即可实现默认方法。 

> 为什么要有这个特性？
> 
> 首先，之前的接口是个双刃剑，好处是面向抽象而不是面向具体编程，缺陷是，当需要修改接口时候，需要修改全部实现该接口的类，目前的java 8之前的集合框架没有foreach方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。所以引进的默认方法。他们的目的是为了解决接口的修改与现有的实现不兼容的问题。

#### 源码接口新增的默认方法

**java.util.Map**

```
default void forEach(BiConsumer<? super K, ? super V> action) {
    Objects.requireNonNull(action);
    for (Map.Entry<K, V> entry : entrySet()) {
        K k;
        V v;
        try {
            k = entry.getKey();
            v = entry.getValue();
        } catch(IllegalStateException ise) {
            // this usually means the entry is no longer in the map.
            throw new ConcurrentModificationException(ise);
        }
        action.accept(k, v);
    }
}
```

**java.util.Comparator**

```
public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
        Function<? super T, ? extends U> keyExtractor)
{
    Objects.requireNonNull(keyExtractor);
    return (Comparator<T> & Serializable)
        (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
}
```

**java.util.List**

```
default void sort(Comparator<? super E> c) {
    Object[] a = this.toArray();
    Arrays.sort(a, (Comparator) c);
    ListIterator<E> i = this.listIterator();
    for (Object e : a) {
        i.next();
        i.set((E) e);
    }
}
```

**java.util.Collection**

```
default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
}
    
default Stream<E> parallelStream() {
    return StreamSupport.stream(spliterator(), true);
}   
```

### <a name="stream_id"></a> Stream

#### 概述
Stream（流）是一个来自数据源的元素队列并支持聚合操作

* 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
* **数据源** 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
* **聚合操作** 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。

和以前的Collection操作不同， Stream操作还有两个基础的特征：

* **Pipelining:** 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
* **内部迭代：** 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。

#### Stream操作分类

分类 	                        |                             | 函数
------------------------------ | --------------------------- | ----------------------
中间操作(Intermediate operations)   |无状态(Stateless)         |  unordered() filter() map() mapToInt() mapToLong() mapToDouble() flatMap() flatMapToInt() flatMapToLong() flatMapToDouble() peek()
                                   |有状态(Stateful)          |  distinct() sorted() limit() skip()
结束操作(Terminal operations)       |非短路操作                 |  forEach() forEachOrdered() toArray() reduce() collect() max() min() count()
                                   |短路操作(short-circuiting)|   anyMatch() allMatch() noneMatch() findFirst() findAny()
                                   
Stream上的所有操作分为两类：**中间操作** 和 **结束操作**，中间操作只是一种标记，只有结束操作才会触发实际计算。中间操作又可以分为 **无状态的(Stateless)** 和 **有状态的(Stateful)**，无状态中间操作是指元素的处理不受前面元素的影响，而有状态的中间操作必须等到所有元素处理之后才知道最终结果，比如排序是有状态操作，在读取所有元素之前并不能确定排序结果；结束操作又可以分为 **短路操作** 和 **非短路操作**，短路操作是指不用处理全部元素就可以返回结果，比如找到第一个满足条件的元素。之所以要进行如此精细的划分，是因为底层对每一种情况的处理方式不同。

#### 示例

```
// Accumulate names into a List
List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());

// Accumulate names into a TreeSet
Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));

// Convert elements to strings and concatenate them, separated by commas
String joined = things.stream()
                      .map(Object::toString)
                      .collect(Collectors.joining(", "));

// Compute sum of salaries of employee
int total = employees.stream()
                     .collect(Collectors.summingInt(Employee::getSalary)));

// Group employees by department
Map<Department, List<Employee>> byDept
    = employees.stream()
               .collect(Collectors.groupingBy(Employee::getDepartment));

// Compute sum of salaries by department
Map<Department, Integer> totalByDept
    = employees.stream()
               .collect(Collectors.groupingBy(Employee::getDepartment,
                                              Collectors.summingInt(Employee::getSalary)));

// Partition students into passing and failing
Map<Boolean, List<Student>> passingFailing =
    students.stream()
            .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));
```

### <a name="optional_id"></a> Optional 类

#### 概述

`Optional` 类是一个可以为 `null` 的容器对象。如果值存在则 `isPresent()` 方法会返回true，调用 `get()` 方法会返回该对象。

`Optional` 是个容器：它可以保存类型T的值，或者仅仅保存 `null` 。`Optional` 提供很多有用的方法，这样我们就不用显式进行空值检测。

`Optional` 类的引入很好的解决空指针异常。

#### 类方法

方法                              |描述
---------------------------------|----------------------------------------------
static <T> Optional<T> empty()                                  | 返回空的 Optional 实例。
boolean equals(Object obj)                                      | 判断其他对象是否等于 Optional。
Optional<T> filter(Predicate<? super <T> predicate)             | 如果值存在，并且这个值匹配给定的 predicate，返回一个Optional用以描述这个值，否则返回一个空的Optional。
`<U> Optional<U> flatMap(Function<? super T,Optional<U>> mapper)` | 如果值存在，返回基于Optional包含的映射方法的值，否则返回一个空的Optional
T get()                                                         | 如果在这个Optional中包含这个值，返回值，否则抛出异常：NoSuchElementException
int hashCode()                                                  | 返回存在值的哈希码，如果值不存在 返回 0。
void ifPresent(Consumer<? super T> consumer)                    | 如果值存在则使用该值调用 consumer , 否则不做任何事情。
boolean isPresent()                                             | 如果值存在则方法会返回true，否则返回 false。
`<U>Optional<U> map(Function<? super T,? extends U> mapper)`      | 如果存在该值，提供的映射方法，如果返回非null，返回一个Optional描述结果。
static <T> Optional<T> of(T value)                              | 返回一个指定非null值的Optional。
static <T> Optional<T> ofNullable(T value)                      | 如果为非空，返回 Optional 描述的指定值，否则返回空的 Optional。
T orElse(T other)                                               | 如果存在该值，返回值， 否则返回 other。
T orElseGet(Supplier<? extends T> other)                        | 如果存在该值，返回值， 否则触发 other，并返回 other 调用的结果。
<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)| 如果存在该值，返回包含的值，否则抛出由 Supplier 继承的异常
String toString()                                               | 返回一个Optional的非空字符串，用来调试

#### 示例

```

public class OptionalDemo {

    public static void main(String[] args) {
        //创建Optional实例，也可以通过方法返回值得到。
        Optional<String> name = Optional.of("Sanaulla");

        //创建没有值的Optional实例，例如值为'null'
        Optional empty = Optional.ofNullable(null);

        //isPresent方法用来检查Optional实例是否有值。
        if (name.isPresent()) {
            //调用get()返回Optional值。
            System.out.println(name.get());
        }

        try {
            //在Optional实例上调用get()抛出NoSuchElementException。
            System.out.println(empty.get());
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }

        //ifPresent方法接受lambda表达式参数。
        //如果Optional值不为空，lambda表达式会处理并在其上执行操作。
        name.ifPresent((value) -> {
            System.out.println("The length of the value is: " + value.length());
        });

        //如果有值orElse方法会返回Optional实例，否则返回传入的错误信息。
        System.out.println(empty.orElse("There is no value present!"));
        System.out.println(name.orElse("There is some value!"));

        //orElseGet与orElse类似，区别在于传入的默认值。
        //orElseGet接受lambda表达式生成默认值。
        System.out.println(empty.orElseGet(() -> "Default Value"));
        System.out.println(name.orElseGet(() -> "Default Value"));

        try {
            //orElseThrow与orElse方法类似，区别在于返回值。
            //orElseThrow抛出由传入的lambda表达式/方法生成异常。
            empty.orElseThrow(ValueAbsentException::new);
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

        //map方法通过传入的lambda表达式修改Optional实例默认值。
        //lambda表达式返回值会包装为Optional实例。
        Optional<String> upperName = name.map((value) -> value.toUpperCase());
        System.out.println(upperName.orElse("No value found"));

        //flatMap与map（Function）非常相似，区别在于lambda表达式的返回值。
        //map方法的lambda表达式返回值可以是任何类型，但是返回值会包装成Optional实例。
        //但是flatMap方法的lambda返回值总是Optional类型。
        upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
        System.out.println(upperName.orElse("No value found"));

        //filter方法检查Optional值是否满足给定条件。
        //如果满足返回Optional实例值，否则返回空Optional。
        Optional<String> longName = name.filter((value) -> value.length() > 6);
        System.out.println(longName.orElse("The name is less than 6 characters"));

        //另一个示例，Optional值不满足给定条件。
        Optional<String> anotherName = Optional.of("Sana");
        Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
        System.out.println(shortName.orElse("The name is less than 6 characters"));

    }
}

class ValueAbsentException extends Throwable {

    public ValueAbsentException() {
        super();
    }

    public ValueAbsentException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return "No value present in the Optional instance";
    }
}
```

**输出结果：**

```result
Sanaulla
No value present
The length of the value is: 8
There is no value present!
Sanaulla
Default Value
Sanaulla
No value present in the Optional instance
SANAULLA
SANAULLA
Sanaulla
The name is less than 6 characters
```

### <a name="lambda_translation_id"></a> lambda实现原理浅析

**示例代码**

```
public class LambdaTranslationTest {

    @FunctionalInterface
    public interface Calculator<T extends Number, U extends Number, R extends Number> {
        R calc(T t, U u);
    }

    public static void main(String[] args) {
        Calculator<Integer, Double, Double> calculator = (x1, x2) -> x1 + x2;
        int num1 = 1;
        double num2 = 4.88;
        double result = calculator.calc(num1, num2);
        System.out.println(result);
        System.out.println("---------lambda----------");
        System.out.println(print(2.48, 4, (x1, x2) -> x1 + x2));
    }

    public static <T extends Number, U extends Number, R extends Number> R print(T t, U u, Calculator<T, U, R> calculator) {
        return calculator.calc(t, u);
    }
}
```

java命令 `javac LambdaTranslationTest.java` 编译 `LambdaTranslationTest.java` 生成 `.class` 文件。

```
LambdaTranslationTest$Calculator.class
LambdaTranslationTest.class		
LambdaTranslationTest.java
```

反编译LambdaTranslationTest类生成的class文件。

命令：`javap -p LambdaTranslationTest.class` , `javap -p(-private)` 输出所有类和成员

```
Compiled from "LambdaTranslationTest.java"
public class com.lambda.LambdaTranslationTest {
  public com.lambda.LambdaTranslationTest();
  public static void main(java.lang.String[]);
  public static <T extends java.lang.Number, U extends java.lang.Number, R extends java.lang.Number> R print(T, U, com.lambda.LambdaTranslationTest$Calculator<T, U, R>);
  private static java.lang.Double lambda$main$1(java.lang.Double, java.lang.Integer);
  private static java.lang.Double lambda$main$0(java.lang.Integer, java.lang.Double);
}
```
可以看到生成了两个私有的静态函数：`lambda$main$0` 和 `lambda$main$1`。
由此可以知道的是Lambda表达式在Java 8中首先会生成一个私有的静态函数，这个私有的静态函数干的就是Lambda表达式里面的内容。lambda表达式转化的私有静态函数是如何被调用的呢？

**LambdaMetafactory.java**

```
public static CallSite metafactory(MethodHandles.Lookup caller,
                                   String invokedName,
                                   MethodType invokedType,
                                   MethodType samMethodType,
                                   MethodHandle implMethod,
                                   MethodType instantiatedMethodType)
        throws LambdaConversionException {
    AbstractValidatingLambdaMetafactory mf;
    mf = new InnerClassLambdaMetafactory(caller, invokedType,
                                         invokedName, samMethodType,
                                         implMethod, instantiatedMethodType,
                                         false, EMPTY_CLASS_ARRAY, EMPTY_MT_ARRAY);
    mf.validateMetafactoryArgs();
    return mf.buildCallSite();
}
```
这个函数为Lambda表达式生成了一个内部类，为了验证是否生成内部类，可以在运行时 `VM options:` 加上 `-Djdk.internal.lambda.dumpProxyClasses` ，加上这个参数后，运行时，会将生成的内部类class码输出到一个文件中，文件目录（与运行类包名一致）与项目src目录同级，这个文件夹就是lambda表达式所在的包名，里面就存放了对应lambda表达式的类。

生成的内部类class文件：

```
LambdaTranslationTest$$Lambda$1.class
LambdaTranslationTest$$Lambda$2.class
```

注意：新生成的类的命名规则为：**包名.类名$$Lambda$自增计数**

javap -c -p 查看LambdaTranslationTest.class

**-c 输出分解后的代码，例如，类中每一个方法内，包含java字节码的指令**

`javap -c -p LambdaTranslationTest.class`

```
Compiled from "LambdaTranslationTest.java"
public class com.lambda.LambdaTranslationTest {
  public com.lambda.LambdaTranslationTest();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: invokedynamic #2,  0              // InvokeDynamic #0:calc:()Lcom/lambda/LambdaTranslationTest$Calculator;
       5: astore_1
       6: iconst_1
       7: istore_2
       8: ldc2_w        #3                  // double 4.88d
      11: dstore_3
      12: aload_1
      13: iload_2
      14: invokestatic  #5                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
      17: dload_3
      18: invokestatic  #6                  // Method java/lang/Double.valueOf:(D)Ljava/lang/Double;
      21: invokeinterface #7,  3            // InterfaceMethod com/lambda/LambdaTranslationTest$Calculator.calc:(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;
      26: checkcast     #8                  // class java/lang/Double
      29: invokevirtual #9                  // Method java/lang/Double.doubleValue:()D
      32: dstore        5
      34: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
      37: dload         5
      39: invokevirtual #11                 // Method java/io/PrintStream.println:(D)V
      42: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
      45: ldc           #12                 // String ---------lambda----------
      47: invokevirtual #13                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      50: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
      53: ldc2_w        #14                 // double 2.48d
      56: invokestatic  #6                  // Method java/lang/Double.valueOf:(D)Ljava/lang/Double;
      59: iconst_4
      60: invokestatic  #5                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
      63: invokedynamic #16,  0             // InvokeDynamic #1:calc:()Lcom/lambda/LambdaTranslationTest$Calculator;
      68: invokestatic  #17                 // Method print:(Ljava/lang/Number;Ljava/lang/Number;Lcom/lambda/LambdaTranslationTest$Calculator;)Ljava/lang/Number;
      71: invokevirtual #18                 // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
      74: return

  public static <T extends java.lang.Number, U extends java.lang.Number, R extends java.lang.Number> R print(T, U, com.lambda.LambdaTranslationTest$Calculator<T, U, R>);
    Code:
       0: aload_2
       1: aload_0
       2: aload_1
       3: invokeinterface #7,  3            // InterfaceMethod com/lambda/LambdaTranslationTest$Calculator.calc:(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;
       8: areturn

  private static java.lang.Double lambda$main$1(java.lang.Double, java.lang.Integer);
    Code:
       0: aload_0
       1: invokevirtual #9                  // Method java/lang/Double.doubleValue:()D
       4: aload_1
       5: invokevirtual #19                 // Method java/lang/Integer.intValue:()I
       8: i2d
       9: dadd
      10: invokestatic  #6                  // Method java/lang/Double.valueOf:(D)Ljava/lang/Double;
      13: areturn

  private static java.lang.Double lambda$main$0(java.lang.Integer, java.lang.Double);
    Code:
       0: aload_0
       1: invokevirtual #19                 // Method java/lang/Integer.intValue:()I
       4: i2d
       5: aload_1
       6: invokevirtual #9                  // Method java/lang/Double.doubleValue:()D
       9: dadd
      10: invokestatic  #6                  // Method java/lang/Double.valueOf:(D)Ljava/lang/Double;
      13: areturn
}
```

`javap -c -p LambdaTranslationTest\$\$Lambda\$2.class`

```
final class com.lambda.LambdaTranslationTest$$Lambda$2 implements com.lambda.LambdaTranslationTest$Calculator {
  private com.lambda.LambdaTranslationTest$$Lambda$2();
    Code:
       0: aload_0
       1: invokespecial #10                 // Method java/lang/Object."<init>":()V
       4: return

  public java.lang.Number calc(java.lang.Number, java.lang.Number);
    Code:
       0: aload_1
       1: checkcast     #15                 // class java/lang/Double
       4: aload_2
       5: checkcast     #17                 // class java/lang/Integer
       8: invokestatic  #23                 // Method com/lambda/LambdaTranslationTest.lambda$main$1:(Ljava/lang/Double;Ljava/lang/Integer;)Ljava/lang/Double;
      11: checkcast     #25                 // class java/lang/Number
      14: areturn
}
```

`LambdaTranslationTest$$Lambda$2.class` 中静态方法调用的就是 `LambdaTranslationTest.class` 中的 `lambda$main$1` 。


通过以上字节码指令发现，lambda表达式等价于如下形式：`LambdaTranslationTest2.java`

```
public class LambdaTranslationTest2 {

    @FunctionalInterface
    public interface Calculator<T extends Number, U extends Number, R extends Number> {
        R calc(T t, U u);
    }

    private static double lambda$main$1(double d1, int i1) {
        return d1 + i1;
    }

    final class Lambda$2 implements Calculator<Double, Integer, Double> {

        @Override
        public Double calc(Double aDouble, Integer integer) {
            return lambda$main$1(aDouble, integer);
        }
    }

    public static void main(String[] args) {
//        System.out.println(print(2.48, 4, (x1, x2) -> x1 + x2));
        System.out.println(print(2.48, 4, new LambdaTranslationTest2().new Lambda$2()));
    }

    public static <T extends Number, U extends Number, R extends Number> R print(T t, U u, Calculator<T, U, R> calculator) {
        return calculator.calc(t, u);
    }
}
```

### <a name="list_to_map_id"></a> List 转 Map

#### 常用方式

代码如下：

```
public Map<Long, String> getIdNameMap(List<Account> accounts) {
    return accounts.stream().collect(Collectors.toMap(Account::getId, Account::getUserName));
}
```

#### 收集成实体本身map

代码如下：

```
public Map<Long, Account> getIdAccountMap(List<Account> accounts) {
    return accounts.stream().collect(Collectors.toMap(Account::getId, account -> account));
}
```

`account -> account` 是一个返回本身的lambda表达式，其实还可以使用 `Function` 接口中的一个默认方法代替，使整个方法更简洁优雅：

```
public Map<Long, Account> getIdAccountMap(List<Account> accounts) {
    return accounts.stream().collect(Collectors.toMap(Account::getId, Function.identity()));
}
```

#### 重复key的情况

代码如下：

```
public Map<String, Account> getNameAccountMap(List<Account> accounts) {
    return accounts.stream().collect(Collectors.toMap(Account::getUserName, Function.identity()));
}
```

这个方法可能报错 `（java.lang.IllegalStateException: Duplicate key）` ，因为name是有可能重复的。`toMap` 有个重载方法，可以传入一个合并的函数来解决key冲突问题：

```
public Map<String, Account> getNameAccountMap(List<Account> accounts) {
    return accounts.stream().collect(Collectors.toMap(Account::getUserName, Function.identity(), (key1, key2) -> key2));
}
```

这里只是简单的使用后者覆盖前者来解决key重复问题。

#### 指定具体收集的map

`toMap` 还有另一个重载方法，可以指定一个 `Map` 的具体实现，来收集数据：

```
public Map<String, Account> getNameAccountMap(List<Account> accounts) {
    return accounts.stream().collect(Collectors.toMap(Account::getUserName, Function.identity(), (key1, key2) -> key2, LinkedHashMap::new));
}
```
   
### 参考资料

* [What's New in JDK 8](http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html)
* [深入浅出Java8 lambda表达式](http://blog.oneapm.com/apm-tech/226.html)
* [Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
* [New and Enhanced APIs That Take Advantage of Lambda Expressions and Streams in Java SE 8](https://docs.oracle.com/javase/8/docs/technotes/guides/language/lambda_api_jdk8.html)
* [Default Methods](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)
* [Method References](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html)
* [深入理解Java 8 Lambda（语言篇——lambda，方法引用，目标类型和默认方法）](http://lucida.me/blog/java-8-lambdas-insideout-language-features/)
* [深入理解Java 8 Lambda（类库篇——Streams API，Collectors和并行）](http://lucida.me/blog/java-8-lambdas-inside-out-library-features/)
* [Translation of Lambda Expressions](http://cr.openjdk.java.net/~briangoetz/lambda/lambda-translation.html)
* [https://github.com/winterbe/java8-tutorial](https://github.com/winterbe/java8-tutorial)
* [Java8 Lambda使用与原理](https://www.jianshu.com/p/bde3699f37e5)
* [深入理解Java Stream流水线](http://www.cnblogs.com/CarpenterLee/p/6637118.html)
* [UML类图几种关系的总结](http://www.uml.org.cn/oobject/201609062.asp)
* [Fork/Join框架详解](https://www.cnblogs.com/senlinyang/p/7885964.html)


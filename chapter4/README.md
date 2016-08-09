<!-- $size: 16:9 -->
# 4 열심히보다는 현명하게(Smarter, Not Harder)
* 패러다임을 바꾸면 더 적은 노력으로 더 많은 일을 할 수 있는 득을 보게 된다. 함수형 프로그래밍에서 나타나는 많은 구조들이 그렇다.

---
## 4.1 메모이제이션 (Memoization)
* 메모니제이션이란 연속해서 사용되는 연산 값을 함수 레벨에서 캐시하는 것을 지칭하는 것
	* 시간이 많이 걸리는 연산을 반복적으로 사용해야 하는 경우 유용.
* 캐싱 방법이 제대로 작동하려면 함수가 **순수**해야 한다.
	* 순수함수란 부수효과가 없는 함수를 말한다. 가변 클래스 필드를 참고하지도 않고, 리턴 값 외에는 아무 값도 설정하지 않아야 하며, 주어진 매개변수에만 의존해야 한다.

---
### 4.1.1 캐싱
#### 메서드 레벨에서의 캐싱
* [2장](https://github.com/happy4u/functional_thinking/tree/master/chapter2#222-조금-더-함수적인-자연수-분류기)에 나왔던 자연수 분류 문제에서의 캐시 이슈
* 다음 코드를 보자
```groovy
if (Classifier.isPerfect(n)) print "!"
else if (Classifier.isAbundant(n)) print "+" 
else if (Classifier.isDeficient(n)) print "-"
```
* 이렇게 구현한 경우, 모든 분류 메서드를 호출할 때마다 매개변수의 합을 계산해야 한다.

---
### 4.1.1 캐싱 - cont.
#### 합산 결과를 캐시하기
* 예제 4-1 합을 캐시하기(그루비)
```groovy
class ClassifierCachedSum { 
  private sumCache = [:]
  
  def sumOfFactors(number) {
  if (! sumCache.containsKey(number)) {
  	sumCache[number] = factorsOf(number).sum()
  }
  return sumCache[number] 
}
// remainder of code unchanged...
```

---
### 4.1.1 캐싱 - cont.
#### 합산 결과를 캐시하기
```
버젼							결과
------------------------------------
캐시 안한 버젼				577ms
캐시 안한 버젼(두 번째 실행)	280ms
합을 캐시한 버젼				600ms
합을 캐시한 버젼(두 번째 실행)	50ms
```
* 캐시 전 후를 테스트 해 보면 확실히 효과가 있음을 알 수 있다.
* 이것이 **외부 캐싱**의 예이다.

---
### 4.1.1 캐싱 - cont.
#### 전부 다 캐시하기
* [예제 4-3 전부 다 캐시하기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.1_ex_4-3.groovy)
```
버젼								결과
------------------------------------
전부 다 캐시한 버젼					411ms
전부 다 캐시한 버젼(두 번째 실행)	38ms
```
* 결과는 매우 좋지만 이 방법은 확장성에 문제가 있다. 다음에 보이는 것 처럼 8000까지 테스트를 해 보면 그 결과는...
```
  java.lang.OutOfMemoryError: Java heap space
          at java.util.ArrayList.<init>(ArrayList.java:112)
  ...more bad things...
```
* 이것이 '움직이는 부분'의 적절한 예이다.

---
### 4.1.2 메모이제이션의 첨가 (그루비)
* 메모이제이션을 통해 예제 4-1, 예제 4-3에 살펴본 기능을 자동으로 제공받을 수 있다.
* 그루비에서 함수를 메모아이즈하기 위해서는 함수를 클로저로 정의하고, 리턴 값이 캐시되는 함수를 리턴하는 memoize() 메서드를 실행해야 한다.
* 함수를 메모아이즈하는 것은 **메타함수**를 적용하는 것이라고 할 수 있다. 즉 리턴 값이 아니라 함수에 어떤 것을 적용하는 것이다.
* 3장에서 거론했던 커링도 하나의 메타함수 기법이다.
* [예제 4-4 합을 메모아이즈하기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.1_ex_4-4.groovy)
	* sumFactors() 메서드를 코드 블록으로 만들었다.
```
버젼							결과
------------------------------------
부분적 메모이제이션				411ms
부분적 메모이제이션(두 번째 실행)	38ms
```

---
### 4.1.2 메모이제이션의 첨가 - cont.(그루비)
* [예제 4-5 전부 다 메모아이즈하기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.1_ex_4-5.groovy)
```
버젼							결과
------------------------------------
전체 메모이제이션				956ms
전체 메모이제이션(두 번째 실행)	19ms
```
* 두 번째 실행의 결과는 매우 좋으나, [예제 4-3]의 명령형 캐싱 방법처럼, 많은 수에 대해 실행하면 성능이 급격하게 줄어든다.
* 명령형 기법이 잘 동작하기 위해서는 안전장치와 함께 실행 조건에 대한 조심스런 배려가 필요하다.

---
### 4.1.2 메모이제이션의 첨가 - cont.(그루비)
* 10,000개 수에 대한 메모이제이션 결과는 다음과 같다.
```
버젼									결과
--------------------------------------------
캐시 안한 버젼							41,909ms
캐시 안한 버젼(두 번째 실행)				22,398ms
최대 10000개 메모이제이션				55,685ms
최대 10000개 메모이제이션(두 번째 실행)	98ms
```
* memoize() 대신 memoizedAtMost(1000) 메서드를 호출해 만든 것.

---
### 4.1.2 메모이제이션의 첨가 - cont.(그루비)
* 메모제이션을 지원하는 다른 언어들처럼 그루비도 결과를 최적화하는 몇 가지 메셔드를 가지고 있다.
	* memoise() : 캐싱 형태의 클로저를 만든다.
	* memoizeAtMost() : 최대 크기를 가지는 캐싱 형태의 클로저를 만든다.
	* memoizeAtLeast() : 최소 크기를 가지고 크키글 자동 조정하는 캐싱 형태의 클로저를 만든다.
	* memoizeAtBetween() : 캐시 크기를 최대치와 최소치 사이에서 자동 조정하는 캐싱 형태의 클로저를 만든다.
* 언어가 캐싱을 더 효율적으로 지원하기도 하지만, 개발자는 그런 책임을 런타임에게 양도하고 더 높은 수준에서 추상화된 문제들에 고민해야 한다.
* 수작업으로 캐시를 만드는 것은 간단하다. 하지만 이 방법은 코드에 내부 상태를 더하고 복잡하게 만든다. 함수형 언어의 메모이제이션 같은 기능을 사용하면 함수 레벨에서 캐시를 더할 수 있어서, 코드를 거의 바꾸지 않고도 명령형보다 더 좋은 성능을 얻게 된다.

---
### 4.1.2 메모이제이션의 첨가 - cont.(그루비)
* [예제 4-6 그루비의 인라인 메모이제이션](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.1_ex_4-6.groovy)
	* 코드 블록을 hash 변수에 지정하는 약간 생소한 함수 정의 구문 마지막 부분이 memorize()를 호출한다.
* 예제 4-7 메모아이즈된 해시 함수 테스트
```grrovy
class NameHashTest extends GroovyTestCase { void testHash() {
    assertEquals("ubzre", NameHash.hash.call("homer"))  }
}
```
* 일반적으로 코드 블럭 실행 시 NameHash.hash("Homer")와 같이 호출하지만, 이 케이스는 반드시 함수의 call()을 호출해야 한다.

---
### 4.1.2 메모이제이션의 첨가 - cont.(클로저)
* 클로저에는 메모이제이션이 내장되어 있다.
* (memoize ) 함수로 어떤 함수든지 메모아이즈할 수 있다.
	* (hash ) 함수가 있다고 가정하면 (memoize (hash "homer"))으로 캐싱 버젼을 만들 수 있다.
* [예제 4-8 클로저에서의 메모이제이션](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.1_ex_4-8.clj)
	* 예제 4-7에서 메모아이즈 함수 호출을 위해 call() 메서드를 호출했어야 했건 것과는 다르게, 클로저 버전에서는 메모아이즈된 메서드 호출이 차이가 없다.

---
### 4.1.2 메모이제이션의 첨가 - cont.(스칼라)
* 예제 4-9 스칼라에서의 메모이제이션 구현
```scala
def memoize[A, B](f: A => B) = new (A => B) {
  val cache = scala.collection.mutable.Map[A, B]() 
  def apply(x: A): B = cache.getOrElseUpdate(x, f(x))
}

def nameHash = memoize(hash)
```
* getOrElseUpdate 함수는 캐시를 만드는 데 최적의 도구이다. 이 함수는 매치된 값을 꺼내거나 매치된 값이 없으면 새 값을 생성한다.

---
### 4.1.2 메모이제이션의 첨가 - cont.
* 메모아이즈된 함수는
	* 부수효과가 없어야 하고
	* 외부 정보에 절대로 의존하지 말아야 한다.
* Java 8은 내장된 메모이제이션이 없지만 새로 도입된 람다 기능을 사용하면 쉽게 구현할 수 있다.

---
4.2 게으름(Laziness)


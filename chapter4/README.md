<!-- $size: 16:9 -->
# 4 열심히보다는 현명하게(Smarter, Not Harder)
* 패러다임을 바꾸면 더 적은 노력으로 더 많은 일을 할 수 있는 득을 보게 된다. 함수형 프로그래밍에서 나타나는 많은 구조들이 그렇다.

---
## 4.1 메모이제이션 (Memoization)
* 메모이제이션이란 연속해서 사용되는 연산 값을 함수 레벨에서 캐시하는 것을 지칭하는 것
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
캐시 안한 버젼			577ms
캐시 안한 버젼(두 번째 실행)		280ms
합을 캐시한 버젼			600ms
합을 캐시한 버젼(두 번째 실행)		50ms
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
* 수많은 언어가 이미 메모이제이션과 같은 메커니즘을 사용하여 이러한 제약을 극복해냈다.

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
* 메모아이즈된 버전은 8000개의 수를 실행하다가 메모리가 바닥나고 만다.
* 명령형 기법이 잘 동작하기 위해서는 안전장치와 함께 실행 조건에 대한 조심스런 배려가 필요하다.

---
### 4.1.2 메모이제이션의 첨가 - cont.(그루비)
* 10,000개 수에 대한 메모이제이션 결과는 다음과 같다.
```
버젼									결과
--------------------------------------------
캐시 안한 버젼					41,909ms
캐시 안한 버젼(두 번째 실행)				22,398ms
최대 1000개 메모이제이션				55,685ms
최대 1000개 메모이제이션(두 번째 실행)			98ms
```
* memoize() 대신 memoizedAtMost(1000) 메서드를 호출해 만든 것.

---
### 4.1.2 메모이제이션의 첨가 - cont.(그루비)
* 메모제이션을 지원하는 다른 언어들처럼 그루비도 결과를 최적화하는 몇 가지 메서드를 가지고 있다.
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
## 4.2 게으름(Laziness)
* 표현의 평가를 가능한 최대로 늦추는 기법인 게으른 평가(Lazy evaluation)는 함수형 프로그래밍 언어에서 많이 볼 수 있는 기능이다.
* 게으른 컬렉션은 그 요소들을 한꺼번에 미리 연산하는 것이 아니라, 필요에 따라 하나씩 전달해준다. 이는 몇 가지 이점이 있다.
	1. 시간이 많이 걸리는 연산을 반드시 필요할 때까지 미룰 수 있게 된다.
	2. 요청이 계속되는 한 요소를 계속 전달하는 무한 컬렉션을 만들 수 있다.
	3. 맵이나 필터 같은 함수형 개념을 게으르게 사용하면 효율이 높은 코드를 만들 수 있다.

---
## 4.2 게으름(Laziness) - cont.
* 자바는 버젼 8 이전까지는 게으름을 지원하지 않았지만 몇몇 프레임워크나 파생 언어는 지원한다.
* 예제 4-10 관대한 평가를 보여주는 의사코드
```
print length([2+1, 3*2, 1/0, 5-4])
```
* 이 코드를 실행해보면 프로그래밍 언어가 엄격한지(strict) 혹은 관대한지(nonstrict)에 따라 결과가 다를 것이다.
	* 엄격한 경우 divByZero 예외 발생
	* 관대한 경우 결과가 4로 나옴.
* 하스켈은 많이 사용되는 관대한 언어 중 하나. 자바는 관대한 평가를 지원하지는 않지만, 평가를 연기하면 게으름을 사용할 수 있다. 차세대 언어들은 기본적으로 자바보다 게으르다.

---
### 4.2.1 자바의 게으른 반복자
* 게으름을 만들려면 자료구조부터 그 개념을 지원해야 한다.
* [예제 4-11 자바로 소수 찾기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-11.java)
* 자바는 게으른 컬렉션을 네이티브로 지원하지 않지만, Iterator를 사용하면 흉내를 낼 수는 있다.
	* hasNext() : 소수가 무한이 많기 때문에 가능.
* 예를 들어 [예제 4-11]의 도우미를 사용하여, 부를 때마다 다음 소수를 리턴하는 반복자를 [예제 4-12]에서 만들어 봤다.
* [예제 4-12 자바를 사용한 소수 반복자](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-12.java)
	* 일반적으로 개발자들은 반복자(iterators)가 컬렉션을 백업 저장소로서 사용한다고 생각하지만, Iterator 인터페이스를 지원하는 것은 어떤 것이든 가능하다.


---
### 4.2.2 토털리 레이지 자연수 분류기 (Totally Lazy Number Classifier)
* [2장에서 다룬 자연수 분류기](https://github.com/happy4u/functional_thinking/tree/master/chapter2#221-명령형-자연수-분류) 참고
* [토털리 레이지](https://totallylazy.com/)는 장황하게나마 자바 구문을 함수형 메커니즘 쪽으로 바꿔주는 자바 프레임워크이다.
	* 간결하고 함수형인 자바 코드를 짜려면 자바 8로 업그레이드해야만 가능하다고 생각할지도 모르겠다. 이전 버젼의 자바에 고계함수를 끼워 맞추기는 불가능하지만, 몇몇 프로임워크들은 제네릭, 익명 클래스, 정적 임포트를 교묘하게 사용하여 앞에 살펴본 혜택을 누려왔다.
* [예제 4-13 토털리 레이지 자바 프레임워크를 사용한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-13.java)
	(1) 프레임워크가 remainder 같은 함수와 where 같은 술어를 제공한다.
	* 토털리 레이지는 연산자 오버로딩 없는 자바의 구문적 제약 조건 아래 자바를 더 좋게 만들어야 하기 때문에 적당한 메서드들을 첨가해야 한다. 따라서 num % i = 0이 where(remainder(n), is(zero))로 바뀐다.
	* 토털리 레이지는 자바의 정적 임포트를 사용하여 읽기 쉬운 코드를 짜는데 좋다. 또한 가능한 한 많은 연산을 지연시킴으로써 게으름을 적극적으로 사용한다.


---
### 4.2.3 그루비의 게으른 목록
* 함수형 언어의 대표적인 기능은 내용물을 필요할 때만 만들어내는 게으른 목록이다.
* 게으른 목록은 초기화하기 어려운 리소스를 반드시 필요할 때까지 지연할 수 있게 해준다.
* [예제 4-14 그루비에서의 게으른 목록 사용법](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-14.groovy)
	* perpend() : 값을 앞에 붙일 수 있는 LazyList를 만든다.
		* 두 매개변수는 목록의 초기 값과 다음 값을 생성하는 코드 블록이다.
	* integers() : 맨 앞에 값이 하나만 주어지고 나머지 수를 생성하는 방식으로, 게으른 수의 목록을 만들어내는 공장처럼 작동한다.
	* 이 목록에서 값을 꺼내려면 요구한 개수의 값을 리턴하는 getHead() 메서드를 사용해야 한다.
* [LazyList 구현](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-15.groovy)

---
### 4.2.4 게으른 목록 만들기
* 그루비는 근본적으로는 엄격한 언어이지만 클로저 내부에 엄격한 목록을 재귀적으로 포함해서 게으르지 ㅇ낳은 목록을 게으른 목록으로 변형할 수 있다.
* 그루비에서 속이 비어 있는 엄격한 목록은 빈 대괄호([])를 써서 배열로 표현된다. 이것을 클로저에 감싸면 속이 빈 게으른 목록이 된다.
  ```
  { -> [ ] }
  ```
* 이 목록에 요소를 더하려면, 요소를 앞에 더해서 새로운 게으른 목록을 만들 수 있다.
  ```
  { -> [ a , { -> [ ] } ] }
  ```
* 전통적으로 목록의 앞에 요소를 더하는 메서드를 prepand나 cons라 부른다. 더 많은 요소를 더하고 싶으면 새 요소마다 이 작업을 반복하면 된다.
* 다음은 a, b, c 세 요소를 목록에 더하는 방법이다.
  ```
  { -> [ a , { -> [ b , { -> [ c , { -> [ ] } ] } ] } ] }
  ```
 
---
### 4.2.4 그루비의 게으른 목록 - cont.
* [예제 4-18 그루비에서 게으른 목록 만들기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-18.groovy) 
	* (1) 생성자는 비공개이며, nil()로 속이 빈 목록을 생성
    * (2) cons() 메서드를 사용하면 주어진 매개변수를 새 요소로 앞에 덧붙이고 그 결과를 클로저 블록으로 감쌀 수 있게 된다.
	* 다음 세 메서드를 사용하면 목록 순회가 가능
		* head() : 목록의 첫 요소를 리턴
		* tail() : 첫 요소를 제외한 모든 요소를 포함하는 부분목록을 리턴
		* 두 경우 모두, 게으른 항들을 그 자리에서 평가하기 위해 클로저 블록을 call()해야 한다. 이 경우에 클로저 블록을 의도적으로 실행해서 값을 받아오기 때문에 이 클로저 블록은 더 이상 게으르다라고 할 수 없다.
		* empty() : 계산할 항들이 아직 남아 있는지를 확인

---
### 4.2.4 그루비의 게으른 목록 - cont.
* 예제 4-19 게으른 목록 작동해보기
  ```
  def lazylist = PLazyList.nil().cons(4).cons(3).cons(2).cons(1) 
  println(lazylist.takeAll())
  println(lazylist.foldAll(0, {i, j -> i + j}))
  // [1, 2, 3, 4]
  // 10

  lazylist = PLazyList.nil().cons(1).cons(2).cons(4).cons(8) 
  println(lazylist.take(2))
  // [8, 4]
  ```
  * takeAll() : 모든 요소를 더해진 순서의 역순으로 리턴
  * foldAll() : 주어진 변형 코드 블록{i, j -> i+j}을 통한 합을 구할 수 있다.
* 현실적인 게으른 목록은 이것과는 다르게 재귀를 피하고 융통성 있는 메서드들을 덧붙여서 구현된다.
* 하지만 내부의 구현 개념을 알면 게으른 목록을 이해하고 사용하는 데에도 도움이 될 것이다.

---
### 4.2.5 게으름의 이점 (그루비)
* 게으른 목록의 이점
	1. 무한 수열을 만들 수 있다.
	2. 저장 시 크기가 줄어든다. 컬렉션 전부를 유지하지 않고 순차적으로 다음 값을 유도할 수 있으니 저장소와 실행 속도를 맞바꿀 수 있다.
	3. 런타임이 좀 더 효율적인 코드를 만들 수 있다.
* [예제 4-20 그루비를 사용한 회문 찾기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-20.groovy) 
	* isPalindorome() : 주어진 단어를 소문자로 정규화하고, 그 단어의 글자들을 역순으로 나열해도 원래 단어와 똑같은지를 확인
	* findFirstPalindrome() : find() 메서드를 사용해 첫번째 회문 단어를 찾으려 시도
	* 아주 긴 문자열에서 첫 번째 회문을 찾아야 할 경우를 가정해 보면, tokenize() 메서드는 게으르지 않이 때문에 이 경우에는 금방 버려질 **엄청나게 큰 임시 자료구조**가 만들어질 수도 있다.

---
### 4.2.5 게으름의 이점 - cont. (클로저)
* 예제 4-20과 예제 4-21은 같은 내용을 다른 언어 구조를 사용하여 구현한 것.
* [예제 4-21 클로저를 사용한 회문 찾기](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-21.clj)
	* (palindrome? ) : 주어진 문자열을 소문자로 바꾸고 역순으로 된 문자열과 동일한지 확인
		* apply : 앞뒤가 뒤바뀐 문자의 수열을 비교를 돕기 위해 문자열로 바꿔준다.
	* (find-palindromes ) : 필터로 사용할 함수와 필터의 대상이 될 컬렉션을 매개변수로 받는 클로저의 (filter )함수를 사용
	* 그루비에서 클로저로 바뀌는 과정은 단순한 구문 변화 이상을 의미한다.
	* 클로저 버젼에서는 모든 것이 자동적으로 게으르게 만들어진다.
	* (filter ) 함수는 출력 시에만 적극적으로 평가되는 게으른 컬렉션을 리턴한다.

---
### 4.2.5 게으름의 이점 - cont. (스칼라)
* 스칼라는 약간 다른 방법으로 게으름에 접근한다.
* 예제 4-22 스칼라를 사용한 회문 찾기
  ```scala
  def isPalindrome(x: String) = x == x.reverse
  def findPalidrome(s: Seq[String]) = s find isPalindrome

  findPalindrome(words take 1000000)
  ```
  * 첫 번째 회문을 찾는 것이 목적일 경우 take 메서드로 백만 개의 단어를 끄집어내 찾는 것은 아주 비효율적인 일일 것이다.
  * 단어 컬렉션을 게으르게 변환하려면 view 메서드를 사용하면 된다.
  ```
  findPalindrome(words.view take 1000000)
  ```
  * view 메서드는 컬렉션을 게으르게 순회하게 하여 코드의 효율을 높인다.

---
### 4.2.6 게으른 필드 초기화
* 두 가지 언어가 비용이 큰 초기화를 게으르게 만드는 데 좋은 기능을 제공한다는 것을 짚고 가겠다.
* 스칼라에서는 val을 선언하는 곳 앞에 lazy를 사용하면 필드를 적극적 평가에서 적시 평가로 간단히 바꿀 수 있다.
  ```
  lazy val x = timeConsumingAndOrSizableComputation()
  ```
  * This is basically syntactic sugar for the code:
  ```
  var _x = None
  def x = if (_x.isDefined) _x.get else {
  	_x = Some(timeConsumingAndOrSizableComputation())
  	_x.get 
  }
  ```

---
### 4.2.6 게으른 필드 초기화 - cont.
* 그루비에도 유사한 기능이 있다. 이 기능은 ** 추상 구문 트리 Abstract Syntax Tree (AST)** 변형이라는 고급 언어 기능을 사용한다.
* [예제 4-23 그루비의 게으른 필드](https://github.com/happy4u/functional_thinking/blob/master/chapter4/4.2_ex_4-23.groovy)
	* Person의 인스턴스 p는 처음 사용되기 전까지는 Cat의 값이 정해지지 않는다.
	
* 클로저 블록을 사용한 자료구조의 값의 초기화를 지원
  ```
  class Person {
      @Lazy List pets = { /* complex computation here */ }()
  }
  ```

* 게으르게 초기화된 필드를 소프트 레퍼런스에 유지하게 만들기
  ```
  class Person {
      @Lazy(soft = true) List pets = ['Cat', 'Dog', 'Bird']
  }
  ```
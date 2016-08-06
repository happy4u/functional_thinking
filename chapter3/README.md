<!-- $size: 16:9 -->
#  3 양도하라 (Cede)
* 컴퓨터의 성능이 향상됨에 따라서 우리는 더 많은 일들을 언어나 런타임에 떠넘겨왔다.
* 가비지 컬렉션 같은 저 수준 세부사항의 조작을 런타임에 양도함으로써 찾아야 할 수많은 오류를 방지해주는 능력이야말로 함수형 사고의 가치라고 하겠다.
* 대다수의 개발자들은 메모리와 같은 기본 추상적 개념을 문제없이 무시하는 데 익숙하겠지만, 더 높은 단계에서 나타나는 추상화는 낯설어한다.
* 이 장에서는 함수형 언어를 사용하는 개발자가 언어나 런타임에 제어를 양도하는 다섯 가지 방식을 살펴보겠다.

---
## 3.1 반복 처리에서 고계함수로
* [예제 2-3](https://github.com/happy4u/functional_thinking/tree/master/chapter2#212-함수형-처리---cont)에서 반복 처리 대신에 map과 같은 함수를 사용하여 제어를 포기하는 법을 보여주었다. 고계함수 내에서 어떤 연산을 할 것인지를 표현하기만 하면, 언어가 그것을 능률적으로 처리할 것이다. 게다가 par라는 변형자를 덪붙히기만 하면 분산처리까지 해준다.
* 그렇다고 개발자가 저수준 추상 단계에서 코드가 어떻게 동작하는지 이해하는 것까지 전부 떠 넘겨도 된다는 것은 아니다.

---
## 3.2 클로저
* 클로저란 그 내부에서 참조되는 모든 인수에 대한 묵시적 바인딩을 지닌 함수를 칭한다. 다시 말하면 이 함수(또는 메서드)는 자신이 참조하는 것들의 문맥을 포함한다.

---
## 3.2 클로저 - cont.
* 예제 3-1 그루비에서의 간단한 클로저 바인딩
```
package com.nealford.ft.simple_closure

class Employee { 
	def name, salary
}

def paidMore(amount) {
	return {Employee e -> e.salary > amount}
}

isHighPaid = paidMore(100000)
```
* paidMore 함수의 리턴값은 **클로저**라는 코드 블록이다.
* 100,000을 매개변수로 주고 isHighPaid란 인수에 할당하면 100,000의 값은 이 코드 블록에 영원히 **바인딩**된다.

---
## 3.2 클로저 - cont.
* 예제 3-2 클로저 블록의 실행
```
def Smithers = new Employee(name:"Fred", salary:120000) 
def Homer = new Employee(name:"Homer", salary:80000) 
println isHighPaid(Smithers)
println isHighPaid(Homer)
// true, false
```
* 클로저가 생성될 때에, 이 코드 블록의 스코프에 포함된 인수들을 둘러싼 상자가 같이 만들어진다. 그래서 이름도 **클로저**
* 이 코드 블록의 모든 인스턴스는 전용 인수조차도 각자 고유한 값을 가진다.

---
## 3.2 클로저 - cont.
* 다른 값을 바인딩해서 paidMore 클로저를 하나 더 만들 수도 있다.
* 예제 3-3 또 다른 클로저 바인딩
```
isHigherPaid = paidMore(200000)
println isHigherPaid(Smithers)
println isHigherPaid(Homer)
def Burns = new Employee(name:"Monty", salary:1000000) println isHigherPaid(Burns)
// false, false, true
```

---
## 3.2 클로저 - cont.
* 클로저는 함수형 언어나 프레임워크에서 코드 블럭을 다양한 상황에서 실행하게 해주는 메커니즘으로 많이 쓰인다.
* 함수형 자바에서는 익명 내부 클래스를 사용하여 '진정한' 클로저의 기능을 흉내 내지만 버젼 8 이전의 자바는 클로저를 지원하지 않았기 때문에 완벽하지는 않다.

---
## 3.2 클로저 - cont.
* [예제 3-4 그루비에서 클로저의 작동 원리](https://github.com/happy4u/functional_thinking/blob/master/chapter3/3.2_ex_3-4.groovy)
	1. 이 함수의 리턴 값은 값이 아니라 코드 블록이다.
	2. c1은 이제 이 코드 블록의 인스턴슬르 가리킨다.
	3. c1을 불러오면 내부의 인수가 증가한다. 이때 이 표현을 평가하면 1이 나온다.
	4. c2는 makeCounter()의 새로운 인스턴스를 가리킨다.
	5. 각각의 인스턴스는 local_variable에 다른 내부 상탤르 지니고 있다.
* local_variable 이 지역 인수는 함수 내부에서 정의되었지만, 코드 블럭이 이 인수에 바인딩되어 있고, 따라서 코드 블록이 존재하는 동안에 이 인수 값은 유지되어야 한다.

* [예제 3-5 자바로 구현한 makeCounter()](https://github.com/happy4u/functional_thinking/blob/master/chapter3/3.2_ex_3-5.java) 

---
## 3.2 클로저 - cont.
* 클로저는 **지연 실행**(deferred execution)의 좋은 예이다. 클로저 블록에 코드를 바인딩함으로써 그 블록의 실행을 나중으로 연기할 수 있다.
	* 이 기능은 여러모로 쓸모가 있는데, 예를 들어 클로저 블록을 정의할 때는 필요한 값이나 함수가 스코프에 없지만, 나중에 실행 시에는 있을 수가 있다.
* 명령형 언어는 **상태**로 프로그래밍 모델을 만든다. 그 좋은 예가 매개변수를 주고받는 것이다. 
* 클로저는 코드와 문맥을 한 구조로 캡슐화해서 **행위**의 모델을 만들 수 있게 해준다.

---
## 3.3 커링과 부분 적용
* 커링과 부분 적용은 20세기 수학자인 해스컬 커리 등의 작업을 통해 수학에서 유래한 언어 기술이다.
* 커링이나 부분 적용은 함수나 메서드의 인수의 개수를 조작할 수 있게 해준다.

---
### 3.3.1 정의와 차이점
* 커링 : 다인수 함수를 일인수 함수들의 체인으로 바꿔주는 방법
* 부분 적용 : 다인수 함수를 생략될 인수의 값을 미리 정해서 더 적은 수의 인수를 받는 하나의 함수로 변형하는 방법

* ex>
	* 커링(완전히 커링된 버젼) : process(x,y,z) -> process(x)(y)(z)
	* 부분적용(인수 하나를 부분적용) : process(x,y,z) -> process(y,z)
* 이 두 가지 방법은 종종 같은 결과를 낳는다. 하지만 이 중요한 차이점이 자주 오해되곤 한다.
* 설상가상으로 그루비는 이 두 방법을 모두 지원함녀서 둘 다 커링이라고 부른다 --;;
* 스칼라는 부분 적용과 PartialFunction 클래슬를 모두 지원하는데 이 두가지는 이름은 비슷하지만 별개의 개념이다.

---
### 3.3.1 정의와 차이점 - cont. (그루비)
* 그루비는 커링을 Closure 클래스의 curry() 함수를 사용하여 구현
* 예제 3-6 그루비에서의 커링
```
defproduct={x,y->x*y} 						

def quadrate = product.curry(4)				// (1)
def octate = product.curry(8)  				// (2)

println "4x4: ${quadrate.call(4)}"			// (3)
println "8x5: ${octate(5)}"				// (4)
```
* 설명
	1. curry()가 매개변수 하나를 고정하고 일인수 함수를 리턴
	2. octate() 함수는 항상 주어진 매개변수의 8배수를 리턴
	3. quadrate() 함수는 일인수 함수이고 Closure 클래스의 call() 메서드를 통해서 호출이 가능
	4. 그루비에서는 호출을 간편하게 해주는 문법적 설탕(syntactic sugar)이 있다.
* 이름과는 달리 curry()는 밑에 깔린 클로저 블록을 조작하는 부분 적용으로 구현되어 있다.

---
### 3.3.1 정의와 차이점 - cont. (그루비)
* 예제 3-7 그루비에서의 부분 적용과 커링
```
defvolume={h,w,l->h*w*l} 
def area = volume.curry(1)
def lengthPA = volume.curry(1, 1)			// (1)
def lengthC = volume.curry(1).curry(1)			// (2)

println "The volume of the 2x3x4 rectangular solid is ${volume(2, 3, 4)}"
println "The area of the 3x4 rectangle is ${area(3, 4)}"
println "The length of the 6 line is ${lengthPA(6)}"
println "The length of the 6 line via curried function is ${lengthC(6)}"
```
* (1) 부분적용
* (2) 커링

---
### 3.3.1 정의와 차이점 - cont. (그루비)
* 6장에서 다룰 **구성**은 함수형 언어에서 흔히 쓰이는 조합 기술이다.
* 예제 3-8 그루비에서의 복합함수 만들기
```
def composite = { f, g, x -> return f(g(x)) }
def thirtyTwoer = composite.curry(quadrate, octate)

println "composition of curried functions yields ${thirtyTwoer(2)}"
// composition of curried functions yields 64
```

---
### 3.3.1 정의와 차이점 - cont. (클로저)
* 클로저에는 (partial f a1 a2 ...) 함수가 있다. 
* 이 함수는 함수 f와 필요한 인수보다 적은 수의 인수를 받아서 부분 적용 함수를 리턴한다.
* 예제 3-9 클로저의 부분 적용
```
(def subtract-from-hundred (partial - 100)) 

(subtract-from-hundred 10) ; same as (- 100 10)
; 90

(subtract-from-hundred 10 20) ; same as (- 100 10 20) 
; 70
```
* 클로저에서는 연산자와 함수의 구분이 없다. (-가 f를 의미)

---
### 3.3.1 정의와 차이점 - cont. (스칼라)
* 스칼라는 제약이 있는 함수를 정의할 수 있는 트레이트와 함께 커링과 부분 적용을 모두 지원한다.

#### 커링
* 스칼라에서는 여러 개의 인수 목록을 여러 개의 괄호로 정의할 수 있다.
* 함수를 정해진 인수의 수보다 적은 인수로 호출하면 그 리턴 값은 나머지 인수를 받는 함수이다.
* [예제 3-10 스칼라의 인수 커링](https://github.com/happy4u/functional_thinking/blob/master/chapter3/3.3_ex_3-10.scala) 
	* filter() 함수는 재귀적으로 주어진 필터 조건을 적용
	* modN() 함수는 두 개의 인수 목록으로 정의된다.
	* modN()을 filter()의 인수로 호출할 때에는 인수 하나를 전달한다.
	* 여기서 filter()함수는 두 번째 인수로 Int 인수를 받아 Boolean을 리턴하는 함수를 받는다.
	* 이 함수의 시그니처는 여기서 전달된 커링된 함수의 시그니처와 같다.

---
### 3.3.1 정의와 차이점 - cont. (스칼라)
#### 부분 적용 함수
* [예제 3-11 스칼라에서의 부분 적용](https://github.com/happy4u/functional_thinking/blob/master/chapter3/3.3_ex_3-10.scala) 
	* 제품과 가격 사이의 매핑을 리턴하는 price() 함수를 만든다.
	* 비용과 판매 지역인 주(state)를 인수로 받는 withTax() 함수를 만든다.
	* 부분 적용 함수를 이용 state값은 'NY'로 고정된 고정함수를 locallyFixed()는 비용 하나만을 인수로 받는다.

---
### 3.3.1 정의와 차이점 - cont. (스칼라)
#### 부분 (제약이 있는) 함수
* 스칼라의 PartialFunction 트레이트는 6장에서 자세히 다룰 패턴 매칭에 자연스럽게 사용하려고 설계된 것이다.
* 이름은 비슷하지만 PartialFunction은 부분 적용 함수를 생성하지는 않는다. 대신에 **특정한 값이나 자료형에만 적용되는 함수**를 만드는데 이것을 사용할 수 있다.
* 예제 3-12 match 없이 case 사용하기
```
val cities = Map("Atlanta" -> "GA", "New York" -> "New York",
  "Chicago" -> "IL", "San Francsico " -> "CA", "Dallas" -> "TX")
  
cities map { case (k, v) => println(k + " -> " + v) }
```
* 도시와 주의 관계를 표현하는 맵을 만든다
* map() 함수를 컬렉션에 적용하면 map() 함수가 키와 값을 떼어내서 출력한다.

---
### 3.3.1 정의와 차이점 - cont. (스칼라)
#### 부분 (제약이 있는) 함수
* 스칼라에서는 case 명령문이 포함된 블록으로 익명함수를 정의할 수 있다.
* 사실 case를 사용하지 않고도 익명함수를 정의할 수 있지만, 예제 3-13과 같은 이점이 있다.
* 예제 3-13 map과 collect의 차이점
```
List(1, 3, 5, "seven") map { case i: Int ? i + 1 } // 동작하지 않음
// scala.MatchError: seven (of class java.lang.String)

List(1, 3, 5, "seven") collect { case i: Int ? i + 1 }

// 검증
assert(List(2, 4, 6) == (List(1, 3, 5, "seven") collect { case i: Int ? i + 1 }))
```
* case 블록은 부분 적용 함수(partially applied functions)가 아니라 부분함수(artial functions)를 정의한다.
* 부분함수는 허용되는 값의 범위가 국한되어 있다.
* 예제 3-13에서 collect()를 부를 때 case가 정수에는 정의되어 있지만 문자열에는 정의되지 않았기 때문에 "seven" 문자열은 collect되지 않은 것이다.

---
### 3.3.1 정의와 차이점 - cont. (스칼라)
#### 부분 (제약이 있는) 함수
* [예제 3-14 스칼라에서 부분함수 정의하기](https://github.com/happy4u/functional_thinking/blob/master/chapter3/3.3_ex_3-14.scala)
	* PartialFunction 트레이트로 answerUnits를 유도해내고, 두 함수를 정의
	* apply() 함수는 리턴 값을 계산
	* PartialFunction의 정의에 꼭 필요한 isDefinedAt() 함수는 인수가 적당한지를 결정하는 조건으로 사용한다.

* [예제 3-15 answerUnits 다르게 정의하기](https://github.com/happy4u/functional_thinking/blob/master/chapter3/3.3_ex_3-15.scala)
	* case와 방호 조건(guard condition)을 함께 사용하여 인수를 제한하고 결과를 리턴한다.
	* 여기서 한 가지 눈여겨볼 것은 이 경우에는 패턴 매칭을 사용했기 때문에 ArithmeticExcetion이 아니라 MatchError가 발생한다.


---
### 3.3.1 정의와 차이점 - cont. (스칼라)
#### 부분 (제약이 있는) 함수
* 부분함수는 수치형 자료형에만 국한된 것이 아니다
* Any를 포함한 모든 자료형에 사용할 수 있다.
* [예제 3-16 스칼라의 증가 함수](https://github.com/happy4u/functional_thinking/blob/master/chapter3/3.3_ex_3-16.scala)
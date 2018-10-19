<!-- $size: 16:9 -->
# 6 전진하라(Advance)
* 사용하는 언어의 주된 패러다임이 객체지향이라면, 모든 문제의 해법을 객체지향적으로 찾게 마련이다.
* 하지만 대부분의 현대 언어들은 다중 패러다임을 갖고 있다. 다시 말하자면 이 언어들은 객체지향형, 메타객체형, 함수형 등의 패러다임을 다 지원한다.
* 문제에 적합한 패러다임을 사용하는 법을 배우는 것이 더 좋은 개발자로 진화하는 길 중의 하나이다.

---
## 6.1 함수형 언어의 디자인 패턴
* 함수형 언어계의 어떤 이들은 디자인 패턴이 개념 자체에 결함이 있기 때문에 함수형 프로그래밍에서는 필요가 없다고 주장한다. 이런 주장은 패턴의 사용보다는 의미론에 국한된 것이다.
* 함수형에서는 빌딩블록과 문제의 접근 방법이 다르기 때문에, 전통적인 GoF 패턴들 중의 일부는 사라지고, 나머지는 근본적으로 다른 방법으로 같은 문제를 풀게 된다.

* 함수형 프로그래밍에서는 전통적인 디자인 패턴들이 다음과 같은 세 가지로 나타난다.
	* 패턴이 언어에 흡수된다.
	* 패턴 해법이 함수형 패러다임에도 존재하지만, 구체적인 구현 방식은 다르다.
	* 해법이 다른 언어나 패러다임에 없는 기능으로 구현된다. (예를 들어 메타프로그래밍을 사용한 해법들은 깔끔하고 멋있다. 이런 해법은 자바에서는 불가능하다.)


---
## 6.2 함수 수준의 재사용
* 구성(Composition)은 함수형 프로그래밍 라이브러리에서 재사용의 방식으로 자주 사용된다. 함수형 언어들은 객체지향 언어들보다 더 큰 단위로 재사용을 한다. 그러기 위해서 커스터마이즈되는 공통된 작업들을 추출해낸다.
* [예제 2-12](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.3_ex_2-12.java)의 목록을 필터하는 함수형 접근 방법은 모든 함수형 언어나 라이브러리에 흔하게 존재한다.
* filter() 메서드에서처럼 코드를 매개변수로 전달하는 기능은 코드 재사용의 다른 접근 방법을 제시해준다. 전통적인 디자인 패턴을 사용하는 객체지향의 관점에서 볼 때는 클래스나 메서드를 만들어서 문제를 푸는 방식이 더 편해 보일 수도 있다.
* 예를 들어 클로저를 지원하는 언어에서는 커맨드 디자인 패턴이 필요 없다. 궁극적으로 디자인 패턴의 존재 목적은 언어의 결함을 매꾸기 위함일 뿐이다. 물론 커맨드 디자인 패턴은 실행취소(undo) 같은 다른 상황에도 유용하다. 하지만 이 패턴은 실행 가능한 코드를 메서드에 전달하는 방법으로서 주로 사용된다.

---
### 6.2.1 템플릿 메소드 (SKIP)
* [예제 6-1 템플릿 메서드의 '표준' 구현](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-1.groovy)
	* process() 메서드는 checkCredit(), checkInventory(), ship()에 의존한다.

* [예제 6-2 일급 함수를 사용한 템플릿 메서드](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-2.groovy)
	* [예제 6-2]에서 알고리즘의 각 단계는 클래스에 할당할 수 있는 성질에 불과하다. 이것이 상세한 구현 방법을 언어의 기능으로 감추는 일례다.(danny's - 개인적으로 잘 이해는 안감)


---
### 6.2.2 전략
* [예제 6-4 전략 패턴을 사용한 두 숫자의 곱셈](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-4.groovy)
	* 두 수의 곱을 인터페이스로 정의하고, 곱셈과 덧셈을 각각 사용한 두 가지 구체 클래스를 구현

* [예제 6-5 곱 전략 테스트하기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-5.groovy)
* [예제 6-6 보일러플레이트 코드를 제거한 지수 계산 테스트](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-6.groovy)
	* [보일러플레이트](http://terms.co.kr/boilerplate.htm) : 원 뜻만 보면 재사용 가능한 좋은 의미 같지만, 여기서는 java에서 getter, setter만드는 것과 같은 코드들을 지칭함
	* 코드 블록을 일급함수로 사용하여, 이전 예제에서의 보일러플레이트 코드의 대부분을 제거할 수 있다.
* 전통적인 방법은 각 전략에 이름과 구조를 정해야 하고, 이런 방법이 바람직한 경우도 있다. 전통적인 방법을 사용하면 거기에 따르는 제약조건을 어길 수가 없지만, [예제 6-6]의 코드에는 개발자가 임의로 그런 제약을 더해서 안정성을 향상할 수 있었다.
* 이것은 함수형 프로그래밍과 디자인 패턴의 논의가 아니라 **동적 언어와 정적 언어의 논의**라 하겠다.

---
### 6.2.3 플라이웨이트 디자인 패턴과 메모이제이션
* [플라이웨이트 패턴](http://goo.gl/sFtKok)은 많은 수의 조밀한 객체의 참조들을 공유하는 최적화 기법
* [예제 6-7 컴퓨터 종류를 모델링한 간단한 클래스](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-7.groovy)
	* AssignedComputer는 컴퓨터와 사용자를 연계해준다.
	* 이 코드를 효율적으로 만드는 보편적인 방법은 팩토리 패턴과 플라이웨이트 패턴을 같이 사용하는 것이다.
* [예제 6-8 컴퓨터의 플라이웨이트 인스턴스를 만드는 싱글턴 클래스](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-8.groovy)
	* ComputerFactory 클래스는 가능한 모든 종류의 컴퓨터의 캐시를 만들고, 적당한 인스턴스를 ofType() 메서드를 통해서 전달. 자바에서 흔히 볼 수 있는 전형적인 싱글턴 팩토리이다.
* 싱글턴의 사용은 패턴을 런타임에 양도하는 또 하나의 좋은 예를 보여준다. [예제 6-9]에서는 그루비가 지원하는 @Singleton 애너테이션을 사용하여 ComputerFactory를 더 단순화 해 보자


---
### 6.2.3 플라이웨이트 디자인 패턴과 메모이제이션 - cont.
* [예제 6-9 단순화한 싱글턴 팩토리](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-9.groovy)
* 4장에서 본, 메모아이즈 한수를 이용해 런타임이 값을 캐시 할 수 있게 해 보자
* [예제 6-11 플라이웨이트를 메모아이즈하기]
  ```groovy
  def computerOf = {type ->
    def of = [MacBookPro6_2: new Laptop(), SunTower: new Desktop()] 
    return of[type]
  }

  def computerOfType = computerOf.memoize()
  ```
* [예제 6-12]에서 두 접근 방식을 비교하는 유닛 테스트를 볼 수 있다.
* [예제 6-12 접근 방식의 비교](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-12.groovy)
	* 결과는 같지만 상세한 구현의 큰 차이를 볼 수 있다.
      * 전통적인 디자인 패턴 : 두 개의 메서드를 구현한 새 클래스를 팩토리로 사용
      * 함수형 버젼 : 하나의 매서드를 구현한 후에 메모아이즈된 버젼을 리턴
	* 캐싱처럼 세부적 사항을 런타임에 맡기면 직접 구현한 코드가 실패할 기회가 적어진다.

---
### 6.2.4 팩토리와 커링
* 디자인 패턴 차원에서 보면, 커링은 함수의 팩토리처럼 사용된다.
* 함수형 프로그래밍 언어에서 보편적인 기능은 함수를 여느 자료구조처럼 사용할 수 있게 해주는 일급 함수들이다.
* 이 기능 덕분에 주어진 조건에 따라 다른 함수들을 리턴하는 함수를 만들 수 있다. 이것이 사실상 팩토리의 본질이다.
* [예제 6-13 함수 팩토리로 사용되는 커링]
  ```groovy
  defadder={x,y->x+y} 
  def incrementer = adder.curry(1)

  println "increment 7: ${incrementer(7)}"
  ```
  * 첫 매개변수를 1로 커링하여, 변수 하나만 받는 함수를 리턴한다. 실질적으로 함수 팩토리를 만든 셈이다.

---
### 6.2.4 팩토리와 커링 - cont.
* [예제 6-14 스칼라에서의 재귀적 필터링](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.2_ex_6-14.scala)
	(1) 커링할 함수를 정의한다.
    (2) filter는 컬렉션(nums)과 일인수 함수(커링된 dividesBy() 함수)를 매개변수로 받는다.
* 이 예제는 함수형 프로그래밍에서의 패턴의 두 가지 형태를 보여준다.
	* 첫째, 커링이 언어나 런타임에 내장되어 있기 때문에, 함수 팩토리의 개념이 이미 녹아들어 있어 다른 구조물이 필요 없다.
    * 둘째, 다양한 구현 방법에 대한 중요성을 보여준다. 대부분의 자바 개발자들에게는 [예제 6-14]처럼 커링을 사용하는 것은 생소할 것이다. 그들은 같은 코드를 여기저기 쉽게 사용할 수 없을 뿐 아니라, 일반화된 함수에서 특정한 함수를 만들어낼 생각조차 할 수 없었을 것이다.
> 일반적 함수에서 특정한 함수를 만들 때는 커링을 사용하라

---
## 6.3 구조형 재사용과 함수형 재사용 (Structural Versus Functional Reuse)
* [예제 6-15 명령형 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-15.java)
* [예제 6-16 명령형으로 소수 찾기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-16.java)
* 위 두 가지 예제의 중복 코드를 제거해보자

#### 중복 코드를 제거하는 리펙토링
* [예제 6-17 리팩토링한 공통 코드](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-17.java)
	* 두 개의 메서드 모두 number 멤버 변수를 사용하기 때문에, 상위 클래스로 이동

---
## 6.3 구조형 재사용과 함수형 재사용 (Structural Versus Functional Reuse) - cont.
* [예제 6-18 리팩토링으로 간단해진 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-18.java)
* [예제 6-19 리팩토링으로 간단해진 소수 찾기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-19.java)
* 리팩토링 시 number 변수의 접근 방식을 어떻게 정하든 이 문제에서는 커플링된 여러 개의 클래스를 다뤄야 한다. 이것은 문제의 부분들을 분리해주기 때문에 종종 이롭기도 하지만, 상위 클래스를 바꾸면 하위 클래스에도 영향을 주는 문제점이 있다.
* 이것이 **커플링**을 통한 코드 재사용의 일례이다.

---
## 6.3 구조형 재사용과 함수형 재사용 (Structural Versus Functional Reuse) - cont.
#### 구성(composition)을 사용한 코드 재사용
* [예제 6-20]은 2장에서 자바로 짠 조금 더 함수형인 자연수 분류기다([예제 2-11](https://github.com/happy4u/functional_thinking/tree/master/chapter2#222-조금-더-함수적인-자연수-분류기))
* [예제 6-20 조금 더 함수형인 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-20.java)
	(1) 모든 메서드는 number를 매개변수로 받아야 한다. 그 값을 유지할 내부 상태는 없다.
    (2) 모든 메서드는 순수함수이기 때문에 public static이다. 그렇기 때문에 자연수 분류 문제는 범위 밖에서도 유용하다.
    (3) 일반적이고 합리적인 변수의 사용으로 함수 수준에서의 재사용이 쉬워졌다.
    (4) 이 코드는 캐시가 없기 때문에 반복적으로 사용하기에 비능률적이다.
* [예제 6-16]을 공유된 상태 없이 순수함수를 사용하게 바꾼 함수형 버젼의 isPrime() 메서드는 [예제 6-21] 참고.

---
## 6.3 구조형 재사용과 함수형 재사용 (Structural Versus Functional Reuse) - cont.
* [예제 6-21 함수형 소수 찾기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-21.java)
* [예제 6-22 함수형으로 리팩토링한 Factors 클래스](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-22.java)
	* factors() 메서드는 가독성을 위해 of()로 rename
	* 함수형 버전의 모든 상태는 매개변수로 주어지기 때문에, 이 추출한 클래스에는 공유된 상태가 없다.
* [예제 6-23 리팩토링한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter6/6.3_ex_6-23.java)
* [예제 6-22], [예제 6-23] 모두 Factors 클래스를 사용하지만, 그 사용은 전적으로 개별 메서드에 포함된다.
* 상속을 통한 커플링과 구성의 차이점은 작지만 중요하다.
* 이 예와 같은 간단한 경우에는 코드 구조물의 골격이 그대로 드러나는 것을 볼 수 있다. 하지만 복잡한 코드베이스를 리팩토링할 때는 객체지향 언어의 코드 재사용 방식인 이런 커필링이 여기저기 나타난다. 무성하게 커플링된 구조물들을 이해해야 하는 어려움 때문에 객체지향 언어에서는 코드 재사용이 피해를 많이 입었다.

---
## 6.3 구조형 재사용과 함수형 재사용 (Structural Versus Functional Reuse) - cont.
* 코드 재사용은 개발자에겐 당연한 목표이지만, 명령형 추상 개념은 함수형 프로그래머들이 사용하는 방법과는 다른 방법으로 이 문제를 해결하려 든다.
* 디자인 패턴이 함수형 프로그래밍과 만나는 방법들의 윤곽을 살펴봤다.
	1. 디자인 패턴은 언어나 런타임에 흡수될 수 있다.
	2. 패턴들은 그 의미를 보존하면서 다른 방법으로 구현될 수 있다.
	3. 함수형 언어와 런타임은 전적으로 다른 기능을 가질 수 있고, 그것들을 사용하면 같은 문제라도 완전히 다른 방식으로 풀어나갈 수 있다.

<!-- $size: 16:9 -->
# 5 진화하라(Evolve)
* 함수형 언어에서의 코드 재사용은 객체지향 언어와는 접근 방법이 다르다
	* 객체지향 언어
		* 수많은 자료구조와 거기에 딸린 수많은 연산을 포함
		* 클래스에 종속된 메서드를 만드는 것을 권장하여 반복되는 패턴을 재사용

	* 함수형 언어
		* 적은 수의 자료구조와 많은 연산	 
		* 자료구조에 대해 공통된 변형 연산을 적용하고, 특정 경우에 맞춰서 주어진 함수를 사용하여 작업을 커스터마이즈함으로써 재사용을 장려
* 소프트웨어에서 반복되는 문제들의 해결 방법을 어떻게 **진화**시켜왔는지를 살펴보자


---
## 5.1 적은 수의 자료구조, 많은 연산자
> 100개의 함수를 하나의 자료구조에 적용하는 것이 10개의 함수를 10개의 자료구조에 적용하는 것보다 낫다
> by 앨런 펄리스

* OOP 세상에서는 특정한 메서드가 장착된 특정한 자료구조를 개발자가 만들기를 권장한다. 함수형 프로그래밍 언어에서는 이 같은 방식으로 재사용을 하려 하지 않는다.
* 함수형 프로그래밍에서는 대신 몇몇 주요 자료구조(list, set, map)와 거기에 따른 최적화된 연산들을 선호한다.
* 함수 수준에서 캡슐화하면 커스텀 클래스 구조를 만드는 것보다 좀 더 세밀하고 기본적인 수준에서 재사용이 가능해진다.
* 이런 접근 방법의 한 가지 이점이 클로저에는 이미 나타나고 있다.
* 일례로 XML 파싱의 경우, 자바에는 이 작업을 하기 위한 프레임워크가 수도 없이 있다. 이들 프레임워크에는 각자의 커스텀 자료구조와 메서드 형태가 존재한다. (ex> SAX vs DOM)
* 클로저는 XML을 커스텀 자료구조 대신에 일반 Map으로 파싱한다.

---
## 5.1 적은 수의 자료구조, 많은 연산자 - cont.
* [예제 5-1 클로저에서 XML 파싱하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.1_ex_5-1.clj)
	* 클로저는 리스프의 변형이기 때문에 안에서 밖으로 읽는 것이 쉽다.
	* 날씨 서비스에서 리턴한 자료구조를 파싱한 일부는 다음과 같다.
      ```
      ({:tag :yweather:condition, :attrs {:text Fair, :code 34, :temp 62
      , :date Tue,  04 Dec 2012 9:51 am EST}, :content nil})
      ```
	* 클로저는 맵을 사용하는 데 최적화되어 있기 때문에 맵에 들어 있는 키워드가 바로 함수가 된다.
	* (:tag x)은 'x라는 맵에 들어 있는 :tag 키에 해당하는 값을 꺼내라'를 짧게 쓴 것이다.
	* 맵이나 다른 자료구조를 다루는 방법이 아주 다양하다는 점이 클로저를 처음 배울 때 어렵게 느껴지는 이유 중 하나다. 하지만 이것은 클로저가 대부분의 일을 이렇게 최적화된 주요 자료구조로 풀어나가려 시도하기 때문이다.
	* 클로저에서는 특정한 프레임워크에 파싱된 XML을 키워 넣기보다는, 이미 여러 가지 도구들이 존재하는 자료구로로 XML을 변환하려고 한다.

---
## 5.2 문제를 향하여 언어를 구부리기
* 대부분의 개발자들은 복잡한 비지니스 문제를 자바와 같은 언어로 번역하는 것이 그들의 할 일 이라는 착각 속에서 일을 한다. 자바가 언어로서 유연하지 못하기 때문에, 아이디어를 기존의 고정된 구조에 맞게 주물러야 하기 때문이다.
* 유연한 언어는 문제를 언어에 맞게 구부리는 대신 언어를 문제에 어울리게 구부릴 수 있다.
* 루비와 같은 언어는 도메인 특화 언어(DSL)를 주류 언어들보다 잘 지원하기 때문에 이런 것이 가능하다
* 현대 함수형 언어들은 좀 더 진화했다. 스칼라는 내부 DSL을 지원하기 위해서 설계된 언어이고, 클로저와 같은 모든 리스프 계열 언어들은 개발자가 문제에 맞게 언어를 바꾸는 유연성 면에서 어떤 언어 못지않다.

---
## 5.2 문제를 향하여 언어를 구부리기 - cont.
* [예제 5-2 스칼라의 XML용 문법적 설탕](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.2_ex_5-2.scala)
	* 스칼라는 가단성(malleability:소프트웨어에서는 언어나 프레임워크가 사용하는 코드에 의해 용도나 반응이 변하는 성질을 의미)을 위해 설계되었기 때문에 연산자 오버로딩이나 묵시적 자료형 같은 확장이 가능하다
	* [예제 5-2]에서는 스칼라를 확장해서 \\란 연산자를 사용하여 XPath 방식의 쿼리를 사용할 수 있었다.
* 특별히 함수형 언어만의 기능은 아니지만, 언어를 우아하게 문제 도메인으로 바꾸는 기능은 함수형, 선언형 방식의 현대 언어에서 흔히 볼 수 있다.
---
## 5.3  디스패치 다시 생각하기
* 3장에서 대안적 디스패치 방식의 한 예로 스칼라의 *패턴 매칭*을 살펴봤다.
* 디스패치란 넓은 의미로 언어가 작동 방식을 동적으로 선택하는 것을 말한다.
* 이 절에서는 여러 함수형 JVM 언어들의 디스패치 방식이 어떻게 자바보다 간결함과 유연함을 가능하게 하는지를 더 살펴보자.

---
### 5.3.1 그루비로 디스패치 개선하기
* 자바에서 조건부 실행은 특별한 경우의 switch 문을 제외하고는 if문을 사용하게 된다. if문이 길게 연결되면 가독성이 떨어지기 때문에 자바 개발자들은 주로 GoF의 팩토리나 추상 팩토리 패턴을 사용한다.
* 그루비에는 자바의 switch 문과 유사한 문법을 사용하지만 다르게 실행되는 강력한 switch문이 있다.
* [예제 5-3 그루비의 개선된 switch문](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.3_ex_5-3.groovy)
	* 자바와 달리, 그루비의 switch 문은 여러 가지 동적 자료형을 받을 수 있다.
	* 자바처럼 폴-스루 형식을 따라 각 경우를 return이나 break로 마쳐야 한다.
	* 자바와는 다르게 그루비에서는 범위(90..100), 열린 범위(80..\<90), 정규식(~"[ABCDFabcdf]"). 디폴트 조건을 모두 사용할 수 있다.
* 강력한 switch 문은 if문의 연속 사용과 팩토리 패턴의 중간 정도로 생각하고 간편하게 사용할 수 있다.

---
### 5.3.2 클로저 언어 구부리기
* 클로저 같은 리스프 계열의 언어에서는 개발자가 언어를 문제에 맞게 변형할 수 있다.
* 클로저를 사용하는 개발자는 가독성이 좋은 코드를 만드는 셈이다.
* [예제 5-5 클로저로 만든 학점 프로그램](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.3_ex_5-5.clj)
	* 읽기 좋은 letter-grade 함수를 만들고, 거기에 사용할 in 함수를 구현했다.
	* 자바의 score.toUpperCase()는 클로저의 (.toUpperCase score)와 동등하다

---
### 5.3.3 클로저의 멀티메서드와 맞춤식 다향성
* 클로저에는 다른 객체지향 언어의 모든 기능이 다른 기능들과는 별개로 구현되어 있다. 예를 들면 클로저도 다형성을 지원하지만 클래스를 평가해서 디스패치를 결정하는 것에 국한되어 있지는 않다.
* 클로저는 개발자가 원하는 대로 디스패치가 결정되는 다형성 멀티메서드를 지원
* 예제 5-7 클로저로 색 구조를 정의하기
  ```
  (defstruct color :red :green :blue)

  (defn red [v]
      (struct color v 0 0))

  (defn green [v] 
      (struct color 0 v 0))

  (defn blue [v]
      (struct color 0 0 v))
  ```
  * 세 가지 색깔 값을 저장하는 구조를 정의
  * 각각 한 색깔을 포함하는 구조를 리턴하는 세 메서드를 정의

---
### 5.3.3 클로저의 멀티메서드와 맞춤식 다향성 - cont.
* [예제 5-8 멀티메서드 정의하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.3_ex_5-8.clj)
	* basic-colors-in이란 디스패치 함수 : 정해진 모든 색깔들을 벡터 형태로 리턴
	* 모든 다른 경우를 처리하는 :default 키워드를 포함

* [예제 5-8 멀티메서드 정의하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.3_ex_5-9.clj)
	* 색깔을 하나만 주고 이 메서드를 호출하면 멀티메서드의 단일 색깔 버젼이 실행
	* 여러 색깔로 이 메서드를 호출하면 디폴트 메서드가 모든 색을 리턴
* 다형성을 상속과 분리하면 강력하고 상황에 맞는 디스패치 방식이 가능해진다.
* 클로저는 이런 디스패치 함수를 사용해서 자바의 다형성만큼 상황에 맞으면서도 제약은 훨씬 적은 강력한 디스패치 방식을 구현할 수 있다.


---
## 5.4 연산자 오버로딩
* 함수형 언어의 공통적인 기능은 연산자 오버로딩이다.
* +, -, \*와 같은 연산자를 새로 정의하여 새로운 자료형에 적용하고 새로운 행동을 하게 하는 기능

---
### 5.4.1 그루비
* 그루비는 연산자들을 메서드 이름에 자동으로 매핑하는 연산자 오버로딩을 허용한다.
* [그루비의 연산자/메서드 매핑 목록](http://docs.groovy-lang.org/next/html/documentation/core-operators.html#Operator-Overloading)
* [예제 5-10 그루비의 복소수](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.4_ex_5-10.groovy)
	* 실수와 허수 부분을 포함하는 클래스
	* 두 복소수를 곱하려면 다음 공식 사용
      ```
      (x+yi)(u+vi)=(xu-yv)+(xv+yu)i
      ```

---
### 5.4.2 스칼라
* 스칼라는 연산자와 메서드의 차이점을 없애는 방법으로 연산자 오버로딩을 허용
* [예제 5-12 스칼라의 복소수](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.4_ex_5-12.scala)
	* 스칼라에서 생성자 변수들은 클래스 변수로 표시. 즉 이 클래스의 real과 imaginary 부분을 받는다.
	* 스칼라는 필드들을 자동으로 제공하기 때문에 클래스의 나머지는 메서드 정의뿐이다.

* 자바 언어 설계자들은 연산자 오버로딩을 지원하지 않기로 결정했다. C++를 너무 복잡하게 만들었던 경험 때문이었다.
* 대부분의 현대 언어들을 복잡한 정의들을 제거했지만, 아직도 그런 복잡한 것의 남용을 예방하려는 의도를 여기저기에서 찾아볼 수 있다.

---
## 5.5 함수형 자료구조
* 자바에서는 언어 자체의 예외 생성 및 전파 기능을 사용하는 전통적인 방법으로 오류를 처리한다.
* 예외는 많은 함수형 언어가 준수하는 전제 몇 가지를 깨트린다.
	1. 함수형 언어는 부수효과가 없는 순수함수를 선호한다. 그런데 예외를 발생시키는 것은 예외적인 프로그램 흐름을 야기하는 부수효과다.
	2. 참조 투명성. 호출하는 입장에서는 단순한 값 하나를 사용하단, 하나의 값을 리턴하는 함수를 사용하든 다를 바가 없어야 한다. 만약 호출된 함수에서 예외가 발생할 수 있다면, 호출하는 입장에서는 안전하게 값을 함수로 대체할 수  없을 것이다.

---
### 5.5.1 함수형 오류 처리
* 자바에서 예외를 사용하지 않고 오류를 처리하기 위해 해결해야 할 근본적인 문제는 메서드가 하나의 값만 리턴할 수 있다는 제약이다.
* 예제 5-14 Map을 사용하여 다중 리턴 값을 처리하기
  ```
  public static Map<String, Object> divide(int x, int y) {
          Map<String, Object> result = new HashMap<String, Object>();
          if(y==0)
                  result.put("exception", new Exception("div by zero"));
          else
                  result.put("answer", (double) x / y);
          return result;
  }
  ```
  * divide() 메서드는 실패를 "exception"으로 표시하고, 성공은 "answer"로 표시한다.

---
### 5.5.1 함수형 오류 처리 - cont.
* [예제 5-15 Map으로 성공과 실패를 테스트하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-15.java)
	* 이 접근 방법에는 문제점이 있다.
		1. Map에 들어가는 값은 타입 세이프하지 않기 때문에 컴파일러가 오류를 잡아낼 수 없다. 열거형을 키로 사용하면 조금 좋아지기는 하겠지만, 근본적인 해결책은 아니다
		2. 메서드 호출자는 리턴 값을 가능한 결과들과 비교해보기 전에는 성패를 알 수 없다.
		3. 두 가지 결과가 모두 리턴 Map에 존재할 수가 있으므로, 그 경우에는 결과가 모호해진다.
	* 여기서 필요한 것은 타입 세이프하게 둘 또는 더 많은 값을 리턴할 수 있게 해주는 메커니즘이다.

---
### 5.5.2 Either 클래스
* 함수형 언어에서 다른 두 값을 리턴해야 하는 경우가 종종 있는데 그런 행동을 모델링하는 자료구조가 Either 클래스이다.
* Either는 왼쪽 또는 오른쪽 값 중 하나만 가질 수 있게 설계되었다. 이런 자료구조를 **분리합집합**이라고 한다.
* [예제 5-16 스칼라의 Either 클래스](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-16.scala)
	* [예제 5-16]에서처럼, Either는 오류 처리에서 주로 사용된다.

* [예제 5-17 스칼라의 Either와 패턴 매칭](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-17.scala)

---
### 5.5.2 Either 클래스 - cont.
* 자바에 내장되지는 않았지만, 제네릭을 사용하면 [예제 5-18]에서 보듯이 간단한 대용품 Either 클래스를 만들 수 있다.
```
// 다음과 같은 식으로 F 인터페이스를 정의해야 예제 5-18을 사용할 수 있다.
package com.nealford.ft.errorhandling;

public interface F<A>{
	public void f(A input);
}
```
* [예제 5-18 Either 클래스를 통해 타입 세이프한 두 값을 리턴하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-18.java)
	* 실제 생성은 정적 메서드인 left(A a)와 right(B b)가 담당
	* 함수형의 보편적인 관례에 따라 Either 클래스의 왼쪽이 예외, 오른쪽이 결과 값이다.

---
### 5.5.2 Either 클래스 - cont.
#### 로마숫자 파싱
* [예제 5-19]는 Either의 사용법을 설명하기 위해서 만든 RomanNumeral이란 클래스다.
* [예제 5-19 자바로 단순하게 구현한 로마숫자](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-19.java)
* 예제 5-20 로마숫자 파싱하기
  ```
  public static Either<Exception, Integer> parseNumber(String s) { 
    if (! s.matches("[IVXLXCDM]+"))
        return Either.left(new Exception("Invalid Roman numeral")); 
    else
        return Either.right(new RomanNumeral(s).toInt());
  }
  ```
* [예제 5-21 로마숫자 파싱 테스트](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-21.java)
* MAP 자료구조를 주고받던 것에 비해면 큰 발전이다. 예외를 얼마든지 세부화할 수 있으면 타입 세이프티를 지킬 수 있다.
* 이런 우회 덕분에 게으름을 구현하는 것이 가능해진다.

---
### 5.5.2 Either 클래스 - cont.
#### 게으른 파싱과 함수형 자바
* Either 클래스는 함수형 알고리즘에 자주 사용된다.
* [예제 5-22 함수형 자바를 사용하여 게으른 파서 생성하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-22.java)
	* P1클래스는 매개변수가 없는 -1()란 간단한 메서드의 단순한 래퍼이다.
	* P1은 함수형 자바에서 코드 블록을 실행하지 않고 여기저기 전해주어서 원하는 컨텍스트에서 실행하게 해주는, 일종의 고계함수이다.
* [예제 5-23 함수형 자바의 게으른 파서 테스트](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-23.java)
	* parse_lazy : 반드시 \_1()를 호출해서 결과를 풀어야 한다.
	* parse_lazy_exception : **왼쪽** 값을 확인하고 풀어서 예외의 메세지를 알아낼 수 있다.
* 이런 게으른 예외는 생성자의 실행을 지연하게 해준다.

---
### 5.5.2 Either 클래스 - cont.
#### 디폴트 값을 제공하기
* Either를 예외 처리에 사용하여 얻는 이점은 게으름만이 아니다. 디폴트 값을 제공한다는 것이 따른 점이다.
* [예제 5-24 적당한 디폴트 리턴 값 제공하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-24.java)
* [예제 5-25 디폴트 값 테스트](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-25.java)
	* MAX보다 큰 로마숫자의 경우 디폴트로 MAX값을 가지게 했다.

---
### 5.5.2 Either 클래스 - cont.
#### 예외 조건을 래핑하기
* [예제 5-26 다른 곳에서 던진 예외를 처리하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-26.java)
* [예제 5-27 예외 래핑 테스트](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-27.java)
	* 예외가 생기면 Either의 왼쪽에 넣고, 그렇지 않으면 오른쪽 값으로 결과를 리턴
	* Either를 사용하면 점검지정 예외(checked exception)를 포함한 모든 예외들을 함수형으로 바꿀 수 있다.

---
### 5.5.2 Either 클래스 - cont.
#### 예외 조건을 래핑하기
* [예제 5-28 게으른 예외 처리](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-28.java)
* [예제 5-29 던저진 예외를 처리하는 예제](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-29.java)
* 자바는 이런 개념을 근본적으로 포함하지 않기 때문에 Either 클래스를 모델링하기가 번거롭다. 
* 스칼라와 같은 언어는 Either나 그와 유사한 구조물이 내장되어 있다.
* 클로저나 그루비는 리턴 값을 쉽게 생성할 수 있는 동적 타이핑 언어이기 때문에 Either같은 것을 굳이 기본적으로 포함하지 않아도 된다.

---
### 5.5.3 옵션 클래스
* Either는 두 부분을 가진 값을 간편하게 리턴할 수 있는 개념이다.
* 여러 언어나 프레임워크에는 함수에서 리턴할 때 사용할 수 있는 Either와 유사한 Option이란 클래스가 있다.
	* 적당한 값이 존재하지 않을 경우를 의미하는 none, 성공적인 리턴을 의미하는 some을 사용하여 예외 조건을 더 쉽게 표현한다.
* 예제 5-30 Option 사용법
  ```
  public static Option<Double> divide(double x, double y) { 
      if(y==0)
          return Option.none(); 
      return Option.some(x / y);
  }
  ```

---
### 5.5.3 옵션 클래스 - cont.
* 예제 5-31 Option 기능 테스트하기
  ```
  @Test
  public void option_test_success() {
  Option result = FjRomanNumeralParser.divide(4.0, 2);
  assertEquals(2.0, (Double) result.some(), 0.1);
  }

  @Test
  public void option_test_failure() {
  Option result = FjRomanNumeralParser.divide(4.0, 0);
  assertEquals(Option.none(), result);
  }
  ```
  * Either와 유사하지만 적당한 리턴 값이 없을 수 있는 메서드를 위해 none()과 some()을 가지고 있다.
  * Option 클래스는 Either의 간단한 부분집합이라고 볼 수 있다.
  * Either는 어떤 값이든 저장할 수 있는 반면, Option은 주로 성공과 실패의 값을 저장하는 데 쓰인다.

---
### 5.5.4 Either 트리와 패턴 매칭
* 이 절에서는 트리 모양의 구조물을 만들면서 Either를 좀 더 살펴보자

#### 스칼라 패턴 매칭
* 스칼라의 훌륭한 기능 중의 하나는 디스패치에 패턴 매칭을 사용할 수 있다는 점이다.
* [예제 5-32 스칼라 패턴 매칭을 사용하여 점수를 기준으로 학점 배정하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-32.scala)
* [예제 5-33 학점 평가 함수 테스트하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-33.scala)
	* [예제 5-32]의 letterGrad() 함수 전체가 주어진 값에 대한 match이다.
	* 패턴 매칭은 스칼라의 케이스 클래스와 같이 사용된다.

---
### 5.5.4 Either 트리와 패턴 매칭 - cont.
#### 스칼라 패턴 매칭
* [예제 5-34 스칼라에서 케이스 클래스 매칭하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-34.scala)
	* 먼저 기본 Color 클래스를 만들고, 단색 버젼들을 케이스 클래스로 만들었다.
	* 어떤 색이 함수에 넘겨졌는지를 알기 위해 match를 사용하여 가능한 모든 값에 대해 패턴 매칭을 시도한다.

* 자바는 패턴 매칭을 지원하지 않는다. 그래서 스칼라처럼 깔끔하고 읽기 쉬운 디스패치 코드를 만들 수 없다.
* 하지만 제네릭과 잘 알려진 자료구조를 같이 사용하면 어느 정도 가까이 갈 수 있다.

---
### 5.5.4 Either 트리와 패턴 매칭 - cont.
#### Either 트리
* Either의 추상 개념은 원하는 개수만큼 슬롯을 확장할 수 있다.
  ```
  Either <Empth, Either<Leaf, Node>>
  ```
  * empty : 셀에 아무 값도 없음
  * leaf : 셀에 특정 자료형의 값이 들어 있음
  * node : 다른 leaf나 node를 가리킴
* [예제 5-35 Either로 만든 트리](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-35.java)
	* Tree 추상 클래스는 그 내부에 세 개의 final 구상 클래스를 정의. Empty, Leaf, Node
	* 이 트리 구조는 내부적으로 \<Either, \<Leaf, Node>>를 바탕으로 하므로 패턴 매칭을 흉내내서 모든 요소를 순회할 수 있다.

---
### 5.5.4 Either 트리와 패턴 매칭 - cont.
#### 패턴 매칭으로 트리 순회하기
* 함수형 자바에서 구현된 Either의 left()와 right() 메서드는 모두 Iterable 인터페이스를 구현
* 덕분에 트리의 깊이를 패턴 매칭 방식으로 알아내는 코드를 짤 수 있다.
* [예제 5-36 패턴 매칭과 유사한 구문으로 트리의 깊이 알아내기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-36.java)
	* 셀이 비어 있으면 그 가지는 깊이가 없고
	* 셀이 leaf이면, 트리와 같은 레벨로 처리
	* 셀이 node이면, 1을 레벨 값에 더하고 재귀적으로 왼쪽과 오른쪽을 모두 검색

---
### 5.5.4 Either 트리와 패턴 매칭 - cont.
#### 패턴 매칭으로 트리 순회하기
* [예제 5-37 트리에서 값의 존재 확인하기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-37.java)
	* 빈 셀에 도달하면 검색이 실패했으므로 false 리턴
	* leaf에서는 주어진 값을 확인하여 값이 같으면 true를 리턴
	* node에 다다르면 비단축평가형 or 연산자인 |를 사용하여 트리를 재귀적으로 계속 검색하여 리턴되는 불리언 값과 결합
* [예제 5-38 트리의 검색 기능성 테스트](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-38.java)
	* inTree() 메서드는 leaf 중 하나가 같은 값을 가지면 true를 리턴하고, 이 true 값은 | 연산자 때문에 재귀적인 호출 스택을 따라 위로 전달된다.

---
### 5.5.4 Either 트리와 패턴 매칭 - cont.
#### 패턴 매칭으로 트리 순회하기
* [예제 5-39 트리에서 발견 횟수 알아내기](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-39.java)
	* 트리 안에서 발견된 횟수를 세기 위해 같은 값을 가진 leaf마다 1을 리턴
* [예제 5-40 복잡한 트리의 깊이, 존재, 횟수에 대한 테스트](https://github.com/happy4u/functional_thinking/blob/master/chapter5/5.5_ex_5-40.java)
* 트리의 내부 구조를 규격화한 덕분에, 트릴르 따라가면서 각 요소의 자료형에 따른 경우에 대해서만 생각하며 분석할 수 있다.
* 스칼라의 패턴 매칭처럼 표현이 풍부하지는 않지만, 이 구문은 놀랍게도 스칼라의 표현과 흡사하다.


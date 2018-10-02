<!-- $size: 16:9 -->
# 2 전환(Shift)
* 일반적으로 개발자들은 이미 다른 언어에 대해 알고 있는 지식을 적용해서 새 언어를 배운다. 하지만 새 패러다임을 익히기는 어렵다. 이미 친숙한 문제에 대해 다른 해답을 떠올릴 능력을 배워야 하기 때문이다.
* **함수형 코드를 작성하기 위해서는 함수형 언어인 스칼라나 클로저로의 전환이 필요한 것이 아니라 문제에 접근하는 방식의 전환이 필요하다.**

---
## 2.1 일반적인 예제
* 함수형 프로그래밍은 복잡한 최적화는 런타임에게 맡기고 개발자가 좀 더 추상화된 수준에서 코드를 작성할 수 있게 함으로써 알고리즘 측면에서 가비지 컬렉션과 동일한 역할을 수행할 것이다.
* 개발자들은 가비지컬렉션에서 얻었던 것같이 복잡성이 낮아지고 성능이 높아지는 혜택을 받게 될 것이다.

---
### 2.1.1 명령형 처리 (1/3)
* 명령형 프로그래밍이란 상태를 변형하는 일련의 명령들로 구성된 프로그래밍 방식을 말한다.

* 다음 예제는 어떤 이름 목록에서 한 글자로 된 이름을 제외한 모든 이름을 대문자화해서 쉼표로 연결한 문자열을 구하는 코드의 예이다.


---
### 2.1.1 명령형 처리 (2/3)
* 예제 2-1 전형적인 회사 프로세스(자바)

```java
Example 2-1. Typical company process (in Java)
package com.nealford.functionalthinking.trans;
import java.util.List;

public class TheCompanyProcess {
    public String cleanNames(List<String> listOfNames) {
        StringBuilder result = new StringBuilder(); 
            for(int i = 0; i < listOfNames.size(); i++) {
            if (listOfNames.get(i).length() > 1) {
                    result.append(capitalizeString(listOfNames.get(i)))
                        .append(",");
            } 
        }
        return result.substring(0, result.length() - 1).toString(); 
    }

    public String capitalizeString(String s) {
    	return s.substring(0, 1).toUpperCase() 
        	+ s.substring(1, s.length());
    }
}
```

---
### 2.1.1 명령형 처리 (3/3)
* 한 글짜짜리 이름을 **필터했고**, 목록에 남아 있는 이름들을 대문자로 **변형하고**, 이 목록을 하나의 문자열로 **변환했다**.


---
### 2.1.2 함수형 처리

* 함수형 프로그래밍 언어는 명령형 언어와는 다르게 문제를 분류한다. 앞에서 언급한 **필터, 변형, 변환** 등의 논리적 분류도 저수준의 변형을 구현하는 함수들이었다.
* 개발자는 고계함수에 매개변수로 주어지는 함수를 이용하여 저수준의 작업을 커스터마이즈할 수 있다.
* 예제 2-2 '회사 프로세스'에 대한 의사코드를 사용한 개념화
```
listOfEmps
    -> filter(x.length > 1)
    -> transform(x.capitalize)
    -> convert(x + "," + y)
```

---
### 2.1.2 함수형 처리 - cont.
* 예제 2-3 스칼라의 함수형 처리
  ```scala
  val employees = List("neal", "s", "stu", "j", "rich", "bob", "aiden", "j", "ethan", "liam", "mason", "noah", "lucas", "jacob", "jayden", "jack")

val result = employees 
	.filter(_.length() > 1) 
	.map(_.capitalize) 
	.reduce(_ + "," + _)
```
* 스칼라에서는 이름을 생략하고 언더바(`_)를 대신 사용한다. reduce()의 경우에는 시그니처에 따라 매개변수를 두 개 전달했지만 일반 지시자인 언더바를 사용했다.


---
### 2.1.2 함수형 처리 - cont.
* 예제 2-4 회사 프로세스의 자바 8 버젼

```java
public String cleanNames(List<String> names) { 
    if (names == null) return "";
    return names
        .stream()
        .filter(name -> name.length() > 1)
        .map(name -> capitalize(name))
        .collect(Collectors.joining(","));

private String capitalize(String e) {
    return e.substring(0, 1).toUpperCase() + e.substring(1, e.length());
}
```

---
### 2.1.2 함수형 처리 - cont.
* 예제 2-5 그루비로 처리하기

```groovy
public static String cleanUpNames(listOfNames) { listOfNames
        .findAll { it.length() > 1 }
        .collect { it.capitalize() }
        .join ','
}
```
* 그루비의 치환 방법은 하나의 매개변수를 표현하는 데 it이라는 키워드를 사용.

---
### 2.1.2 함수형 처리 - cont.
* 예제 2-6 클로저로 처리하기
  ```clojure
  (ns trans.core
      (:require [clojure.string :as s]))

  (defn process [list-of-emps] 
      (reduce str (interpose ","
          (map s/capitalize (filter #(< 1 (count %)) list-of-emps)))))
  ```
*내 생각 : 클로저를 처음 접했는데, 다른 언어와 비교하면 클로저는 외계어처럼 느껴진다.*

* 클로저 코드를 읽는 데 익숙하지 않다면 코드 구조를 이해하기가 어려울 거다. 클로저와 같은 리스프 계열 언어는 '안에서 밖으로' 실행되므로 마지막 매개변수 값인 list-of-emps에서 시작해야 한다.

---
### 2.1.2 함수형 처리 - cont.
* 이제까지 본 모든 언어들은 함수형 프로그래밍의 주요 개념을 포함하고 있다. 함수형 사고로의 전환은 어떤 경우에 세부적인 구현에 뛰어들지 않고 이런 고수준 추상 개념을 적용할지를 배우는 것이다.
* 고수준의 추상적 사고로 얻는 이점은?
	1. 문제의 공통점을 고려하여 다른 방식으로 분류하기를 권장한다. (it encourages you to categorize problems differently, seeing commonalities)
	2. 런타임이 최적화를 잘할 수 있도록 해준다. (어떤 경우 작업 순서를 바꾸면 더 능률적이 된다)
	3. 개발자가 엔진 세부사항에 깊이 파묻힐 경우 불가능한 해답을 가능하게 한다.


---
### 2.1.2 함수형 처리 - cont.
* 예제 2-1과 같은 java의 명령형 코드를 병렬처리화 한다고 생각해보자 --;;
* 예제 2-8 스칼라에서의 병렬처리
	* 스트림에 par만 붙이면 된다.
  ```java
  val parallelResult = employees 
      .par
      .filter(_.length() > 1)
      .map(_.capitalize)
      .reduce(_ + "," + _)
  ```

---
### 2.1.2 함수형 처리 - cont.
* 예제 2-9 자바 8에서의 분산처리
	* parallelStream만 추가

```java
public String cleanNamesP(List<String> names) { if (names == null) return "";
    return names
        .parallelStream()
        .filter(n -> n.length() > 1)
        .map(e -> capitalize(e))
        .collect(Collectors.joining(","));
}
```

---
### 2.1.2 함수형 처리 - cont.
* 높은 추상 수준에서 코딩 작업을 하고, 저수준의 세부적인 최적화는 런타임이 담당하게 하면 된다.
* JVM 엔지니어들이 가비지 컬렉션을 거의 신경 쓸 필요가 없을 정도로 추상화해준 덕분에 개발자들의 삶의 질은 향상되었다.
* map, reduce, filter 같은 함수형 연산도 이와 같은 이중적인 혜택을 준다. 훌륭한 일례가 클로저용 리듀서 라이브러리이다.
* 반복, 변형, 리덕션 같은 저수준 작업의 세부사항에 대해 생각하지 말고, 유사한 형태의 문제들이 얼마나 많은지부터 인식해보라.

---
## 2.2 사례 연구: 자연수의 분류
* 자연수 분류기의 구현 예
* 고대 그리스의 수학자 니코마코스는 자연수를 과잉수, 완전수, 부족수로 나누는 분류법을 고안
	* 완전수 : 진약수의 합 = 수
	* 과잉수 : 진약수의 합 > 수
	* 부족수 : 진약수의 합 < 수
* ex> 6의 약수는 1, 2, 3, 6 : 6 = 1 + 2 + 3 --> 완전수
* ex> 28 = 1 + 2 + 4 + 7 + 14 --> 완전수

---
### 2.2.1 명령형 자연수 분류
* [예제 2-10 자바를 사용한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.1_ex_2-10.java)
	* (1) 대상이 되는 수를 보유한 내부 상태
	* (2) 합을 반복해서 계산하는 것을 피하기 위한 내부 캐시
	* (3) aliquotSum(자신을 제외한 모든 약수의 합) 계산
* 두 개의 내부 상태가 존재
	1. _number : 대상이 되는 수
	2. _cache : 진약수의 합을 유지
* OOP 언어는 캡슐화를 이점으로 사용하기 때문에 객체지향적인 세계에서는 내부 상태의 사용이 보편적이면 권장된다. 상태를 분리해놓으면 값을 삽입할 수가 있기 때문에 단위 테스팅 같은 엔지니어링이 쉬워진다.
* 많은 수의 작은 메서드로 세밀하게 분리되어 있다. 이것은 테스트 주도개발의 부수효과이다. 그 결과로 알고리즘의 각 부분을 살펴보기가 쉽다.



---
### 2.2.2 조금 더 함수적인 자연수 분류기
* 공유상태를 최소화 하기위해 멤버 변수를 없애고 필요한 값들을 매개변수로 넘김
* [예제 2-11 약간 더 함수형인 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.2_ex_2-11.java)
	* (1) 모든 메서드는 number를 매개변수로 받아야 한다. 그 값을 유지할 내부 상태는 없다.
	* (2) 모든 메서드는 순수함수이기 때문에 public static이다. 그렇기 때문에 자연수 분류 문제라는 범위 밖에서도 유용하다
	* (3) 일반적이고 합리적인 변수의 사용으로 함수 수준에서의 재사용이 쉬워졌다.
	* (4) 이 코드는 캐시가 없기 때문에 반복적으로 사용하기에 비능률적이다.

* 모든 메서드가 public static 스코프를 가지며 자급적인 순수함수(부수효과가 없는 함수)이다. 이 객체에는 내부 상태가 없기 때문에 메서드를 숨길 이유가 없다. 사실 factors 메서드는 소수를 찾는 것과 같은 다른 애플리케이션에서도 유용하다.
* 개발자들은 소형 패키지가 재사용이 용이하다는 것을 잘 잊는다. aliquotSum의 경우 특정 목록 대신 Collection<Integer>를 받으므로 함수 수준에서 일반적으로 재사용이 가능하다.
* 이 해법에는 sum의 캐시를 구현하지 않았다. 4장에서 볼 버젼에는 메모이제이션을 통해 이런 상태성을 보존하면서 캐시를 회복하지만, 당분간은 캐시는 없애기로 하자.



---
### 2.2.3 자바 8을 사용한 자연수 분류기
* 자바 8에 더해진 최고의 기능은 람다블록과 고계함수다.
* [예제 2-12 자바 8 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.3_ex_2-12.java)
	* factorOf() 메서드는 스트림을 종료한 뒤 값으로 만드는 것과 같은 다른 작업과 연계할 수 있도록 IntStream을 리턴한다.
	* aliquotSum() 메서드는 목록에 있는 자연수의 합에서 자신의 값을 제외하기만 하면 된다. 이 메서드는 스트림을 종료하고 값을 생성해준다.


---
### 2.2.4 함수형 자바를 사용한 자연수 분류기
* Functional Java는 자바 1.5 이후 버전에 무리 없이 함수형 표현을 추가하려는 목적으로 만들어진 오픈소스 프레임워크다.
* 1.5 시절의 자바에는 고계함수가 없었기 때문에 함수형 자바는 제네릭이나 익명 내부 클래스를 흉내 내어 사용했다.
* [예제 2-13 함수형 자바를 사용한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.4_ex_2-13.java)
	* [예제 2-12](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.3_ex_2-12.java)와 [예제 2-13](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.4_ex_2-13.java)의 주요 차이점은 두 메서드에서 볼 수 있다. aliquotSum()과 factorsOf() 메서드다.
	* aliquotSum() 메서드는 함수형 자바에서 제공하는 List 클래스의 foldLeft() 메서드를 사용한다.
	* factorsOf() : 자연수 1부터 대상의 수까지 f() 메서드의 코드를 사용해서 목록을 필터한다.

---
## 2.3 공통된 빌딩블록

---
### 2.3.1 필터
* 목록(Lists)에서 할 수 있는 흔한 작업은 필터하는 것이다.
* 필터 작업을 할 때에는 필터 조건에 따라서 원래 목록보다 작은 목록(또는 컬렉션)을 생성한다.
* 예제 2-14 자바 8에서의 필터 작업
  ```java
  public static IntStream factorsOf(int number) {
          return range(1, number + 1)
                 .filter(potential -> number % potential == 0);
  }
  ```
---
### 2.3.1 필터 - cont.
* 람다 블록을 사용하지 않고도 같은 결과를 얻을 수는 있지만([예제 2-13](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.4_ex_2-13.java)) 람다 블록이 있는 언어에서는 더 간결하게 표현할 수 있다.
* 예제 2-15 그루비에서의 필터 작업(그루비에서는 필터를 findAll()이라고 부른다)
  ```groovy
  static def factors(number) {
          (1..number).findAll {number % it == 0}
  }
  ```

---
### 2.3.2 맵
* 맵 연산은 컬렉션의 각 요소에 같은 함수를 적용하여 새로운 컬렉션으로 만든다.
* map()이나 그와 관련된 변형 연산들을 살펴보기 위해서 자연수 분류기의 최적화된 버젼을 만들었다.
* 명령형 버전 - [예제 2-16 최적화된 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.3.2_ex_2-16.java)
	* [예제 2-10 자바를 사용한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.1_ex_2-10.java)와 비교하면 getFactors()가 최적화 됨.
	1. 여기저기에 number를 매개변수로 입력하는 것을 방지하기 위한 내부 상태
	2. sum을 더 효율적으로 조회하기 위한 캐시
	3. 약수는 항상 짝으로 찾을 수 있는 점을 이용한 최적화
	4. 캐시된 합이 있는 경우 리턴하는 메서드

---
### 2.3.2 맵 - cont.
* 그루비는 물론 함수형 변형 함수들을 포함하고 있다. 예제 2-17에서 보듯이 collect()가 map()의 그루비 버젼이다.
* 예제 2-17 그루비로 최적화된 factors

```groovy
static def factors(number) {
        def factors = (1..round(sqrt(number)+1)).findAll({number % it == 0}) 
        (factors + factors.collect {number / it}).unique()
}
```
* 마지막 unique() 호출은 목록에서 중복된 요소를 제거하여 4와 같은 완전제곱근이 목록에 두 번 포함되는 것을 방지


---
### 2.3.2 맵 - cont.
* 함수형 프로그래밍이 얼마나 코드를 바꿀 수 있는지를 보기 위해 클로저로 짠 예제 2-18을 살펴보자
* [예제 2-18 모든 연산을 몇 번의 할당으로 캡슐화한 클로저의 (classify) 함수](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.3.2_ex_2-18.clj)
	1. 메서드는 곧 할당이 된다.
	2. 필터된 범위에 약수를 할당한다
	3. 리듀스된 약수에 sum을 할당한다.
	4. 진약수의 합을 계산한다
	5. 해당하는 분류 키워드(열거)를 리턴한다.

---
### 2.3.3 폴드/리듀스
* 셋째로 자주 사용하는 함수는 많이 사용하는 언어들 사이에서도 이름이 다양하고 약간씩 의미도 다르다. foldleft나 reduce는 캐터모피즘(카테고리 이론의 개념으로 목록을 접어서 다른 형태로 만드는 연산을 총칭한다.)이라는 목록 조작 개념의 특별한 변형이다.

폴드 연산(fold operation)
![폴드 연산(fold operation)](https://raw.githubusercontent.com/happy4u/functional_thinking/master/chapter2/pic_2-3_fold_operation.png)

* reduce 함수는 주로 초기 값을 주어야 할 때 사용하고, flod는 누산기에 아무것도 없는 채로 시작한다.

---
### 2.3.3 폴드/리듀스 - cont.
* 좀 더 정제된 조건이 필요할 때는 어떻게 할 것인가?
* [예제 2-10 사용자가 지정한 조건을 사용한 foldLeft()](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.3.3_ex_2-20.java) - 함수형 자바 버젼
* 컬렉션의 요소를 하나씩 다른 함수로 처리할 때는 reduce나 fold를 사용하라.

---
### 2.3.3 폴드/리듀스 - cont.
* 자연수 분류는 인위적인 예이기 때문에 다른 문제들로의 일반화가 어렵다. 하지만 나는 추상적인 개념을 지원하는 언어를 사용하는 프로젝트들에서 엄청난 코딩 방식의 변화가 일어나고 있는 것을 목격했다.
* 루비에는 이렇게 클로저 블록을 사용하는 목록 조작 메서드들이 있는데 collect(), map(), inject()가 놀라우리만치 자주 사용되고 있었다.

* 함수형 프로그래밍같이 다른 패러다임을 익힐 때 어려운 점은 새로운 빌딩블록을 배우고, 풀고자 하는 문제에서 그것이 해법이 될 수 있다는 점을 인지하는 것이다.
* 함수형 프로그래밍에서는 추상 개념이 많지 않은 대신, 그 각 개념이 범용성을 띤다.
* 함수형 프로그래밍은 매개변수와 구성에 크게 의존하므로 '움직이는 부분' 사이의 상호작용에 대한 규칙이 많지 않고, 따라서 개발자의 작업을 쉽게 해준다.

---
## 2.4 골치 아프게 비슷비슷한 이름들

---
### 2.4.1 필터
* 필터 함수로 컬렉션에 불리언 조건을 명시할 수 있다. 이 함수는 조건을 만족시키는 요소로 이루어진 컬렉션의 부분집합을 리턴한다. 필터 연산은 조건을 만족시키는 첫 요소를 리턴하는 찾기 기능과 깊은 연관이 있다.

---
### 2.4.1 필터 - cont. (스칼라)
* 각 숫자가 3으로 나뉘어야 한다는 조건을 가진 코드 블럭을 넘겨 filter() 함수를 적용
  ```scala
  val numbers = List.range(1, 11) 
  numbers filter( x => x % 3 == 0) 
  // List(3, 6, 9)
  ```
* 묵시적 매개변수를 사용하면 더 간결한 코드 블록을 만들 수 있다.
  ```scala
  numbers filter (_ % 3 == 0) 
  // List(3, 6, 9)
  ```
* 스칼라에서는 매개변수를 언더바로 치환하는게 가능. 	 

---
### 2.4.1 필터 - cont. (스칼라)
* filter()는 어떤 컬렉션에도 적용할 수 있다.
* filter()를 단어 목록에 적용하여 세 글자 단어들을 얻어낸다.
  ```scala
  val words = List("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog")
  words filter (_.length == 3) 
  // List(the, fox, the, dog)
  ```

---
### 2.4.1 필터 - cont. (스칼라)
* 스칼라의 필터 연산 중 다른 한 가지는 컬렉션을 여러 조각으로 분리한 결과를 리턴하는 partition() 함수이다.
* partition() 함수는 3으로 나뉘는가 여부에 따라 분리된 두 목록을 리턴한다.
  ```scala
  numbers partition (_ % 3 == 0)
  // (List(3, 6, 9),List(1, 2, 4, 5, 7, 8, 10))
  ```

---
### 2.4.1 필터 - cont. (스칼라)
* find() 함수는 조건을 만족시키는 첫 번째 요소만 리턴한다.
  ```scala
  numbers find (_ % 3 == 0) 
  // Some(3)
  ```
* find()의 리턴 값은 조건을 만족시킨 값 자체가 아니고, Option 클래스로 래핑된 값이다. Option은 Some 또는 None 두 가지 값을 가질 수 있다.
* 다른 함수형 언어들과 마찬가지로 스칼라는 null을 리턴하는 것을 피하기 위해 관례적으로 Option을 사용한다. 
* 리턴할 값이 없는 경우에는 대신 None을 리턴한다.
  ```scala
  numbers find (_ < 0) 
  // None
  ```

---
### 2.4.1 필터 - cont. (스칼라)
* 스칼라는 컬렉션에서 주어진 술어 함수에 만족시키는 요소를 간직하거나 또는 버리는 함수들도 가지고 있다. 
* takeWhile() 함수는 컬렉션의 앞에서부터 술어 함수를 만족시키는 값들의 최대 집합을 리턴한다.
  ```scala
  List(1, 2, 3, -4, 5, 6, 7, 8, 9, 10) takeWhile (_ > 0) 
  // List(1, 2, 3)
  ```
* dropWhile() 함수는 술어 함수를 만족시키는 최다수의 요소를 건너뛴다.
  ```scala
  words dropWhile (_ startsWith "t")
  // List(quick, brown, fox, jumped, over, the, lazy, dog)
  ``` 

---
### 2.4.1 필터 - cont. (그루비)
* 그루비는 함수형 언어라고는 할 수는 없지만 스크립팅 언어에서 파생된 이름을 가진 함수형 패러다임을 다수 포함하고 있다.
* findAll() 메서드는 함수형 언어에서 전통적으로 filter()라고 불리는 함수이다.
  ```groovy
  (1..10).findAll {it % 3 == 0} 
  // [3, 6, 9]
  ```
* filterAll()은 문자열을 포함해 모든 자료형에 적용된다.
  ```groovy
  def words = ["the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog"]
  words.findAll {it.length() == 3} 
  // [The, fox, the, dog]
  ```

---
### 2.4.1 필터 - cont. (그루비)
* split()이라는, partition()과 유사한 함수도 있다.
  ```groovy
  (1..10).split {it % 3}
  // [ [1, 2, 4, 5, 7, 8, 10], [3, 6, 9] ]
  ```
* find() : 컬렉션에서 조건을 만족시키는 첫 요소를 리턴
  ```groovy
  (1..10).find {it % 3 == 0}
  // 3
  ```
* 그루비는 자바의 관례를 따라서 find()의 결과가 없을 때는 null을 리턴
  ```groovy
  (1..10).find {it < 0} 
  // null
  ```

---
### 2.4.1 필터 - cont. (그루비)
* takeWhile
  ```groovy
  [1, 2, 3, -4, 5, 6, 7, 8, 9, 10].takeWhile {it > 0} 
  // [1, 2, 3]
  ```
* dropWhile
	* 목록의 앞부분만 필터하여 술어 조건을 만족시키는 **최다수**의 요소를 건너뛴다.
  ```groovy
  def words = ["the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog"]
  words.dropWhile {it.startsWith("t")}
  // [quick, brown, fox, jumped, over, the, lazy, dog]

  def moreWords = ["the", "two", "ton"] + words 
  moreWords.dropWhile {it.startsWith("t")}
  // [quick, brown, fox, jumped, over, the, lazy, dog]
  ```

---
### 2.4.1 필터 - cont. (클로저)
* 클로저에는 컬렉션을 조작하는 루틴들이 놀라울 만큼 많이 있다.
* 클로저의 동적 타이핑 덕분에 이것들은 범용적으로 사용할 수 있어서 아주 유용하다.
* 클로저는 (filter ) 함수에서 볼 수 있듯이, 전통적인 함수형 프로그래밍 이름을 사용한다.
  ```clojure
  (def numbers (range 1 11))
  (filter (fn [x] (= 0 (rem x 3))) numbers) 
  ; (3 6 9)
  ```
* 익명함수를 사용한 간결한 문법
  ```clojure
  (filter #(zero? (rem % 3)) numbers) 
  ; (3 6 9)
  ```

---
### 2.4.1 필터 - cont. (클로저)
* 함수들은 문자열 등 어떤 자료형에도 사용할 수 있다.
  ```clojure
  (def words ["the" "quick" "brown" "fox" "jumped" "over" "the" "lazy" "dog"]) (filter #(= 3 (count %)) words)
  ; (the fox the dog)
  ```
* 클로저에서 (filter )의 리턴 자료형은 ()로 표기되는 Seq이다. Seq 인터페이스는 클로저의 순차적 컬렉션의 주요 추상 개념이다.

---
### 2.4.2 맵
* 모든 함수형 언어에서 볼 수 있는 두 번째 주요 변형 함수는 맵이다. 맵 함수는 함수와 컬렉션을 받아서 이 함수를 각 요소에 적용한 후 컬렉션을 리턴한다. 리턴된 컬렉션은 개개의 값은 변했지만 필터의 경우와는 달리 원래 컬렉션과 크기는 같다.

---
### 2.4.2 맵 - cont. (스칼라)
* map() : 코드 블록을 받아서 변형된 컬렉션을 리턴
  ```scala
  List(1,2,3,4,5)map(_+1)
  // List(2, 3, 4, 5, 6)
  ```
* 문자열의 각 요소의 길이를 목록으로 리턴
  ```scala
  words map (_.length)
  // List(3, 5, 5, 3, 6, 4, 3, 4, 3)
  ```

---
### 2.4.2 맵 - cont. (스칼라)
* 중첩을 펼치는 연산을 라이브러리에서 지원하는데 이를 흔히 플래트닝이라고 한다.
  ```scala
  List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)) flatMap (_.toList) 
  // List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  ```
* 문자열의 목록은 중첩된 문자들의 배열로 볼 수 있다.
  ```scala
  words flatMap (_.toList)
  // List(t, h, e, q, u, i, c, k, b, r, o, w, n, f, o, x, ...
  ```

---
### 2.4.2 맵 - cont. (그루비)
* 맵의 변형인 collect()를 가지고 있다.
  ```groovy
  (1..5).collect {it += 1} 
  // [2, 3, 4, 5, 6]
  ```
* 문자열 배열에도 사용 가능
  ```groovy
  def words = ["the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog"]
  words.collect {it.length()} 
  // [3, 5, 5, 3, 6, 4, 3, 4, 3]
  ```
* flatten() : flatMap()과 유사
  ```groovy
  [ [1, 2, 3], [4, 5, 6], [7, 8, 9] ].flatten() 
  // [1, 2, 3, 4, 5, 6, 7, 8, 9]

  (words.collect {it.toList()}).flatten()
  // [t, h, e, q, u, i, c, k, b, r, o, w, n, f, o, x, j, ...
  ```

---
### 2.4.2 맵 - cont. (클로저)
* (map )
  ```clojure
  (def numbers (range 1 11))
  (map inc numbers)
  ; (2 3 4 5 6 7 8 9 10 11)
  ```
* (map ) 함수의 첫째 매개변수는 단일 매개변수를 하나만 받는 어떤 함수든 될 수 있다. 기명함수, 익명함수, 또는 전달된 인수의 값을 증가하는 inc 같은 내장함수도 가능.
* 단어들의 길이를 컬렉션으로 만드는 예제
  ```clojure
  (map #(count %) words) 
  ; (3 5 5 3 6 4 3 4 3)
  ```
* (flatten )
  ```clojure
  (flatten[[123][456][789]])
  ; (1 2 3 4 5 6 7 8 9)
  ```

---
### 2.4.2 폴드/리듀스
* 이름이 가장 다양하고, 언어에 따라 미묘한 차이가 많이 있다.

---
### 2.4.2 폴드/리듀스 - cont. (스칼라)
* 합계를 내는 데에는 리듀스를 주로 사용
  ```scala
  List.range(1, 10) reduceLeft((a, b) => a + b) 
  // 45
  ```
* 스칼라의 편리한 구문을 사용하여 함수를 간결하게 정의
  ```scala
  List.range(1, 10).reduceLeft(0)(_ + _) 
  // 45
  ```

---
### 2.4.2 폴드/리듀스 - cont. (스칼라)
* reduceLeft() : 첫째 요소가 연산의 좌항
* reduceRight() : 연산자가 적용되는 순서를 뛰바꿈
  ```scala
  List.range(1, 10) reduceRight(_ - _)
  // 8 -    9  = -1
  // 7 - (-1) = 8
  // 6 -    8  = -2
  // 5 - (-2) = 7
  // 4 -    7  = -3
  // 3 - (-3) = 6
  // 2 -    6 = -4
  // 1 - (-4) = 5
  // result: 5
  ```
* 8-9를 먼저 연산하고, 그 결과를 다시 다음 연산의 **두 번째** 매개변수로 사용한다.
* 리듀스와 같은 고수준의 추상 개념을 어떤 경우에 사용하는가를 터득하는 것이 함수형 프로그래밍을 마스터하는 방법 중의 하나이다.

---
### 2.4.2 폴드/리듀스 - cont. (스칼라)
* 컬렉션에 들어 있는 가장 긴 단어를 찾아낸다.
  ```scala
  def words = ["the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog"]
  words.reduceLeft((a, b) => if (a.length > b.length) a else b) // jumped
  ```

---
### 2.4.2 폴드/리듀스 - cont. (스칼라)
* flodLeft()를 사용해서 컬렉션을 합하는 예제 - 초기값을 받는 형태
  ```scala
  List.range(1, 10).foldLeft(0)(_ + _)
  // 45
  ```
* 스칼라는 연산자 오버로딩을 지원
* flodLeft와 foldRight는 상응하는 연산자 :/과 :\가 있다
  ```scala
  (0 /: List.range(1, 10)) (_ + _) 
  // 45
  ```
* foldRight or :\ 연산자를 사용하여 목록 각 요서간의 계단식 차를 구하기
  ```scala
  (List.range(1, 10) :\ 0) (_ - _) 
  // 5
  ```

---
### 2.4.2 폴드/리듀스 - cont. (그루비)
* 리듀스 계열의 inject() 함수에 오버로딩을 사용하여 스칼라의 reduce()와 foldLeft()의 기능을 제공
  ```groovy
  (1..10).inject {a, b -> a + b} 
  // 55
  ```
* 초기 값을 받는 다른 형태
  ```groovy
  (1..10).inject(0, {a, b -> a + b}) 
  // 55
  ```

---
### 2.4.2 폴드/리듀스 - cont. (클로저)
* (reduce ) 함수는 선택적으로 초기 값을 받아서 스칼라의 reduce()와 foldLeft() 두 경우를 해결
  ```clojure
  (reduce + (range 1 11)) 
  ; 55
  ```
* 클로저는 리듀서 같은 고급 기능을 제공하는 [리듀서 라이브러리](http://clojure.org/reference/reducers)를 제공한다.

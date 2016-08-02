<!-- $size: 16:9 -->
# 2 전환(Shift)
* 일반적으로 개발자들은 이미 다른 언어에 대해 알고 있는 지식을 적용해서 새 언어를 배운다. 하지만 새 패러다임을 익히기는 어렵다. 이미 친숙한 문제에 대해 다른 해답을 떠올릴 능력을 배워야 하기 때문이다.
* **함수형 코드를 작성하기 위해서는 함수형 언어인 스칼라나 클로저로의 전환이 필요한 것이 아니라 문제에 접근하는 방식의 전환이 필요하다.**

---
# 2.1 일반적인 예제
* 함수형 프로그래밍은 복잡한 최적화는 런타임에게 맡기고 개발자가 좀 더 추상화된 수준에서 코드를 작성할 수 있게 함으로써 알고리즘 측면에서 가비지 컬렉션과 동일한 역할을 수행할 것이다.
* 개발자들은 가비지컬렉션에서 얻었던 것같이 복잡성이 낮아지고 성능이 높아지는 혜택을 받게 될 것이다.

---
# 2.1.1 명령형 처리 (1/2)
* 명령형 프로그래밍이란 상태를 변형하는 일련의 명령들로 구성된 프로그래밍 방식을 말한다.

* 다음 예제는 어떤 이름 목록에서 한 글자로 된 이름을 제외한 모든 이름을 대문자화해서 쉼표로 연결한 문자열을 구하는 코드의 예이다.


---
# 2.1.1 명령형 처리 (2/2)
```
Example 2-1. Typical company process (in Java)
package com.nealford.functionalthinking.trans;
import java.util.List;

public class TheCompanyProcess {
public String cleanNames(List<String> listOfNames) {
	StringBuilder result = new StringBuilder(); 
    	for(int i = 0; i < listOfNames.size(); i++) {
		if (listOfNames.get(i).length() > 1) {
        		result.append(capitalizeString(listOfNames.get(i))).append(",");
		} 
    }
	return result.substring(0, result.length() - 1).toString(); }

	public String capitalizeString(String s) {
    		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}
}
```
---
# 2.1.2 함수형 처리
* psudeocode
```
listOfEmps
    -> filter(x.length > 1)
    -> transform(x.capitalize)
    -> convert(x + "," + y)
```

---
# 2.1.2 함수형 처리 - cont.
* 스칼라 예
```
val employees = List("neal", "s", "stu", "j", "rich", "bob", "aiden", "j", "ethan", "liam", "mason", "noah", "lucas", "jacob", "jayden", "jack")

val result = employees 
	.filter(_.length() > 1) 
	.map(_.capitalize) 
	.reduce(_ + "," + _)
```

---
# 2.1.2 함수형 처리 - cont.
* java 8
```
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
# 2.1.2 함수형 처리 - cont.
* 그루비
```
public static String cleanUpNames(listOfNames) { listOfNames
        .findAll { it.length() > 1 }
        .collect { it.capitalize() }
        .join ','
}
```

---
# 2.1.2 함수형 처리 - cont.
* 클로저
```
(ns trans.core
	(:require [clojure.string :as s]))
    
(defn process [list-of-emps] 
	(reduce str (interpose ","
		(map s/capitalize (filter #(< 1 (count %)) list-of-emps)))))
```
*내 생각 : 클로저를 처음 접했는데, 다른 언어와 비교하면 클로저는 외계어처럼 느껴진다.*

* 클로저 코드를 읽는 데 익숙하지 않다면 코드 구조를 이해하기가 어려울 거다. 클로저와 같은 리스프 계열 언어는 '안에서 밖으로' 실행되므로 마지막 매개변수 값인 list-of-emps에서 시작해야 한다.

---
# 2.1.2 함수형 처리 - cont.
* 이제까지 본 모든 언어들은 함수형 프로그래밍의 주요 개념을 포함하고 있다. 함수형 사고로의 전환은 어떤 경우에 세부적인 구현에 뛰어들지 않고 이런 고수준 추상 개념을 적용할지를 배우는 것이다.
* 고수준의 추상적 사고로 얻는 이점은?
	1. 문제의 공통점을 고려하여 다른 방식으로 분류하기를 권장한다. (it encourages you to categorize problems differently, seeing commonalities)
	2. 런타임이 최적화를 잘할 수 있도록 해준다. (어떤 경우 작업 순서를 바꾸면 더 능률적이 된다)
	3. 개발자가 엔진 세부사항에 깊이 파묻힐 경우 불가능한 해답을 가능하게 한다.

---
# 2.1.2 함수형 처리 - cont.
* java의 명령형 코드를 병렬처리화 한다고 생각해보자 --;;
* 다음은 scala의 병렬처리 코드
	* 스트림에 par만 붙이면 된다.
```
val parallelResult = employees 
	.par
	.filter(_.length() > 1)
	.map(_.capitalize)
	.reduce(_ + "," + _)
```

---
# 2.1.2 함수형 처리 - cont.
* java 8의 병렬처리도 마찬가지
	* parallelStream만 추가
```
public String cleanNamesP(List<String> names) { if (names == null) return "";
return names
                .parallelStream()
                .filter(n -> n.length() > 1)
                .map(e -> capitalize(e))
                .collect(Collectors.joining(","));
}
```

---
# 2.1.2 함수형 처리 - cont.
* JVM 엔지니어들이 가비지 컬렉션을 거의 신경 쓸 필요가 없을 정도로 추상화해준 덕분에 개발자들의 삶의 질은 향상되었다.
* map, reduce, filter 같은 함수형 연산도 이와 같은 이중적인 혜택을 준다. 훌륭한 일례가 클로저용 리듀서 라이브러리이다.


---
# 2.2 사례 연구: 자연수의 분류
* 자연수 분류기의 구현 예
* 고대 그리스의 수학자 니코마코스는 자연수를 과잉수, 완전수, 부족수로 나누는 분류법을 고안
	* 완전수 : 진약수의 합 = 수
	* 초과수 : 진약수의 합 > 수
	* 부족수 : 진약수의 합 < 수
* ex> 6의 약수는 1, 2, 3, 6 : 6 = 1 + 2 + 3 --> 완전수
* ex> 28 = 1 + 2 + 4 + 7 + 14 --> 완전수

---
# 2.2.1 명령형 자연수 분류
* [예제 2-10 자바를 사용한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.1_ex_2-10.java)
	* (1) 대상이 되는 수를 보유한 내부 상태
	* (2) 합을 반복해서 계산하는 것을 피하기 위한 내부 캐시
	* (3) aliquotSum(자신을 제외한 모든 약수의 합) 계산

---
# 2.2.2 조금 더 함수적인 자연수 분류기
* 공유상태를 최소화 하기위해 멤버 변수를 없애고 필요한 값들을 매개변수로 넘김
* [예제 2-11 약간 더 함수형인 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.2_ex_2-11.java)
	* (1) 모든 메서드는 number를 매개변수로 받아야 한다. 그 값을 유지할 내부 상태는 없다.
	* (2) 모든 메서드는 순수함수이기 때문에 public static이다. 그렇기 때문에 자연수 분류 문제라는 범위 밖에서도 유용하다
	* (3) 일반적이고 합리적인 변수의 사용으로 함수 수준에서의 재사용이 쉬워졌다.
	* (4) 이 코드는 캐시가 없기 때문에 반복적으로 사용하기에 비능률적이다.

* 이 해법에는 sum의 캐시를 구현하지 않았다. 4장에서 볼 버젼에는 메모이제이션을 통해 이런 상태성을 보존하면서 캐시를 회복하지만, 당분간은 캐시는 없애기로 하자.

---
# 2.2.3 자바 8을 사용한 자연수 분류기
* 자바 8에 더해진 최고의 기능은 람다블록과 고계함수다.
* [예제 2-12 자바 8 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.3_ex_2-12.java)
	* factorOf() 메서드는 스트림을 종료한 뒤 값으로 만드는 것과 같은 다른 작업과 연계할 수 있도록 IntStream을 리턴한다.
	* aliquotSum() 메서드는 목록에 있는 자연수의 합에서 자신의 값을 제외하기만 하면 된다. 이 메서드는 스트림을 종료하고 값을 생성해준다.


---
# 2.2.4 함수형 자바를 사용한 자연수 분류기
* Functional Java는 자바 1.5 이후 버전에 무리 없이 함수형 표현을 추가하려는 목적으로 만들어진 오픈소스 프레임워크다.
* 1.5 시절의 자바에는 고계함수가 없었기 때문에 함수형 자바는 제네릭이나 익명 내부 클래스를 흉내 내어 사용했다.
* [예제 2-13 함수형 자바를 사용한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.4_ex_2-13.java)
	* [예제 2-12](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.3_ex_2-12.java)와 [예제 2-13](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.4_ex_2-13.java)의 주요 차이점은 두 메서드에서 볼 수 있다. aliquotSum()과 factorsOf() 메서드다.
	* aliquotSum() 메서드는 함수형 자바에서 제공하는 List 클래스의 foldLeft() 메서드를 사용한다.
	* factorsOf() : 자연수 1부터 대상의 수까지 f() 메서드의 코드를 사용해서 목록을 필터한다.

---
# 2.3 공동된 빌딩블록

---
# 2.3.1 필터
* 목록(Lists)에서 할 수 있는 흔한 작업은 필터하는 것이다.
* 필터 작업을 할 때에는 필터 조건에 따라서 원래 목록보다 작은 목록(또는 컬렉션)을 생성한다.
* 예제 2-14 자바 8에서의 필터 작업
```
public static IntStream factorsOf(int number) {
        return range(1, number + 1)
               .filter(potential -> number % potential == 0);
}
```
---
# 2.3.1 필터 - cont.
* 람다 블록이 있는 언어에서는 더 간결하게 표현할 수 있다.
* 예제 2-15 그루비에서의 필터 작업(그루비에서는 필터를 findAll()이라고 부른다)
```
static def factors(number) {
        (1..number).findAll {number % it == 0}
}
```

---
# 2.3.2 맵
* 맵 연산은 컬렉션의 각 요소에 같은 함수를 적용하여 새로운 컬렉션으로 만든다.
* map()이나 그와 관련된 변형 연산들을 살펴보기 위해서 자연수 분류기의 최적화된 버젼을 만들었다.
* [예제 2-16 최적화된 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.3.2_ex_2-16.java)
	* [예제 2-10 자바를 사용한 자연수 분류기](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.2.1_ex_2-10.java)와 비교하면 getFactors()가 최적화 됨.
	1. 여기저기에 number를 매개변수로 입력하는 것을 방지하기 위한 내부 상태
	2. sum을 더 효율적으로 조회하기 위한 캐시
	3. 약수는 항상 짝으로 찾을 수 있는 점을 이용한 최적화
	4. 캐시된 합이 있는 경우 리턴하는 메서드

---
# 2.3.2 맵 - cont.
* 그루비는 물론 함수형 변형 함수들을 포함하고 있다. 예제 2-17에서 보듯이 collect()가 map()의 그루비 버젼이다.
* 예제 2-17 그루비로 최적화된 factors
```
static def factors(number) {
        def factors = (1..round(sqrt(number)+1)).findAll({number % it == 0}) 
        (factors + factors.collect {number / it}).unique()
}
```

---
# 2.3.2 맵 - cont.
* 함수형 프로그래밍이 얼마나 코드를 바꿀 수 있는지를 보기 위해 클로저로 짠 예제 2-18을 살펴보자
* [예제 2-18 모든 연산을 몇 번의 할당으로 캡슐화한 클로저의 (classify) 함수](https://github.com/happy4u/functional_thinking/blob/master/chapter2/2.3.2_ex_2-18.clj)
	1. 메서드는 곧 할당이 된다.
	2. 필터된 범위에 약수를 할당한다
	3. 리듀스된 약수에 sum을 할당한다.
	4. 진약수의 합을 계산한다
	5. 해당하는 분류 키워드(열거)를 리턴한다.

---
# 2.3.3 폴드/리듀스
* 셋째로 자주 사용하는 함수는 많이 사용하는 언어들 사이에서도 이름이 다양하고 약간씩 의미도 다르다. foldleft나 reduce는 캐터모피즘(카테고리 이론의 개념으로 목록을 접어서 다른 형태로 만드는 연산을 총칭한다.)이라는 목록 조작 개념의 특별한 변형이다.
* ![폴드 연산(fold operation)](https://github.com/happy4u/functional_thinking/blob/master/chapter2/fold_operation.gif)
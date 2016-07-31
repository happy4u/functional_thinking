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

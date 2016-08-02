(ns number_classifier.core)

(defn classify [num]
  (let [factors (->> (range 1 (inc num))    ; (1)
                (filter#(zero?(remnum%))))  ; (2)
                sum (reduce + factors)      ; (3)
                aliquot-sum (- sum num)]    ; (4)
  (cond                                     ; (5)
    (= aliquot-sum num) :perfect
    (> aliquot-sum num) :abundant
    (< aliquot-sum num) :deficient)))

;; The MIT License (MIT)
;;
;; Copyright (c) 2016 Richard Hull
;;
;; Permission is hereby granted, free of charge, to any person obtaining a copy
;; of this software and associated documentation files (the "Software"), to deal
;; in the Software without restriction, including without limitation the rights
;; to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
;; copies of the Software, and to permit persons to whom the Software is
;; furnished to do so, subject to the following conditions:
;;
;; The above copyright notice and this permission notice shall be included in all
;; copies or substantial portions of the Software.
;;
;; THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
;; IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
;; FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
;; AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
;; LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
;; OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
;; SOFTWARE.

(ns base58.core)

(def alphabet (vec "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"))

(defn leading-zeros [s]
  (->>
    s
    (map byte)
    (take-while zero?)
    count))

(defn string->bigint [s]
  (reduce +
    (map #(* (byte %1) %2)
      (reverse s)
      (iterate (partial * 256M) 1M))))

(def divmod (juxt quot mod))

(defn base-emitter [value]
  (if (pos? value)
    (let [[d m] (divmod value 58)]
      (cons
        (int m)
        (lazy-seq (base-emitter d))))))

(defn encode [s]
  (->>
    (string->bigint s)
    base-emitter
    (map alphabet)
    reverse
    (concat (repeat (leading-zeros s) (first alphabet)))
    (apply str)))

;; TODO - decode

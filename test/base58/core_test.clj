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

(ns base58.core-test
  (:require
    [clojure.test :refer :all]
    [base58.core :refer :all]))

(deftest check-leading-zeros
  (is (= 0 (count-leading zero? nil)))
  (is (= 0 (count-leading zero? "hello world")))
  (is (= 0 (count-leading zero? "hello\000world")))
  (is (= 3 (count-leading zero? "\000\000\000hello world"))))

(deftest check-string->bigint
  (is (= 0 (string->bigint 256 byte nil)))
  (is (= 0 (string->bigint 256 byte "")))
  (is (= 104M (string->bigint 256 byte "h")))
  (is (= (+ 101M (* 256 104)) (string->bigint 256 byte "he"))))

(deftest check-encoding
  (is (= "" (encode nil)))
  (is (= "" (encode "")))
  (is (= "StV1DL6CwTryKyV" (encode "hello world")))
  (is (= "11StV1DL6CwTryKyV" (encode "\000\000hello world"))))

(deftest check-decoding
  (is (= "" (decode nil)))
  (is (= "" (decode "")))
  (is (= "hello world" (decode "StV1DL6CwTryKyV")))
  (is (= "\000\000hello world" (decode "11StV1DL6CwTryKyV"))))

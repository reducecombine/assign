(ns assign.core
  (:require
   [om.core :as om]
   [sablono.core :refer-macros [html]]
   [clojure.string :as str])
  (:import
   [goog.testing PseudoRandom]))

(defonce app-state
  (atom {:assignee nil}))

(def reviewers
  [nil ;; have to include nil, since the last number of a random number will never be 0
   :jborell
   :aramos
   :vemv
   :mrivero
   :jsanchez])

(defn assign! [text-input]
  (when-let [n (re-find #"[0-9]+" text-input)]
    (let [random-numbers (-> n (js/parseInt 10) PseudoRandom. .random str (str/split ".") last reverse)
          random-numbers (map int random-numbers)
          chosen (first (map (partial get reviewers)
                             random-numbers))]
      (swap! app-state assoc :assignee chosen))))

(om/root (fn [data owner]
           (reify om/IRender
             (render [_]
               (html
                [:div
                 [:input {:on-change #(-> % .-target .-value assign!)}]
                 "Assignee is: "
                 (-> data :assignee str)]))))
         app-state
         {:target (js/document.getElementById "app")})

(defn on-js-reload [])

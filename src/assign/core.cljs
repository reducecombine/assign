(ns assign.core
  (:require
   [om.core :as om]
   [sablono.core :refer-macros [html]]
   [skratchdot.random-seed]
   [clojure.string :as str])
  (:import
   [goog.testing PseudoRandom]))

(defonce app-state
  (atom {:assignee nil}))

(defn even-pseudo-random [seed]
  (-> seed js/uheprng.create .random str (str/split ".") last (get 3) int))

(comment
  "Certain keys are very slighly more likely to be assigned so `reviewers` is defined in a special order. You can check it with:"
  (->>
   (range 2000)
   (map even-pseudo-random)
   (frequencies)
   (sort-by second)
   (reverse)))

(def reviewers
  {0 :jborrell
   7 :jborrell
   6 :aramos
   5 :aramos
   1 :mrivero
   9 :mrivero
   4 :vemv
   8 :vemv
   3 :jsanchez
   2 :jsanchez})

(comment
  "Check fairness with:"
  (->> (range 800 3000)
       (map even-pseudo-random)
       (map reviewers)
       frequencies))

(defn assign! [text-input]
  (swap! app-state assoc :assignee (when-let [n (re-find #"[0-9]+" text-input)]
                                     (-> n (js/parseInt 10) even-pseudo-random reviewers))))

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

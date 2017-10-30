(ns assign.core
  (:require
   [om.core :as om]
   [sablono.core :refer-macros [html]]))

(enable-console-print!)

(defonce app-state (atom {}))

(om/root (fn [data owner]
           (reify om/IRender
             (render [_]
               (html
                [:div]))))
         app-state
         {:target (js/document.getElementById "app")})

(defn on-js-reload [])

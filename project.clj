(defproject assign "0.1.0-SNAPSHOT"
  :description "_"
  :url "http://example.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[cljsjs/react "15.6.1-1"]
                 [cljsjs/react-dom "15.6.1-1"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.908"]
                 [org.clojure/core.async  "0.3.443"]
                 [org.omcljs/om "1.0.0-alpha46"]
                 [sablono "0.8.0" :exclusions [org.clojure/clojure]]]

  :plugins [[lein-figwheel "0.5.14"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {:on-jsload "assign.core/on-js-reload"
                           :open-urls ["http://localhost:3449/index.html"]}

                :compiler {:main assign.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/assign.js"
                           :foreign-libs [{:file "js_libraries/random_seed.js" :provides ["skratchdot.random-seed"]}]
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/assign.js"
                           :main assign.core
                           :foreign-libs [{:file "js_libraries/random_seed.js" :provides ["skratchdot.random-seed"]}]
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {:css-dirs ["resources/public/css"]
             :nrepl-port 7888
             :nrepl-middleware ["cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"
                                "cemerick.piggieback/wrap-cljs-repl"]}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.4"]
                                  [figwheel-sidecar "0.5.14" :exclusions [org.clojure/tools.nrepl]]
                                  [com.cemerick/piggieback "0.2.2"]]
                   :source-paths ["src"]
                   :plugins [[cider/cider-nrepl "0.16.0-SNAPSHOT" :exclusions [org.clojure/tools.nrepl]]
                             [refactor-nrepl "2.4.0-SNAPSHOT"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js/compiled" :target-path]}})
